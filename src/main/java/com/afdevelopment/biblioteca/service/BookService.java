package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.exception.BookNotFoundException;
import com.afdevelopment.biblioteca.model.Book;
import com.afdevelopment.biblioteca.repository.BookRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.NoSuchElementException;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    BookRepository bookRepository;


    public Book findById(Integer id){
        logger.info("Buscando libro con Id: ".concat(id.toString()));
        Book foundBook = bookRepository.findBookById(id);
        if (foundBook == null){
            logger.error("Libro no encontrado");
            logger.error("To va tal bien papi");
            throw new BookNotFoundException("No se encontró el libro con Id ".concat(id.toString()));
        }
        logger.info("Se encontró el libro ".concat(foundBook.getTitle())
                .concat(" del autor ").concat(foundBook.getAuthor()));
        return foundBook;
    }
}
