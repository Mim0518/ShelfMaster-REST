package com.afdevelopment.biblioteca.controller;

import com.afdevelopment.biblioteca.exception.book.BookAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.book.BookKeysNotInRequestException;
import com.afdevelopment.biblioteca.exception.book.BookNotFoundException;
import com.afdevelopment.biblioteca.exception.generic.InvalidParametersException;
import com.afdevelopment.biblioteca.exception.shelf.ShelfAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.shelf.ShelfKeyesNotInRequestException;
import com.afdevelopment.biblioteca.exception.shelf.ShelfNotFoundException;
import com.afdevelopment.biblioteca.exception.user.UserAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.user.UserKeysNotInRequestException;
import com.afdevelopment.biblioteca.exception.user.UserNotFoundException;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException e){
        Map<String,Object> jsonResponse = new HashMap<>();
        DetailFail failResponse = new DetailFail();
        Error error = new Error();
        error.setBussinessMeaning(e.getMessage());
        error.setCode(UserNotFoundException.getCode());
        ArrayList errores = new ArrayList<>();
        errores.add(error);
        failResponse.setErrors(errores);
        jsonResponse.put(DETAIL_FAIL, failResponse);
        logger.info(UserNotFoundException.getCode().concat(" --> ").concat(e.getMessage()));
        return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParametersException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidParametersException(InvalidParametersException e){
        Map<String,Object> jsonResponse = new HashMap<>();
        DetailFail failResponse = new DetailFail();
        Error error = new Error();
        error.setBussinessMeaning(e.getMessage());
        error.setCode(InvalidParametersException.getCode());
        ArrayList errores = new ArrayList<>();
        errores.add(error);
        failResponse.setErrors(errores);
        jsonResponse.put(DETAIL_FAIL, failResponse);
        logger.info(InvalidParametersException.getCode().concat(" --> ").concat(e.getMessage()));
        return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserKeysNotInRequestException.class)
    public ResponseEntity<Map<String, Object>> handleUserKeysNotInRequestException(UserKeysNotInRequestException e){
        Map<String,Object> jsonResponse = new HashMap<>();
        DetailFail failResponse = new DetailFail();
        Error error = new Error();
        error.setBussinessMeaning(e.getMessage());
        error.setCode(UserKeysNotInRequestException.getCode());
        ArrayList errores = new ArrayList<>();
        errores.add(error);
        failResponse.setErrors(errores);
        jsonResponse.put(DETAIL_FAIL, failResponse);
        logger.info(UserKeysNotInRequestException.getCode().concat(" --> ").concat(e.getMessage()));
        return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ShelfAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleShelfAlreadyExistsException(ShelfAlreadyExistsException e){
        Map<String,Object> jsonResponse = new HashMap<>();
        DetailFail failResponse = new DetailFail();
        Error error = new Error();
        error.setBussinessMeaning(e.getMessage());
        error.setCode(ShelfAlreadyExistsException.getCode());
        ArrayList errores = new ArrayList<>();
        errores.add(error);
        failResponse.setErrors(errores);
        jsonResponse.put(DETAIL_FAIL, failResponse);
        logger.info(ShelfAlreadyExistsException.getCode().concat(" --> ").concat(e.getMessage()));
        return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShelfNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleShelfNotFoundException(ShelfNotFoundException e){
        Map<String,Object> jsonResponse = new HashMap<>();
        DetailFail failResponse = new DetailFail();
        Error error = new Error();
        error.setBussinessMeaning(e.getMessage());
        error.setCode(ShelfNotFoundException.getCode());
        ArrayList errores = new ArrayList<>();
        errores.add(error);
        failResponse.setErrors(errores);
        jsonResponse.put(DETAIL_FAIL, failResponse);
        logger.info(ShelfNotFoundException.getCode().concat(" --> ").concat(e.getMessage()));
        return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ShelfKeyesNotInRequestException.class)
    public ResponseEntity<Map<String, Object>> handleShelfKeyesNotInRequestException(ShelfKeyesNotInRequestException e){
        Map<String,Object> jsonResponse = new HashMap<>();
        DetailFail failResponse = new DetailFail();
        Error error = new Error();
        error.setBussinessMeaning(e.getMessage());
        error.setCode(ShelfKeyesNotInRequestException.getCode());
        ArrayList errores = new ArrayList<>();
        errores.add(error);
        failResponse.setErrors(errores);
        jsonResponse.put(DETAIL_FAIL, failResponse);
        logger.info(ShelfKeyesNotInRequestException.getCode().concat(" --> ").concat(e.getMessage()));
        return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
