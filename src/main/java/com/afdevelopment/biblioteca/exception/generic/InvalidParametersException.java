package com.afdevelopment.biblioteca.exception.generic;

public class InvalidParametersException extends RuntimeException{
    private final static String code = "EX-006";
    public InvalidParametersException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}
