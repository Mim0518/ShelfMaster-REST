package com.afdevelopment.biblioteca.service;


import com.afdevelopment.biblioteca.model.Librarian;
import com.afdevelopment.biblioteca.repository.LibrarianRepository;
import com.afdevelopment.biblioteca.request.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final LibrarianRepository librarianRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(LibrarianRepository librarianRepository, PasswordEncoder passwordEncoder) {
        this.librarianRepository = librarianRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Librarian register(RegisterRequest request) {
        Librarian librarian = new Librarian();
        librarian.setUsername(request.getUsername());
        librarian.setPassword(passwordEncoder.encode(request.getPassword()));
        librarian.setNombre(request.getNombre());
        librarian.setApellidoPaterno(request.getApellidoPaterno());
        librarian.setApellidoMaterno(request.getApellidoMaterno());
        librarian.setPermisos(request.getPermisos());

        return librarianRepository.save(librarian);
    }
}
