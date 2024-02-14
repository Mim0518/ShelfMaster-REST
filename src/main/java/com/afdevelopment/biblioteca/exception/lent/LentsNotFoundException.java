package com.afdevelopment.biblioteca.exception.lent;

public class LentsNotFoundException extends RuntimeException{
    private final static String code = "EX-011";
    public LentsNotFoundException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}
