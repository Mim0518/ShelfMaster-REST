package com.afdevelopment.biblioteca.exception.Librarian;

public class LibrarianNotFoundException extends RuntimeException{
    private final static String code = "EX-016";
    public static String getCode(){
        return code;
    }
    public LibrarianNotFoundException(String message) {
        super(message);
    }
}
