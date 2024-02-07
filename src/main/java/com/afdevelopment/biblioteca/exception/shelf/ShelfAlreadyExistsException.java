package com.afdevelopment.biblioteca.exception.shelf;

public class ShelfAlreadyExistsException extends RuntimeException{
    private final static String code = "EX-008";
    public ShelfAlreadyExistsException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}
