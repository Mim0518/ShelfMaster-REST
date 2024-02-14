package com.afdevelopment.biblioteca.exception.lent;

public class BookAlreadyLentException extends RuntimeException{
    private final static String code = "EX-013";
    public BookAlreadyLentException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}
