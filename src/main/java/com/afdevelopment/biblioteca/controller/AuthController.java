package com.afdevelopment.biblioteca.controller;


import com.afdevelopment.biblioteca.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final String OPCORRECTA = "Operación correcta";
    private final String DETAIL = "detailResponse";
    private final String SUCCESSCODE = "SUC-002";
    private final String RESPONSEDETAIL = "tokenDetail";
    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login() {
        return "Login Successful";
    }
    @PostMapping("/register")
    public String register() {
        return "Register Successful";
    }
}
