package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.exception.book.BookNotFoundException;
import com.afdevelopment.biblioteca.exception.lent.NoLentsFoundException;
import com.afdevelopment.biblioteca.model.Book;
import com.afdevelopment.biblioteca.model.Lent;
import com.afdevelopment.biblioteca.repository.LentMapper;
import com.afdevelopment.biblioteca.repository.LentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LentService {
    private static final Logger logger = LoggerFactory.getLogger(LentService.class);

    private final LentRepository lentRepository;
    private final LentMapper lentMapper;

    public LentService(LentRepository lentRepository, LentMapper lentMapper) {
        this.lentRepository = lentRepository;
        this.lentMapper = lentMapper;
    }

    public List <Lent> findByUserId(Integer id){
        logger.info("Buscando prestamos para el usuario con Id: ".concat(id.toString()));
        List<Lent> foundLents = lentRepository.findAllByUserId(id);
        if (foundLents.isEmpty()){
            logger.error("Este usuario no tiene prestamos");
            throw new NoLentsFoundException("No se encontraron prestamos para el usuario con Id ".concat(id.toString()));
        }
        logger.info("Se encontraron ".concat(String.valueOf(foundLents.size()))
                .concat("prestamos para el usuario con id ").concat(id.toString()));
        return foundLents;
    }
}
