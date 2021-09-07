package com.psi.calendar.api.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psi.calendar.api.exceptions.JwtException;
import com.psi.calendar.api.services.IJwtService;
import com.psi.calendar.api.services.impl.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.psi.calendar.api.contants.HttpProtocolConstants.AUTH_URI;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final IJwtService jwtService;

    @Autowired
    public JwtRequestFilter (final UserDetailsService userDetailsService, final JwtServiceImpl jwtService){
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        // If req is on auth uri, proceed without executing filter
        if (req.getRequestURI().contains(AUTH_URI)) {
            filterChain.doFilter(req, res);
            return;
        }

        final var authHeader = req.getHeader("Authorization");

        // Check if jwt was passed in upon request Authorization token
        if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")){
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setCharacterEncoding("UTF-8");

            var mapper = new ObjectMapper();
            res.getWriter().write(mapper.writeValueAsString(Map.of("error", "Jwt not found")));
            return;
        }


        // Remove "Bearer " and leave just the jwt
        final var jwt = authHeader.substring(7);

        try {
            // Extract username from jwt
            final var username = jwtService.extractUsername(jwt);

            // Check for nullability
            if (username == null || username.isEmpty() ) throw new JwtException("Unable to extract username from token");

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // Find user related to jwt username
                final var userDetails = userDetailsService.loadUserByUsername(username);

                // Check if token is valid and not expired
                final var isJwtValid = jwtService.validateToken(jwt, userDetails);

                // If jwt not valid, spring security will block request after filter chain
                if (isJwtValid) {
                    var usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // If jwt is valid, allow request to hit endpoint setting authentication on context
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
                }
            }
        } catch (JwtException e) {
            throw new ServletException(e.getMessage());
        } catch (UsernameNotFoundException e) {
            throw new ServletException("Invalid jwt: " + e.getMessage());
        } finally {
            filterChain.doFilter(req, res);
        }
    }
}
