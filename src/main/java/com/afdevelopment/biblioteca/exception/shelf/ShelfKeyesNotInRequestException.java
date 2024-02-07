package com.afdevelopment.biblioteca.exception.shelf;

public class ShelfKeyesNotInRequestException extends RuntimeException{
    private final static String code = "EX-010";
    public ShelfKeyesNotInRequestException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}
