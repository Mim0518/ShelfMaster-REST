package com.afdevelopment.biblioteca.exception;

public class BookNotFoundException extends RuntimeException{
    private final static String code = "EX-001";
    public BookNotFoundException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}
