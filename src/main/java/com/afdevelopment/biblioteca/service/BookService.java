package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.exception.BookNotFoundException;
import com.afdevelopment.biblioteca.model.Book;
import com.afdevelopment.biblioteca.repository.BookRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.List;
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
            throw new BookNotFoundException("No se encontró el libro con Id ".concat(id.toString()));
        }
        logger.info("Se encontró el libro ".concat(foundBook.getTitle())
                .concat(" del autor ").concat(foundBook.getAuthor()));
        return foundBook;
    }

    public List<Book> findAllByAuthor(String author){
        logger.info("Buscando libros escritos por ".concat(author));
        List<Book> books = null;
        books = bookRepository.findAllByAuthor(author);
        if(books.isEmpty()){
            logger.error("No se encontraron libros escritos por ".concat(author));
            throw new BookNotFoundException("No se encontraron libros escritos por ".concat(author));
        }
        logger.info("Se encontraron ".concat(String.valueOf(books.size())).concat(" escritos por ").concat(author));
        return books;
    }
}
