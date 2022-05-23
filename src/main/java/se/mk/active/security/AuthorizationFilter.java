package se.mk.active.security;

import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import se.mk.active.service.exception.AuthenticationFailedException;
import se.mk.active.service.exception.UnknownServerError;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static se.mk.active.security.SecurityConstants.HEADER_NAME;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtBuilderParser jwtBuilderParser;
    private final SecurityConstants securityConstants;

    public AuthorizationFilter(AuthenticationManager authenticationManager, JwtBuilderParser jwtBuilderParser, SecurityConstants securityConstants) {
        super(authenticationManager);
        this.jwtBuilderParser = jwtBuilderParser;
        this.securityConstants = securityConstants;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_NAME);

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication;

        try {
            authentication = authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            //This will happen often, and is expected...signal that the token has expired.
            log.info("Jwt expired");
            log.info(e.getMessage());

        } catch (JwtException e) {
            log.warn("Unexpected error parsing Jwt");
            log.warn(e.getMessage());
            throw new AuthenticationFailedException("Authentication failed.");
        } catch (Exception e) {
            log.error("Unexpected error when authenticating user");
            log.error(e.getMessage());
            throw new UnknownServerError("Unknown server error");
        }

    }

    private UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) {
        String token = request.getHeader(HEADER_NAME);
        if (token != null) {
            Claims user = jwtBuilderParser.parseToken(token, securityConstants.getKey());
            if (user != null) {
                //We need to pass the role(s) along the filter chain for protection of endpoints...
                List<SimpleGrantedAuthority> authorities = setRolesFromToken(user);
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            } else {
                return null;
            }
        }
        return null;
    }

    private List<SimpleGrantedAuthority> setRolesFromToken(Claims userClaim) {
        List<String> userRoles = jwtBuilderParser.getRolesFromClaims(userClaim);
        return userRoles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
