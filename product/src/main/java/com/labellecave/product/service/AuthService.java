package com.labellecave.product.service;

import com.labellecave.product.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;

    public boolean isNotAdmin(String token) {
        if (jwtService.validateToken(token)) {
            Map<String, Object> claims = jwtService.extractAllClaims(token);
            return (!((boolean) claims.get("isntAdmin")));
        } else {
            throw new UnauthorizedException("The provided token is not valid.");
        }
    }
}