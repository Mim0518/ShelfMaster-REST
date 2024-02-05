package com.afdevelopment.biblioteca.service;


import com.afdevelopment.biblioteca.exception.InvalidParametersException;
import com.afdevelopment.biblioteca.exception.UserAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.UserNotFoundException;
import com.afdevelopment.biblioteca.model.User;
import com.afdevelopment.biblioteca.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public User findByCURP(String curp){
        logger.info("Buscando usuario con CURP: ".concat(curp));
        User foundUser;
        if (curpIsValid(curp)){
            foundUser = userRepository.findUserByCurp(curp);
            if (foundUser == null){
                logger.error("Usuario no encontrado");
                throw new UserNotFoundException("No se encontró el usuario con CURP ".concat(curp));
            }
            logger.info("Se encontró el usuario ".concat(foundUser.getNombre())
                    .concat("con CURP ").concat(foundUser.getCurp()));
            return foundUser;
        }
        return null;
    }

    public boolean curpIsValid(String curp){
        String REGEX_CURP =
                "^([A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM]" +
                        "(?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)" +
                        "[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)$";
        if(!curp.toUpperCase().matches(REGEX_CURP)){
            String message = "La curp ingresada no es válida, revise el formato ".concat(curp);
            logger.info(message);
            throw new InvalidParametersException(message);
        }
        logger.info("La curp ingresada es válida, se continua con el flujo");
        return true;
    }
}
