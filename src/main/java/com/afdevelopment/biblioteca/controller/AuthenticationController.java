package com.afdevelopment.biblioteca.controller;

import com.afdevelopment.biblioteca.exception.auth.TokenRefreshException;
import com.afdevelopment.biblioteca.model.AuthResponse;
import com.afdevelopment.biblioteca.model.Librarian;
import com.afdevelopment.biblioteca.model.TokenRefreshResponse;
import com.afdevelopment.biblioteca.request.AuthRequest;
import com.afdevelopment.biblioteca.request.RegisterRequest;
import com.afdevelopment.biblioteca.request.TokenCheck;

import com.afdevelopment.biblioteca.response.DetailResponse;
import com.afdevelopment.biblioteca.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("DuplicatedCode")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final String OPCORRECTA = "Operación correcta";
    private final String DETAIL = "detailResponse";
    private final String SUCCESSCODE = "SUC-005";
    private final String RESPONSEDETAIL = "librarianDetail";
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody AuthRequest request) {
        logger.info("Inicia controlador de autenticación");
        AuthResponse authResponse = authService.authenticateUser(request);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, authResponse);
        logger.info("Finaliza controlador de autenticación");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody @jakarta.validation.Valid RegisterRequest request) {
        logger.info("Inicia controlador de registro de usuario");
        Librarian registeredLibrarian = authService.register(request);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, registeredLibrarian);
        logger.info("Finaliza controlador de registro de usuario");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }

    @GetMapping("/refreshtoken")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestHeader(value = "Authorization") String authorization) {
        logger.info("Inicia controlador de refresh token");
        try {
            TokenRefreshResponse tokenRefreshResponse = authService.refreshToken(authorization);
            DetailResponse responseOk = new DetailResponse();
            responseOk.setCode(SUCCESSCODE);
            responseOk.setBussinessMeaning(OPCORRECTA);
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put(DETAIL, responseOk);
            jsonResponse.put(RESPONSEDETAIL, tokenRefreshResponse);
            logger.info("Finaliza controlador de refresh token");
            return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK);
        } catch (TokenRefreshException e) {
            DetailResponse responseError = new DetailResponse();
            responseError.setCode("ERR-401");
            responseError.setBussinessMeaning("Invalid refresh token");
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put(DETAIL, responseError);
            logger.warn("Invalid refresh token during refresh request");
            return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/check-session")
    public ResponseEntity<?> validateSession(@RequestBody TokenCheck tokenCheck){
        boolean isActive = authService.isSessionActive(tokenCheck.getToken());
        if(isActive){
            AuthResponse authResponse = authService.getSessionInfo(tokenCheck.getToken());
            DetailResponse responseOk = new DetailResponse();
            responseOk.setCode(SUCCESSCODE);
            responseOk.setBussinessMeaning(OPCORRECTA);
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put(DETAIL, responseOk);
            jsonResponse.put(RESPONSEDETAIL, authResponse);
            return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "La sesión no está activa"));
        }
    }
}
