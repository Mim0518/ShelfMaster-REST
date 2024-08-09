package com.afdevelopment.biblioteca.controller;

import com.afdevelopment.biblioteca.model.Librarian;
import com.afdevelopment.biblioteca.request.AuthRequest;
import com.afdevelopment.biblioteca.request.RegisterRequest;
import com.afdevelopment.biblioteca.service.AuthService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String token = JWT.create()
                    .withSubject(request.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_00)) // 10 días
                    .sign(Algorithm.HMAC256("secret"));
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Librarian> register(@RequestBody RegisterRequest request) {
        Librarian registeredLibrarian = authService.register(request);
        return ResponseEntity.ok(registeredLibrarian);
    }
}
