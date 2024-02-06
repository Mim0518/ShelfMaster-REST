package com.afdevelopment.biblioteca.service;


import com.afdevelopment.biblioteca.dto.UserDto;
import com.afdevelopment.biblioteca.exception.book.BookNotFoundException;
import com.afdevelopment.biblioteca.exception.generic.InvalidParametersException;
import com.afdevelopment.biblioteca.exception.user.UserAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.user.UserKeysNotInRequestException;
import com.afdevelopment.biblioteca.exception.user.UserNotFoundException;
import com.afdevelopment.biblioteca.model.User;
import com.afdevelopment.biblioteca.repository.UserMapper;
import com.afdevelopment.biblioteca.repository.UserRepository;
import com.afdevelopment.biblioteca.request.GetUser;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    final UserRepository userRepository;
    final UserMapper userMapper;
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User saveUser(User user){
        logger.info("Guardando al usuario ".concat(user.getNombre()).concat("con CURP ").concat(user.getCurp()));
        User userResponse;
        if(curpIsInvalid(user.getCurp())){
            throw new InvalidParametersException("La curp ingresada no es válida");
        }
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
        if(curpIsInvalid(curp)){
            throw new InvalidParametersException("La curp ingresada no es válida");
        }
        foundUser = userRepository.findUserByCurp(curp);
        if (foundUser == null){
            logger.error("Usuario no encontrado");
            throw new UserNotFoundException("No se encontró el usuario con CURP ".concat(curp));
        }
        logger.info("Se encontró el usuario ".concat(foundUser.getNombre())
                .concat("con CURP ").concat(foundUser.getCurp()));
        return foundUser;
    }
    @Transactional
    public String deleteUserByCurp(GetUser getUser){
        logger.info("Eliminando el usuario con CURP ".concat(getUser.getCurp()));
        if(getUser.getCurp().isEmpty()){
            throw new InvalidParametersException("El parametro curp no puede estar vacío");
        }
        User isInDB = userRepository.findUserByCurp(getUser.getCurp());
        if(isInDB == null){
            logger.info("Algo ocurrió, el usuario con CURP ".concat(getUser.getCurp())
                    .concat(" no se encuentra en la base de datos"));
            throw new BookNotFoundException("Algo ocurrió, el usuario con CURP ".concat(getUser.getCurp())
                    .concat(" no se encuentra en la base de datos"));
        }else{
            userRepository.deleteUserByCurp(getUser.getCurp());
            return "Usuario eliminado con éxito";
        }
    }
    public User patchUser(UserDto userDto){
        User toPatch;
        if (curpIsInvalid(userDto.getCurp())){
            throw new InvalidParametersException("La curp ingresada no es válida");
        }
        if (userDto.getId() != null) {
            toPatch =  userRepository.findUserById(userDto.getId());
        } else {
            throw new UserKeysNotInRequestException("El Id del usuario a actualizar no puede ser nulo");
        }
        if(toPatch == null){
            throw new UserNotFoundException("El usuario con CURP ".concat(userDto.getCurp()).concat(" no existe"));
        }
        userMapper.updateUserFromDto(userDto, toPatch);
        userRepository.save(toPatch);
        return toPatch;
    }
    private boolean curpIsInvalid(String curp){
        final String REGEX_CURP =
                "^[A-Z]{1}[AEIOUX]{1}[A-Z]{2}((\\d{2}((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01])|(0[13-9]|1[0-2])" +
                        "(0[1-9]|[12][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8])))|([02468][048]|[13579][26])0229)[HM]{1}" +
                        "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
                        "[B-DF-HJ-NP-TV-Z]{3}[0-9A-Z]{1}[0-9]$";
        if(!curp.toUpperCase().matches(REGEX_CURP)){
            String message = "La curp ingresada no es válida, revise el formato ".concat(curp);
            logger.info(message);
            return true;
        }
        logger.info("La curp ingresada es válida, se continua con el flujo");
        return false;
    }
}
