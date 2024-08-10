package com.afdevelopment.biblioteca.exception.auth;

public class RequiredDataException extends RuntimeException{
    private final static String code = "EX-016";
    public static String getCode(){
        return code;
    }
    public RequiredDataException(String message) {
        super(message);
    }
}