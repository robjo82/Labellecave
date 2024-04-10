package com.labellecave.user.service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(Map<String, Object> claims) {
        Key signingKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Key signingKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS512.getJcaName());
            JwtParser parser = Jwts.parserBuilder().setSigningKey(signingKey).build();
            parser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Map<String, Object> extractAllClaims(String token) {
        Key signingKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS512.getJcaName());
        JwtParser parser = Jwts.parserBuilder().setSigningKey(signingKey).build();
        return parser.parseClaimsJws(token).getBody();
    }
}
