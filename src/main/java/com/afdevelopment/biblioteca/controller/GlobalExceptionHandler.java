package com.afdevelopment.biblioteca.controller;

import com.afdevelopment.biblioteca.exception.BookAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.BookKeysNotInRequestException;
import com.afdevelopment.biblioteca.exception.BookNotFoundException;
import com.afdevelopment.biblioteca.exception.UserAlreadyExistsException;
import com.afdevelopment.biblioteca.response.DetailFail;
import com.afdevelopment.biblioteca.response.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String DETAIL_FAIL = "detailFail";
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookNotFoundException(BookNotFoundException e){
        Map<String,Object> jsonResponse = new HashMap<>();
        DetailFail failResponse = new DetailFail();
        Error error = new Error();
        error.setBussinessMeaning(e.getMessage());
        error.setCode(BookNotFoundException.getCode());
        ArrayList errores = new ArrayList<>();
        errores.add(error);
        failResponse.setErrors(errores);
        jsonResponse.put(DETAIL_FAIL, failResponse);
        logger.info(BookNotFoundException.getCode().concat(" --> ").concat(e.getMessage()));
        return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleBookAlreadyExistsException(BookAlreadyExistsException e){
        Map<String,Object> jsonResponse = new HashMap<>();
        DetailFail failResponse = new DetailFail();
        Error error = new Error();
        error.setBussinessMeaning(e.getMessage());
        error.setCode(BookAlreadyExistsException.getCode());
        ArrayList errores = new ArrayList<>();
        errores.add(error);
        failResponse.setErrors(errores);
        jsonResponse.put(DETAIL_FAIL, failResponse);
        logger.info(BookAlreadyExistsException.getCode().concat(" --> ").concat(e.getMessage()));
        return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BookKeysNotInRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBookKeysNotInRequestException(BookKeysNotInRequestException e){
        Map<String,Object> jsonResponse = new HashMap<>();
        DetailFail failResponse = new DetailFail();
        Error error = new Error();
        error.setBussinessMeaning(e.getMessage());
        error.setCode(BookKeysNotInRequestException.getCode());
        ArrayList errores = new ArrayList<>();
        errores.add(error);
        failResponse.setErrors(errores);
        jsonResponse.put(DETAIL_FAIL, failResponse);
        logger.info(BookKeysNotInRequestException.getCode().concat(" --> ").concat(e.getMessage()));
        return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExistsException e){
        Map<String,Object> jsonResponse = new HashMap<>();
        DetailFail failResponse = new DetailFail();
        Error error = new Error();
        error.setBussinessMeaning(e.getMessage());
        error.setCode(UserAlreadyExistsException.getCode());
        ArrayList errores = new ArrayList<>();
        errores.add(error);
        failResponse.setErrors(errores);
        jsonResponse.put(DETAIL_FAIL, failResponse);
        logger.info(UserAlreadyExistsException.getCode().concat(" --> ").concat(e.getMessage()));
        return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }
}
