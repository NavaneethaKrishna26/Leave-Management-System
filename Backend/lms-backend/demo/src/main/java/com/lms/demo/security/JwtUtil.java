package com.lms.demo.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret; // Must be >= 32 characters

    @Value("${jwt.expiration}")
    private long expirationMs; // e.g. 86400000 = 24 hours

    private Key signingKey;

    @PostConstruct
    private void init() {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 🔥 Generate token using EMAIL
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // ✅ email stored as subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(signingKey)
                .compact();
    }

    // 🔥 Extract email (internally still "subject")
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // 🔥 Better validation using UserDetails
    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractUsername(token);

        return email.equals(userDetails.getUsername()) // username = email
                && !isTokenExpired(token);
    }

    // 🔥 Check expiration separately (clean design)
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // 🔥 Central method to parse claims
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}