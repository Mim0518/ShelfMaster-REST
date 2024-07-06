package com.afdevelopment.biblioteca.exception.Librarian;

public class LibrarianAlreadyExistsException extends RuntimeException{
    private final static String code = "EX-014";
    public static String getCode(){
        return code;
    }
    public LibrarianAlreadyExistsException(String message) {
        super(message);
    }
}
