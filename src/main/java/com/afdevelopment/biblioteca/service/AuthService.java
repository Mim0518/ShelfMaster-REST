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
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

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
        logger.info("Iniciando proceso de autenticación para usuario: {}", request.getUsername());
        AuthResponse authResponse = new AuthResponse();
        if(request.getUsername() == null || request.getPassword() == null){
            logger.error("Intento de autenticación con datos incompletos");
            throw new RequiredDataException("Nombre de usuario y contraseña requeridos");
        }
        if(!librarianRepository.existsLibrarianByUsername(request.getUsername())){
            logger.error("Intento de autenticación para usuario inexistente: {}", request.getUsername());
            throw new LibrarianNotFoundException("El usuario no existe");
        }
        try {
            logger.info("Verificando credenciales para usuario: {}", request.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            authResponse.setUser(request.getUsername());
            logger.info("Generando token JWT para usuario: {}", request.getUsername());
            String token = generateJwt(request.getUsername());
            authResponse.setToken(token);

            // Create a refresh token
            logger.info("Generando refresh token para usuario: {}", request.getUsername());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getUsername());
            authResponse.setRefreshToken(refreshToken.getToken());
            logger.info("Autenticación exitosa para usuario: {}", request.getUsername());
        } catch (AuthenticationException e) {
            logger.error("Contraseña incorrecta para usuario: {}", request.getUsername());
            throw new WrongPasswordException("Contraseña incorrecta");
        }
        return authResponse;
    }

    public TokenRefreshResponse refreshToken(String requestRefreshToken) {
        logger.info("Iniciando proceso de refresh token");
        try {
            return refreshTokenService.findByToken(requestRefreshToken)
                    .map(token -> {
                        logger.info("Refresh token encontrado, verificando expiración");
                        return refreshTokenService.verifyExpiration(token);
                    })
                    .map(RefreshToken::getLibrarian)
                    .map(librarian -> {
                        logger.info("Generando nuevo JWT token para usuario: {}", librarian.getUsername());
                        String token = generateJwt(librarian.getUsername());
                        logger.info("Refresh token procesado exitosamente para usuario: {}", librarian.getUsername());
                        return new TokenRefreshResponse(token, requestRefreshToken, librarian.getUsername());
                    })
                    .orElseThrow(() -> {
                        logger.error("Refresh token no encontrado en la base de datos: {}", maskToken(requestRefreshToken));
                        return new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!");
                    });
        } catch (Exception e) {
            logger.error("Error al procesar refresh token: {}", e.getMessage());
            throw e;
        }
    }

    private String maskToken(String token) {
        if (token == null) return "null";
        int len = token.length();
        if (len <= 6) return "***";
        String start = token.substring(0, Math.min(4, len));
        String end = token.substring(len - 2);
        return start + "..." + end;
    }
    private boolean isStrongPassword(String password) {
        if (password == null) return false;
        if (password.length() < 8 || password.length() > 128) return false;
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> "!@#$%^&*()_+-=[]{};:'\"|,.<>/?".indexOf(c) >= 0);
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    private String generateJwt(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public Librarian register(RegisterRequest request) {
        logger.info("Iniciando proceso de registro para nuevo usuario: {}", request.getUsername());
        Librarian librarian = new Librarian();
        if(librarianRepository.existsLibrarianByUsername(request.getUsername())){
            logger.error("Intento de registro con nombre de usuario ya existente: {}", request.getUsername());
            throw new LibrarianAlreadyExistsException("El usuario ya existe");
        }else{
            logger.info("Creando nuevo usuario: {}", request.getUsername());
            // Fallback validation in case Bean Validation is bypassed
            if (request.getPassword() == null || !isStrongPassword(request.getPassword())) {
                logger.warn("Intento de registro con contraseña débil para usuario: {}", request.getUsername());
                throw new RequiredDataException("Password does not meet complexity requirements");
            }
            librarian.setUsername(request.getUsername());
            librarian.setPassword(passwordEncoder.encode(request.getPassword()));
            librarian.setNombre(request.getNombre());
            librarian.setApellidoPaterno(request.getApellidoPaterno());
            librarian.setApellidoMaterno(request.getApellidoMaterno());
            librarian.setPermisos(request.getPermisos());
            logger.info("Guardando nuevo usuario en la base de datos: {}", request.getUsername());
            librarianRepository.save(librarian);
            librarian.setPassword(null);
            logger.info("Usuario registrado exitosamente: {}", request.getUsername());
        }
        return librarian;
    }

    public boolean isSessionActive(String token) {
        logger.info("Verificando si la sesión está activa");
        if (token == null || !token.startsWith("Bearer ")) {
            logger.warn("Token inválido o con formato incorrecto");
            return false;
        }

        try {
            // Eliminar el prefijo "Bearer "
            String jwt = token.substring(7);
            logger.debug("Verificando token JWT");

            // Verificar el token
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtSecret))
                    .build()
                    .verify(jwt);

            // Verificar la expiración
            Date expirationDate = decodedJWT.getExpiresAt();
            boolean isActive = expirationDate != null && expirationDate.after(new Date());
            
            if (isActive) {
                logger.info("Sesión activa para usuario: {}", decodedJWT.getSubject());
            } else {
                logger.info("Sesión expirada para usuario: {}", decodedJWT.getSubject());
            }
            
            return isActive;

        } catch (TokenExpiredException e) {
            logger.warn("Token expirado: {}", e.getMessage());
            return false;
        } catch (JWTVerificationException e) {
            logger.error("Error al verificar token JWT: {}", e.getMessage());
            return false;
        }
    }

    public AuthResponse getSessionInfo(String token) {
        logger.info("Obteniendo información de sesión");
        AuthResponse authResponse = new AuthResponse();
        if (!isSessionActive(token)) {
            logger.warn("Sesión inactiva, no se puede obtener información");
            return null;
        }
        try {
            String jwt = token.substring(7);
            logger.debug("Decodificando token JWT para obtener información de sesión");
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtSecret))
                    .build()
                    .verify(jwt);

            String username = decodedJWT.getSubject();
            logger.info("Recuperando información de sesión para usuario: {}", username);
            authResponse.setUser(username);
            authResponse.setToken(jwt);
            
            try {
                RefreshToken refreshToken = refreshTokenService.findByLibrarian(username);
                authResponse.setRefreshToken(refreshToken.getToken());
                logger.info("Información de sesión recuperada exitosamente para usuario: {}", username);
            } catch (Exception e) {
                logger.warn("No se pudo recuperar el refresh token para usuario: {}", username);
            }
            
            return authResponse;
        } catch (Exception e) {
            logger.error("Error al obtener información de sesión: {}", e.getMessage());
            return null;
        }
    }


}
