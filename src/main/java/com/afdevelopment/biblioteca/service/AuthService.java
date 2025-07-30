package com.afdevelopment.biblioteca.service;


import com.afdevelopment.biblioteca.exception.auth.RequiredDataException;
import com.afdevelopment.biblioteca.exception.auth.TokenRefreshException;
import com.afdevelopment.biblioteca.exception.librarian.LibrarianAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.auth.WrongPasswordException;
import com.afdevelopment.biblioteca.exception.librarian.LibrarianNotFoundException;
import com.afdevelopment.biblioteca.model.AuthResponse;
import com.afdevelopment.biblioteca.model.Librarian;
import com.afdevelopment.biblioteca.model.RefreshToken;
import com.afdevelopment.biblioteca.model.TokenRefreshResponse;
import com.afdevelopment.biblioteca.repository.LibrarianRepository;
import com.afdevelopment.biblioteca.request.AuthRequest;
import com.afdevelopment.biblioteca.request.RegisterRequest;
import com.afdevelopment.biblioteca.request.TokenRefreshRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final LibrarianRepository librarianRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration:3600000}")
    private long jwtExpiration; // Default to 1 hour if not specified

    public AuthService(LibrarianRepository librarianRepository, PasswordEncoder passwordEncoder, 
                      AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        this.librarianRepository = librarianRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse authenticateUser(AuthRequest request){
        AuthResponse authResponse = new AuthResponse();
        if(request.getUsername() == null || request.getPassword() == null){
            throw new RequiredDataException("Nombre de usuario y contraseña requeridos");
        }
        if(!librarianRepository.existsLibrarianByUsername(request.getUsername())){
            throw new LibrarianNotFoundException("El usuario no existe");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            authResponse.setUser(request.getUsername());
            String token = JWT.create()
                    .withSubject(request.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration)) // Using configured expiration time
                    .sign(Algorithm.HMAC256(jwtSecret));
            authResponse.setToken(token);

            // Create a refresh token
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getUsername());
            authResponse.setRefreshToken(refreshToken.getToken());
        } catch (AuthenticationException e) {
            throw new WrongPasswordException("Contraseña incorrecta");
        }
        return authResponse;
    }

    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getLibrarian)
                .map(librarian -> {
                    String token = JWT.create()
                            .withSubject(librarian.getUsername())
                            .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration))
                            .sign(Algorithm.HMAC256(jwtSecret));

                    return new TokenRefreshResponse(token, requestRefreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
    public Librarian register(RegisterRequest request) {
        Librarian librarian = new Librarian();
        if(librarianRepository.existsLibrarianByUsername(request.getUsername())){
            throw new LibrarianAlreadyExistsException("El usuario ya existe");
        }else{
            librarian.setUsername(request.getUsername());
            librarian.setPassword(passwordEncoder.encode(request.getPassword()));
            librarian.setNombre(request.getNombre());
            librarian.setApellidoPaterno(request.getApellidoPaterno());
            librarian.setApellidoMaterno(request.getApellidoMaterno());
            librarian.setPermisos(request.getPermisos());
            librarianRepository.save(librarian);
            librarian.setPassword(null);
        }
        return librarian;
    }

    public boolean isSessionActive(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return false;
        }

        try {
            // Eliminar el prefijo "Bearer "
            String jwt = token.substring(7);

            // Verificar el token
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtSecret))
                    .build()
                    .verify(jwt);

            // Verificar la expiración
            Date expirationDate = decodedJWT.getExpiresAt();
            return expirationDate != null && expirationDate.after(new Date());

        } catch (TokenExpiredException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public AuthResponse getSessionInfo(String token) {
        AuthResponse authResponse = new AuthResponse();
        if (!isSessionActive(token)) {
            return null;
        }
        try {
            String jwt = token.substring(7);
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtSecret))
                    .build()
                    .verify(jwt);

            authResponse.setUser(decodedJWT.getSubject());
            authResponse.setToken(jwt);
            authResponse.setRefreshToken(refreshTokenService.findByLibrarian(decodedJWT.getSubject()).getToken());
            return authResponse;
        } catch (Exception e) {
            return null;
        }
    }


}
