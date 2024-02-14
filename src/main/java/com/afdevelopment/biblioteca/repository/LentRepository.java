package com.afdevelopment.biblioteca.repository;

import com.afdevelopment.biblioteca.model.Lent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LentRepository extends CrudRepository<Lent, Integer> {
    List<Lent> findAllByUserId(Integer userId);
}
