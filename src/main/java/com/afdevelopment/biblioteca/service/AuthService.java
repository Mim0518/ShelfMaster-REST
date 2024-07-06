package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.exception.user.UserAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.user.UserNotFoundException;
import com.afdevelopment.biblioteca.model.Librarian;
import com.afdevelopment.biblioteca.repository.LibrarianRepository;
import com.afdevelopment.biblioteca.request.GetLibrarian;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    final LibrarianRepository librarianRepository;
    public AuthService(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }
    // Este médoto regresa el JWT token para
    public String login(GetLibrarian librarian) {
        Librarian authLib = librarianRepository.findLibrarianByUsername(librarian.getUsername());
        if (authLib == null) {
            throw new UserNotFoundException("Este usuario no existe");
        }
        if (!passwordEncoder.matches(librarian.getPassword(), authLib.getPassword())) {

        }
        return null;
    }
    //Este método registra a un usuario nuevo
    public Librarian register(Librarian librarian) {
        logger.info("Intento de registro de bilbiotecario");
        if (librarianRepository.existsLibrarianByUsername(librarian.getUsername()))
            throw new UserAlreadyExistsException("Este nombre de usuario ya existe");
        librarian.setPassword(passwordEncoder.encode(librarian.getPassword()));
        Librarian librarianResult = librarianRepository.save(librarian);
        logger.info("Bibliotecario registrado correctamente");
        librarianResult.setPassword(null);
        return librarianResult;
    }
}
