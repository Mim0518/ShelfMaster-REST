package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.dto.BookDto;
import com.afdevelopment.biblioteca.exception.book.BookAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.book.BookKeysNotInRequestException;
import com.afdevelopment.biblioteca.exception.book.BookNotFoundException;
import com.afdevelopment.biblioteca.exception.generic.InvalidParametersException;
import com.afdevelopment.biblioteca.model.Book;
import com.afdevelopment.biblioteca.repository.BookMapper;
import com.afdevelopment.biblioteca.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.List;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    final BookRepository bookRepository;
    final BookMapper bookMapper;
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }
    public Book findById(Integer id){
        logger.info("Buscando libro con Id: ".concat(id.toString()));
        Book foundBook = bookRepository.findBookById(id);
        CommonServiceUtils.ensureFound(foundBook, () -> {
            logger.error("Libro con id {} no encontrado", id);
            return new BookNotFoundException("No se encontró el libro con Id ".concat(id.toString()));
        });
        logger.info("Se encontró el libro ".concat(foundBook.getTitle())
                .concat(" del autor ").concat(foundBook.getAuthor()));
        return foundBook;
    }
    public Book findByISBN(String isbn){
        if(isbnIsInvalid(isbn))
            throw new InvalidParametersException("El ISBN enviado no es válido");
        logger.info("Buscando libro con ISBN: ".concat(isbn));
        Book foundBook = bookRepository.findBookByIsbn(isbn);
        CommonServiceUtils.ensureFound(foundBook, () -> {
            logger.error("Libro con ISBN {} no encontrado", isbn);
            return new BookNotFoundException("No se encontró el libro con ISBN ".concat(isbn));
        });
        logger.info("Se encontró el libro ".concat(foundBook.getTitle())
                .concat(" del autor ").concat(foundBook.getAuthor()));
        return foundBook;
    }
    public List<Book> findAllByAuthor(String author){
        logger.info("Buscando libros escritos por ".concat(author));
        List<Book> books = bookRepository.findAllByAuthor(author);
        if(books.isEmpty()){
            logger.error("No se encontraron libros escritos por ".concat(author));
            throw new BookNotFoundException("No se encontraron libros escritos por ".concat(author));
        }
        logger.info("Se encontraron ".concat(String.valueOf(books.size())).concat(" escritos por ").concat(author));
        return books;
    }
    public Book saveBook(Book book){
        logger.info("Guardando el libro ".concat(book.getTitle()).concat(" de ").concat(book.getAuthor()));
        Book responseBook;
        try{
            if(isbnIsInvalid(book.getIsbn()))
                throw new InvalidParametersException("El ISBN del libro a guardar no es válido");
            responseBook = bookRepository.save(book);
        } catch (Exception e) {
            throw new BookAlreadyExistsException("El libro con ISBN ".concat(book.getIsbn())
                    .concat(" ya está registrado"));
        }
        return responseBook;
    }
    @Transactional
    public String deleteBookByISBN(String isbn){
        if(isbnIsInvalid(isbn))
            throw new InvalidParametersException("El ISBN enviado no es válido");
        logger.info("Eliminando el libro con ISBN ".concat(isbn));
        Book isInDB = bookRepository.findBookByIsbn(isbn);
        CommonServiceUtils.ensureFound(isInDB, () -> new BookNotFoundException(
                "Algo ocurrió, el libro con ISBN ".concat(isbn)
                        .concat(" no pudo ser eliminado")));
        bookRepository.deleteBookByIsbn(isbn);
        return "Libro con ISBN "+isbn+" eliminado";
    }
    @Transactional
    public String deleteBookById(Integer Id){
        logger.info("Eliminando el libro con ID ".concat(Id.toString()));
        Book isInDB = bookRepository.findBookById(Id);
        CommonServiceUtils.ensureFound(isInDB, () -> {
            String message = "Algo ocurrió, el libro con ID ".concat(Id.toString())
                    .concat(" no está en la base de datos y no puede ser eliminado");
            logger.info(message);
            return new BookNotFoundException(message);
        });
        bookRepository.deleteBookById(Id);
        logger.info("Libro eliminado");
        return "Libro con ID "+Id+" eliminado";
    }
    public Book patchBook(BookDto bookDto){
        if(isbnIsInvalid(bookDto.getIsbn()))
            throw new InvalidParametersException("El ISBN actualizado no es válido");
        Book toPatch;
        if (bookDto.getId() != null) {
            toPatch =  bookRepository.findBookById(bookDto.getId());
        } else if (bookDto.getIsbn() != null) {
            toPatch =  bookRepository.findBookByIsbn(bookDto.getIsbn());
        } else {
            throw new BookKeysNotInRequestException("Ambos, ID e ISBN, no pueden ser nulos");
        }
        CommonServiceUtils.ensureFound(toPatch, () -> new BookNotFoundException("No se encontró el libro a actualizar"));
        bookMapper.updateBookFromDto(bookDto, toPatch);
        bookRepository.save(toPatch);
        return toPatch;
    }
    public List<Book> findAll() {
        List<Book> allBooks = bookRepository.findAll();
        if(allBooks.isEmpty()) throw new BookNotFoundException("No existen libros registrados");
        return allBooks;
    }

    private boolean isbnIsInvalid(String isbn){
        final String REGEX_ISBN="^(97([89]))?\\d{9}(\\d|X)$";
        CommonServiceUtils.requireNonNull(isbn, "isbn");
        CommonServiceUtils.requireNotBlank(isbn, "isbn");
        if (!isbn.matches(REGEX_ISBN)){
            String message = "El ISBN ingresado no es válido, revise el formato ".concat(isbn);
            logger.info(message);
            return true;
        }
        logger.info("El ISBN ingresado es válido, se continua con el flujo");
        return false;
    }
}
