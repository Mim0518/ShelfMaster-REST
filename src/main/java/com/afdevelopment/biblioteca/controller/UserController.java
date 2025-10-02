package com.afdevelopment.biblioteca.controller;


import com.afdevelopment.biblioteca.dto.UserDto;
import com.afdevelopment.biblioteca.model.User;
import com.afdevelopment.biblioteca.request.GetUser;
import com.afdevelopment.biblioteca.response.DetailResponse;
import com.afdevelopment.biblioteca.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
/**
 * REST controller for user management endpoints.
 */
public class UserController {
    private final String OPCORRECTA = "Operación correcta";
    private final String DETAIL = "detailResponse";
    private final String SUCCESSCODE = "SUC-002";
    private final String RESPONSEDETAIL = "userDetail";
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return a response entity with detail metadata and the created user
     */
    public ResponseEntity<Map<String, Object>> newUser(@RequestBody User user){
        logger.info("Inicia controlador de registro de usuarios");
        User userResponse = userService.saveUser(user);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, userResponse);
        logger.info("Finaliza controlador de registro de usuarios");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @GetMapping("/curp/{curp}")
    /**
     * Retrieves a user by CURP.
     *
     * @param curp the CURP identifier
     * @return a response entity with detail metadata and the user
     */
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable String curp){
        logger.info("Inicia controlador de búsqueda de usuarios por curp");
        User user = userService.findByCURP(curp);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, user);
        logger.info("Finaliza controlador de búsqueda de usuarios por curp");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @PostMapping("/delete/curp")
    /**
     * Deletes a user by CURP.
     *
     * @param getUser payload containing the CURP
     * @return a response entity with detail metadata and a confirmation message
     */
    public ResponseEntity<Map<String, Object>> deleteUserByCurp(@RequestBody GetUser getUser){
        logger.info("Inicia controlador de eliminación de usuarios");
        String userResponse = userService.deleteUserByCurp(getUser);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, userResponse);
        logger.info("Finaliza controlador de eliminación de usuarios");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @PatchMapping("/update")
    /**
     * Partially updates a user using the provided DTO fields.
     *
     * @param userDto the DTO with fields to update
     * @return a response entity with detail metadata and the updated user
     */
    public ResponseEntity<Map<String, Object>> patchUser(@RequestBody UserDto userDto){
        logger.info("Inicia controlador de actualización de usuarios");
        User userResponse = userService.patchUser(userDto);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, userResponse);
        logger.info("Finaliza controlador de actualización de usuarios");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
}
