package com.afdevelopment.biblioteca.exception;

public class UserAlreadyExistsException extends RuntimeException{
    private final static String code = "EX-004";
    public UserAlreadyExistsException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}
