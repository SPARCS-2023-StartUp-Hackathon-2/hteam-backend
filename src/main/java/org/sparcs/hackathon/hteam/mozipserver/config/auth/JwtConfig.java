package org.sparcs.hackathon.hteam.mozipserver.config.auth;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${auth.jwt.secret-key}")
    private String secretKey;

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build();
    }

    @Bean
    public Key getSecretKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
