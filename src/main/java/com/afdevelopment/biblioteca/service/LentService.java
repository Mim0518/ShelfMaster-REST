package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.exception.generic.InvalidParametersException;
import com.afdevelopment.biblioteca.exception.lent.BookAlreadyLentException;
import com.afdevelopment.biblioteca.exception.lent.ForeignKeysNotExistentException;
import com.afdevelopment.biblioteca.exception.lent.LentsNotFoundException;
import com.afdevelopment.biblioteca.model.Lent;
import com.afdevelopment.biblioteca.repository.BookRepository;
import com.afdevelopment.biblioteca.repository.LentMapper;
import com.afdevelopment.biblioteca.repository.LentRepository;
import com.afdevelopment.biblioteca.request.GetLent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LentService {
    private static final Logger logger = LoggerFactory.getLogger(LentService.class);

    private final LentRepository lentRepository;
    private final LentMapper lentMapper;

    private final BookRepository bookRepository;
    public LentService(LentRepository lentRepository, LentMapper lentMapper, BookRepository bookRepository) {
        this.lentRepository = lentRepository;
        this.lentMapper = lentMapper;
        this.bookRepository = bookRepository;
    }
    public Lent saveLent(GetLent lent){
        logger.info("Guardando el préstamo para el usuario con id ".concat(lent.getUserId().toString()));
        Lent responseLent = new Lent();
        if (lent.getBookId() == null || lent.getUserId() == null)
            throw new InvalidParametersException("No puede haber parámetros nulos en el body de la petición");
        responseLent.setBookId(lent.getBookId());
        responseLent.setUserId(lent.getUserId());
        responseLent.setActive(true);
        responseLent.setDate(String.valueOf(LocalDate.now()));
        if(bookRepository.isBookLent(lent.getBookId()))
            throw new BookAlreadyLentException("Este libro ya está prestado");
        try {
            responseLent = lentRepository.save(responseLent);
            bookRepository.setBookAsLent(lent.getBookId());
        } catch (DataIntegrityViolationException e){
            throw new ForeignKeysNotExistentException("El libro o usuario ingresados no son válidos");
        }
        return responseLent;
    }
    public List <Lent> findByUserId(Integer id){
        logger.info("Buscando prestamos para el usuario con Id: ".concat(id.toString()));
        List<Lent> foundLents = lentRepository.findAllByUserId(id);
        if (foundLents.isEmpty()){
            logger.error("Este usuario no tiene prestamos");
            throw new LentsNotFoundException("No se encontraron prestamos para el usuario con Id ".concat(id.toString()));
        }
        logger.info("Se encontraron ".concat(String.valueOf(foundLents.size()))
                .concat("prestamos para el usuario con id ").concat(id.toString()));
        return foundLents;
    }
}
