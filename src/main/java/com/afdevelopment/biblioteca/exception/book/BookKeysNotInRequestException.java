package com.afdevelopment.biblioteca.exception.book;

public class BookKeysNotInRequestException extends RuntimeException{
    private final static String code = "EX-003";
    public BookKeysNotInRequestException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}