package com.afdevelopment.biblioteca.exception.shelf;

public class ShelfNotFoundException extends RuntimeException{
    private final static String code = "EX-009";
    public ShelfNotFoundException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}
