package com.afdevelopment.biblioteca.repository;


import com.afdevelopment.biblioteca.model.Book;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BookRepository extends CrudRepository<Book, Integer> {
    Book findBookById(Integer id);
    Book findBookByIsbn(String ISBN);
    List<Book> findAllByAuthor(String author);
    void deleteBookByIsbn(String Isbn);
    void deleteBookById(Integer Id);
    @Nonnull
    List<Book> findAll();
    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.lent = true WHERE b.id = :id")
    void setBookAsLent(@Param("id") Integer id);
    @Query("SELECT b.lent FROM Book b where b.id = :id")
    boolean isBookLent(@Param("id") Integer id);
}
