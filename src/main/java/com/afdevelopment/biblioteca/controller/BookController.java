package com.afdevelopment.biblioteca.controller;

import com.afdevelopment.biblioteca.response.BookResponse;
import com.afdevelopment.biblioteca.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/id/{bookId}")
    public BookResponse bookById(@PathVariable Integer bookId){
        BookResponse bookResponse = new BookResponse();
        bookResponse.setBook(bookService.findById(bookId));
        bookResponse.setMessage("Encontrao");
        return bookResponse;
    }
}
