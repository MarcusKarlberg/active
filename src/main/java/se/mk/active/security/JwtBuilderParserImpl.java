package se.mk.active.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import se.mk.active.model.User;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class JwtBuilderParserImpl implements JwtBuilderParser {

    private static final Logger LOG = LoggerFactory.getLogger(JwtBuilderParserImpl.class);

    private static final String BEARER_PREFIX ="[bB]earer";
    private static final String PROVIDER_CLAIM_KEY = "providerId";

    private static final String ROLE_CLAIM_KEY = "role";

    @Override
    public String buildToken(Authentication auth, long expTimeMillis, String appKey) {
        Date expirationTime = Date.from(Instant.now().plusMillis(expTimeMillis));
        Key key = Keys.hmacShaKeyFor(appKey.getBytes());
        Claims claims = Jwts.claims().setSubject(auth.getName());
        setUserProviderIdOnClaim(auth, claims);
        setRolesOnClaim(auth, claims);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationTime)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private void setRolesOnClaim(Authentication auth, Claims claims) {
        final Collection<? extends GrantedAuthority> grantedAuthorities= auth.getAuthorities();
        if(auth.getAuthorities() != null &&
            !auth.getAuthorities().isEmpty()) {
            String roles = getUserRoleAsString(grantedAuthorities);
            claims.put(ROLE_CLAIM_KEY, roles);
        }
    }

    private String getUserRoleAsString(Collection<? extends GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    private void setUserProviderIdOnClaim(Authentication auth, Claims claims) {
        if(auth.getAuthorities() != null &&
                !auth.getAuthorities().isEmpty() &&
                auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            claims.put(PROVIDER_CLAIM_KEY, user.getProvider().getId());
        }
    }

    @Override
    public Claims parseToken(String token, String appKey) {
        String replacedToken = token.replaceFirst(BEARER_PREFIX, "");

        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(appKey.getBytes()))
                .build()
                .parseClaimsJws(replacedToken)
                .getBody();
    }

    public List<String> getRolesFromClaims(Claims claims) {
        if(claims != null) {
            String roleList = (String) claims.get(ROLE_CLAIM_KEY);
            if(roleList != null || !roleList.isEmpty()) {
                return Arrays.asList(roleList.split(","));
            }
        }
        return new ArrayList<>();
    }
}
