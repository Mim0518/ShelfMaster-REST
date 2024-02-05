package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.exception.UserAlreadyExistsException;
import com.afdevelopment.biblioteca.model.User;
import com.afdevelopment.biblioteca.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        logger.info("Guardando al usuario ".concat(user.getNombre()).concat("con CURP ").concat(user.getCurp()));
        User userResponse;
        try {
            userResponse = userRepository.save(user);
        }catch (Exception e){
            throw new UserAlreadyExistsException("El usuario con CURP ".concat(user.getCurp()).concat(" ya existe"));
        }
        return userResponse;
    }
}
