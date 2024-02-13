package com.afdevelopment.biblioteca.exception.lent;

public class NoLentsFoundException extends RuntimeException{
    private final static String code = "EX-011";
    public NoLentsFoundException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}
