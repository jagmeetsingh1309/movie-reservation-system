package com.project.movie_reservation_system.service;

import com.project.movie_reservation_system.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

@Service
@Scope(value = "singleton")
public class JwtService {

    private final SecretKey secretKey;

    public JwtService(){
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGenerator.init(256);
        secretKey = keyGenerator.generateKey();
    }

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claims(Map.of("ROLES", user.getAuthorities()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 30 * 60 * 60 * 1000))
                .signWith(secretKey)
                .compact();
    }

    public Claims extractAllClaims(String token){
        return (Claims) Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parse(token)
                .getPayload();

    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
}
