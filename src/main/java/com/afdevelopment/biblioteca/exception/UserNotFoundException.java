package com.afdevelopment.biblioteca.exception;

public class UserNotFoundException extends RuntimeException{
    private final static String code = "EX-005";
    public UserNotFoundException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}
