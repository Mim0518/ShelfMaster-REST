package com.afdevelopment.biblioteca.exception;

public class UserKeysNotInRequestException extends RuntimeException{
    private final static String code = "EX-007";
    public UserKeysNotInRequestException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}
