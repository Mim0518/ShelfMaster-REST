package com.afdevelopment.biblioteca.repository;

import com.afdevelopment.biblioteca.model.Librarian;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends CrudRepository<Librarian, Integer> {
    Librarian findLibrarianById(int id);
    Librarian findLibrarianByUsername(String username);
    boolean existsLibrarianByUsername(String username);
}
