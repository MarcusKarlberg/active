package se.mk.active.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

import static se.mk.active.security.SecurityConstants.TOKEN_HEADER_NAME;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtBuilderParser jwtBuilderParser;

    private final SecurityConstants securityConstants;

    @Autowired
    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtBuilderParser jwtBuilderParser,
                                SecurityConstants securityConstants) {
        this.authenticationManager = authenticationManager;
        this.jwtBuilderParser = jwtBuilderParser;
        this.securityConstants = securityConstants;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Authentication Attempt: Username: {} ", username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        System.out.println("*** attemptAuthentication ***");
        System.out.println(username + " " + password);
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = jwtBuilderParser.buildToken(authResult, setTimeFromMinutesToMillis(), securityConstants.getKey());
        response.addHeader(TOKEN_HEADER_NAME, token);
        log.info("Username: {} authenticated!", authResult.getName());
    }

    private long setTimeFromMinutesToMillis() {
        return Duration.ofMinutes(securityConstants.getExpirationInMinutes()).toMillis();
    }
}
