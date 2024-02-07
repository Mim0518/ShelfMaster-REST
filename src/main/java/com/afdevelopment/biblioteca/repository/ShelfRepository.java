package com.afdevelopment.biblioteca.repository;

import com.afdevelopment.biblioteca.model.Shelf;
import org.springframework.data.repository.CrudRepository;

public interface ShelfRepository extends CrudRepository<Shelf, Integer> {
    Shelf findShelfById(Integer Id);
    void deleteShelfById(Integer Id);
    boolean existsById(Integer Id);
}
