package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.exception.BookAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.BookNotFoundException;
import com.afdevelopment.biblioteca.model.Book;
import com.afdevelopment.biblioteca.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.List;

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
    public Book findByISBN(String isbn){
        logger.info("Buscando libro con ISBN: ".concat(isbn));
        Book foundBook = bookRepository.findBookByIsbn(isbn);
        if (foundBook == null){
            logger.error("Libro no encontrado");
            throw new BookNotFoundException("No se encontró el libro con ISBN ".concat(isbn));
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

    public Book saveBook(Book book){
        logger.info("Guardando el libro ".concat(book.getTitle()).concat(" de ").concat(book.getAuthor()));
        Book responseBook = null;
        try{
            responseBook = bookRepository.save(book);
        } catch (Exception e) {
            throw new BookAlreadyExistsException("El libro con ISBN ".concat(book.getIsbn()).concat(" ya está registrado"));
        }
        return responseBook;
    }
    @Transactional
    public String deleteBookByISBN(String isbn){
        logger.info("Eliminando el libro con ISBN ".concat(isbn));
        try {
            bookRepository.deleteBookByIsbn(isbn);
            return "Libro eliminado";
        }catch (Exception e){
            throw new BookNotFoundException("Algo ocurrió, el libro con ISBN ".concat(isbn).concat(" no pudo ser eliminado"));
        }
    }
}
