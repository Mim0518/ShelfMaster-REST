package com.afdevelopment.biblioteca.repository;


import com.afdevelopment.biblioteca.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByCurp(String curp);
    void deleteUserByCurp(String curp);
}
