package com.psi.calendar.api.services;

import com.psi.calendar.api.exceptions.JwtException;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface IJwtService {
    String extractUsername(String token) throws JwtException;

    Date extractExpiration(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Boolean validateToken(String token, UserDetails userDetails) throws JwtException;

    String generateToken(UserDetails userDetails);
}
