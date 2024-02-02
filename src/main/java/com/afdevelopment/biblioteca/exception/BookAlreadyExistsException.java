package com.afdevelopment.biblioteca.exception;

public class BookAlreadyExistsException extends RuntimeException{
    private final static String code = "EX-002";
    public BookAlreadyExistsException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }

}
