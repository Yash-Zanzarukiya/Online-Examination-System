package com.yashpz.examination_system.examination_system.utils;

import com.yashpz.examination_system.examination_system.model.Auth;
import com.yashpz.examination_system.examination_system.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${ACCESS_TOKEN_SECRET}")
    private String ACCESS_TOKEN_SECRET;

    @Value("${ACCESS_TOKEN_EXPIRY}")
    private int ACCESS_TOKEN_EXPIRY;

    private final AuthRepository authRepository;

    public JwtUtil(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String generateToken(String username, Map<String, Object> claims) {
        return createToken(claims, username);
    }

    public Auth validateUserFromToken(String token) {
        Boolean isValid = validateToken(token);
        String username = extractUsername(token);

        if(!isValid || username == null || username.isEmpty())
            return null;

        return authRepository.findByUsername(username);
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public Map<String, Object> getPayloadData(String token) {
        Claims claims = extractAllClaims(token);
        return new HashMap<>(claims);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY))
                .signWith(getSigningKey())
                .compact();
    }
}