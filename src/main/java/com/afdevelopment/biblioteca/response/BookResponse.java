package com.afdevelopment.biblioteca.response;

import com.afdevelopment.biblioteca.model.Book;

public class BookResponse {
    private Book book;
    private String message;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
