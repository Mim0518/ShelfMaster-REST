package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.model.Librarian;
import com.afdevelopment.biblioteca.repository.LibrarianRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianUserDetailsService implements UserDetailsService {

    private final LibrarianRepository librarianRepository;

    public LibrarianUserDetailsService(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Librarian librarian = librarianRepository.findLibrarianByUsername(username);
        if (librarian == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                librarian.getUsername(),
                librarian.getPassword(),
                List.of(() -> librarian.getPermisos()) // Assuming 'permisos' is a comma-separated list of roles
        );
    }
}
