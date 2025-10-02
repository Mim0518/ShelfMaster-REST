package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.dto.ShelfDto;
import com.afdevelopment.biblioteca.exception.book.BookNotFoundException;
import com.afdevelopment.biblioteca.exception.generic.InvalidParametersException;
import com.afdevelopment.biblioteca.exception.shelf.ShelfAlreadyExistsException;
import com.afdevelopment.biblioteca.exception.shelf.ShelfKeyesNotInRequestException;
import com.afdevelopment.biblioteca.exception.shelf.ShelfNotFoundException;
import com.afdevelopment.biblioteca.model.Shelf;
import com.afdevelopment.biblioteca.repository.ShelfMapper;
import com.afdevelopment.biblioteca.repository.ShelfRepository;
import com.afdevelopment.biblioteca.request.GetShelf;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ShelfService {
    private static final Logger logger = LoggerFactory.getLogger(ShelfService.class);

    final ShelfRepository shelfRepository;

    final ShelfMapper shelfMapper;

    public ShelfService(ShelfRepository shelfRepository, ShelfMapper shelfMapper) {
        this.shelfRepository = shelfRepository;
        this.shelfMapper = shelfMapper;
    }

    public Shelf saveShelf(Shelf shelf){
        logger.info("Guardando estante");
        Shelf shelfResponse;
        if (shelf.getId() != null){
            if(shelfRepository.existsById(shelf.getId())) throw new ShelfAlreadyExistsException("Este estante ya está registrado");
        }
        if (shelf.getLocation() ==  null || shelf.getLocation().isEmpty()){
            logger.info("La localización del estante no puede estar vacia");
            throw new InvalidParametersException("La localización del estante no puede estar vacia");
        }
        try{
            shelfResponse = shelfRepository.save(shelf);
        } catch (Exception e){
            logger.info("Algo pasó");
            throw new RuntimeException(e.getMessage());
        }
        return shelfResponse;
    }
    public Shelf findById(Integer id){
        logger.info("Buscando estante con Id: ".concat(id.toString()));
        Shelf foundShelf = shelfRepository.findShelfById(id);
        CommonServiceUtils.ensureFound(foundShelf, () -> {
            logger.error("Estante no encontrado");
            return new ShelfNotFoundException("No se encontró el estante con Id ".concat(id.toString()));
        });
        logger.info("Se encontró el estante ".concat(foundShelf.getLocation()));
        return foundShelf;
    }
    @Transactional
    public String deleteShelfById(GetShelf getShelf){
        logger.info("Eliminando el estante con Id ".concat(getShelf.getId().toString()));
        CommonServiceUtils.requireNotBlank(getShelf.getId().toString(), "id");
        CommonServiceUtils.requireNonNull(getShelf.getId().toString(), "id");

        Shelf isInDB = shelfRepository.findShelfById(getShelf.getId());
        CommonServiceUtils.ensureFound(isInDB, () -> new BookNotFoundException(
                "Algo ocurrió, el estante con ID ".concat(getShelf.getId().toString())
                        .concat(" no se encuentra en la base de datos")));
        shelfRepository.deleteShelfById(getShelf.getId());
        return "Estante con Id "+getShelf.getId()+" eliminado con éxito";
    }
    public Shelf patchShelf(ShelfDto shelfDto){
        Shelf toPatch;
        if (shelfDto.getId() != null) {
            toPatch =  shelfRepository.findShelfById(shelfDto.getId());
        } else {
            throw new ShelfKeyesNotInRequestException("El Id del estante a actualizar no puede ser nulo");
        }
        CommonServiceUtils.ensureFound(toPatch, () -> new ShelfNotFoundException("El estante con Id ".concat(shelfDto.getId().toString()).concat(" no existe")));
        shelfMapper.updateShelfFromDto(shelfDto, toPatch);
        shelfRepository.save(toPatch);
        return toPatch;
    }
}
