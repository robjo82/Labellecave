package com.labellecave.user.service;

import com.labellecave.user.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;

    public boolean isAdminOrSameUser(String token, long id) {
        if (jwtService.validateToken(token)) {
            Map<String, Object> claims = jwtService.extractAllClaims(token);
            long userId = Long.parseLong(claims.get("id").toString());
            return userId != id && !((boolean) claims.get("isAdmin"));
        } else {
            throw new UnauthorizedException("The provided token is not valid.");
        }
    }
}
