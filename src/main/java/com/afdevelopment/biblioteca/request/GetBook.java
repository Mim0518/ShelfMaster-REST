package com.afdevelopment.biblioteca.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class GetBook {
    @Positive(message = "Id must be a positive number")
    private Integer id;

    @Pattern(regexp = "^(97([89]))?\\d{9}(\\d|X)$", message = "ISBN format is invalid")
    private String isbn;

    @Size(min = 1, max = 255, message = "Author must be between 1 and 255 characters")
    private String author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
