package com.psi.calendar.api.controllers;

import com.psi.calendar.api.exceptions.InvalidCredentialsException;
import com.psi.calendar.api.models.dto.auth.AuthRequestDTO;
import com.psi.calendar.api.models.dto.auth.AuthResponseDTO;
import com.psi.calendar.api.services.IJwtService;
import com.psi.calendar.api.services.impl.JwtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthController(
            final AuthenticationManager authenticationManager,
            final JwtServiceImpl jwtService,
            final UserDetailsService userDetailsService
    ){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequestDTO authRequest) throws InvalidCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e){
            throw new InvalidCredentialsException("Incorrect username or password");
        }

        var jwt = jwtService.generateToken(
                userDetailsService.loadUserByUsername(authRequest.getUsername())
        );

        var response = AuthResponseDTO.builder().jwt(jwt).build();

        return ResponseEntity.ok(response);
    }
}
