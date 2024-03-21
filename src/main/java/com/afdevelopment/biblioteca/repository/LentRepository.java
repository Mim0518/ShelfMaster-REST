package com.afdevelopment.biblioteca.repository;

import com.afdevelopment.biblioteca.model.Lent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface LentRepository extends CrudRepository<Lent, Integer> {
    List<Lent> findAllByUserId(Integer userId);
    Lent findLentById(Integer id);
    @Query("SELECT CASE WHEN COUNT(l.id) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Lent l WHERE l.userId = :userId AND l.bookId = :bookId AND l.active = true")
    boolean lentExistsAndIsActive(@Param("bookId")Integer bookId, @Param("userId")Integer userId);
    @Modifying
    @Transactional
    @Query("UPDATE Lent l SET l.active = false WHERE l.userId = :userId and l.bookId = :bookId and l.active = true ")
    void returnBook(@Param("bookId")Integer bookId, @Param("userId")Integer userId);
    Lent findLentByUserIdAndBookId(Integer userId, Integer bookId);
}
