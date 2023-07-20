package com.JWT.security.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private String secretKey;
    private String timeExpiration;
}
