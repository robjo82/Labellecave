package com.labellecave.product.service;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

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
