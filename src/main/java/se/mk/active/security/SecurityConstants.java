package se.mk.active.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SecurityConstants {
    @Value("${active.security.apikey}")
    private String key;
    @Value("${active.security.expiration.minutes}")
    private int expirationInMinutes;

    public static final String TOKEN_HEADER_NAME = "token";
    public static final String HEADER_NAME = "Authorization";
}
