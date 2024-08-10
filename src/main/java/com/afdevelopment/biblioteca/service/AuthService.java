package com.afdevelopment.biblioteca.service;


import com.afdevelopment.biblioteca.exception.auth.RequiredDataException;
import com.afdevelopment.biblioteca.exception.librarian.LibrarianAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.auth.WrongPasswordException;
import com.afdevelopment.biblioteca.exception.librarian.LibrarianNotFoundException;
import com.afdevelopment.biblioteca.model.AuthResponse;
import com.afdevelopment.biblioteca.model.Librarian;
import com.afdevelopment.biblioteca.repository.LibrarianRepository;
import com.afdevelopment.biblioteca.request.AuthRequest;
import com.afdevelopment.biblioteca.request.RegisterRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final LibrarianRepository librarianRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(LibrarianRepository librarianRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.librarianRepository = librarianRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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
                    .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_00)) // 10 días
                    .sign(Algorithm.HMAC256("secret"));
            authResponse.setToken(token);
        } catch (AuthenticationException e) {
            throw new WrongPasswordException("Contraseña incorrecta");
        }
        return authResponse;
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
}
