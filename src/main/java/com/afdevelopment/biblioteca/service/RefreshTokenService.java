package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.exception.auth.TokenRefreshException;
import com.afdevelopment.biblioteca.model.RefreshToken;
import com.afdevelopment.biblioteca.model.Librarian;
import com.afdevelopment.biblioteca.repository.RefreshTokenRepository;
import com.afdevelopment.biblioteca.repository.LibrarianRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${security.jwt.refresh-token.expiration:604800000}") // Default to 7 days
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final LibrarianRepository librarianRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, LibrarianRepository librarianRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.librarianRepository = librarianRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String username) {
        Librarian librarian = librarianRepository.findLibrarianByUsername(username);
        if (librarian == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        // First delete any existing refresh token for this user
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByLibrarian(librarian);
        if (existingToken.isPresent()) {
            refreshTokenRepository.delete(existingToken.get());
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setLibrarian(librarian);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    public RefreshToken findByLibrarian(String librarian) {
        Librarian librarianDB = librarianRepository.findLibrarianByUsername(librarian);
        return refreshTokenRepository.findByLibrarian(librarianDB)
                .orElseThrow(() -> new TokenRefreshException(librarian,
                        "Refresh token is not in database!"));
    }

    @Transactional
    public int deleteByLibrarian(Librarian librarian) {
        return refreshTokenRepository.deleteByLibrarian(librarian);
    }
}
