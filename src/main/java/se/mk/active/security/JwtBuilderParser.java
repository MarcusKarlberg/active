package se.mk.active.security;


import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface JwtBuilderParser {
    String buildToken(Authentication auth, long expTimeMillis, String appKey);
    Claims parseToken(String token, String appKey);
    List<String> getRolesFromClaims(Claims claims);
}
