package com.afdevelopment.biblioteca.controller;

import com.afdevelopment.biblioteca.dto.BookDto;
import com.afdevelopment.biblioteca.model.Book;
import com.afdevelopment.biblioteca.request.GetBook;
import com.afdevelopment.biblioteca.response.DetailResponse;
import com.afdevelopment.biblioteca.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
/**
 * REST controller exposing endpoints to manage books in the library.
 * <p>
 * Provides operations to retrieve, create, update and delete books.
 * </p>
 */
public class BookController {
    private final String OPCORRECTA = "Operación correcta";
    private final String DETAIL = "detailResponse";
    private final String SUCCESSCODE = "SUC-001";
    private final String RESPONSEDETAIL = "bookDetail";
    private final BookService bookService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/id/{bookId}")
    /**
     * Retrieves a book by its internal identifier.
     *
     * @param bookId the unique identifier of the book
     * @return a response entity containing detail metadata and the book information
     */
    public ResponseEntity<Map<String, Object>> bookById(@PathVariable Integer bookId){
        logger.info("Inicia controlador de búsqueda de libros por id");
        Book book = bookService.findById(bookId);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, book);
        logger.info("Finaliza controlador de búsqueda de libros por id");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @GetMapping("/isbn/{bookISBN}")
    /**
     * Retrieves a book by its ISBN.
     *
     * @param bookISBN the ISBN of the book
     * @return a response entity containing detail metadata and the book information
     */
    public ResponseEntity<Map<String, Object>> bookByISBN(@PathVariable String bookISBN){
        logger.info("Inicia controlador de búsqueda de libros por ISBN");
        Book book = bookService.findByISBN(bookISBN);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, book);
        logger.info("Finaliza controlador de búsqueda de libros por ISBN");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @PostMapping("/author")
    /**
     * Finds all books by the given author.
     *
     * @param getBook request payload containing the author name
     * @return a response entity with detail metadata and the list of books
     */
    public ResponseEntity<Map<String, Object>> booksByAuthor(@RequestBody GetBook getBook){
        logger.info("Inicia controlador de búsqueda de libros por autor");
        List<Book> books = bookService.findAllByAuthor(getBook.getAuthor());
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, books);
        logger.info("Finaliza controlador de búsqueda de libros por autor");
        return new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK);
    }
    @PostMapping("/new")
    /**
     * Creates a new book.
     *
     * @param book the book to create
     * @return a response entity with detail metadata and the created book
     */
    public ResponseEntity<Map<String, Object>> saveBook(@RequestBody Book book){
        logger.info("Inicia controlador de guardado de libros");
        Book bookResponse = bookService.saveBook(book);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, bookResponse);
        logger.info("Finaliza controlador de guardado de libros");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @PostMapping("/delete/isbn")
    /**
     * Deletes a book by its ISBN.
     *
     * @param getBook request payload containing the ISBN
     * @return a response entity with detail metadata and a confirmation message
     */
    public ResponseEntity<Map<String, Object>> deleteBookByISBN(@RequestBody GetBook getBook){
        logger.info("Inicia controlador de eliminación de libros por ISBN");
        String bookResponse = bookService.deleteBookByISBN(getBook.getIsbn());
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, bookResponse);
        logger.info("Finaliza controlador de eliminación de libros por ISBN");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @PostMapping("/delete/id")
    /**
     * Deletes a book by its internal identifier.
     *
     * @param getBook request payload containing the ID
     * @return a response entity with detail metadata and a confirmation message
     */
    public ResponseEntity<Map<String, Object>> deleteBookById(@RequestBody GetBook getBook){
        logger.info("Inicia controlador de eliminación de libros por ID");
        String bookResponse = bookService.deleteBookById(getBook.getId());
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, bookResponse);
        logger.info("Finaliza controlador de eliminación de libros por ID");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @PatchMapping("/update")
    /**
     * Partially updates a book using the provided DTO fields.
     *
     * @param bookDto the DTO with fields to update
     * @return a response entity with detail metadata and the updated book
     */
    public ResponseEntity<Map<String, Object>> patchBook(@RequestBody BookDto bookDto){
        logger.info("Inicia controlador de actualización de libros");
        Book bookResponse = bookService.patchBook(bookDto);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, bookResponse);
        logger.info("Finaliza controlador de actualización de libros");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @GetMapping("/all")
    /**
     * Retrieves all books in the catalog.
     *
     * @return a response entity with detail metadata and the list of all books
     */
    public ResponseEntity<Map<String, Object>> findAllBooks(){
        logger.info("Inicia controlador de búsqueda general de libros");
        List<Book> allBooks = bookService.findAll();
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, allBooks);
        logger.info("Finaliza controlador de búsqueda general de libros");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
}
