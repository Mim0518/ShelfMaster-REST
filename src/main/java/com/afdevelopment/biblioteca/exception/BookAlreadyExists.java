package com.afdevelopment.biblioteca.exception;

public class BookAlreadyExists extends RuntimeException{
    private final static String code = "EX-002";
    public BookAlreadyExists(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }

}
