package com.afdevelopment.biblioteca.exception.lent;

public class ForeignKeysNotExistentException extends RuntimeException{
    private final static String code = "EX-012";
    public ForeignKeysNotExistentException(String message){
        super(message);
    }
    public static String getCode(){
        return code;
    }
}

