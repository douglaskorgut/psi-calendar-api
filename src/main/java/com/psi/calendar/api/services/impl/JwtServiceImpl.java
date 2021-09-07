package com.psi.calendar.api.services.impl;

import com.psi.calendar.api.services.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements IJwtService {

    private String SECRET_KEY = "my-super-secret-key";

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return null;
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return null;
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        return null;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return null;
    }
}
