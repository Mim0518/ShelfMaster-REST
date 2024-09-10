package com.afdevelopment.biblioteca.controller;

import com.afdevelopment.biblioteca.dto.ShelfDto;
import com.afdevelopment.biblioteca.model.Shelf;
import com.afdevelopment.biblioteca.request.GetShelf;
import com.afdevelopment.biblioteca.response.DetailResponse;
import com.afdevelopment.biblioteca.service.ShelfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/shelf")
@RestController
public class ShelfController {
    private final String OPCORRECTA = "Operación correcta";
    private final String DETAIL = "detailResponse";
    private final String SUCCESSCODE = "SUC-003";
    private final String RESPONSEDETAIL = "shelfDetail";
    private final ShelfService shelfService;
    private static final Logger logger = LoggerFactory.getLogger(ShelfController.class);
    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> newUser(@RequestBody Shelf shelf){
        logger.info("Inicia controlador de registro de estantes");
        Shelf shelfResponse = shelfService.saveShelf(shelf);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, shelfResponse);
        logger.info("Finaliza controlador de registro de estantes");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @GetMapping("/id/{Id}")
    public ResponseEntity<Map<String, Object>> getShelf(@PathVariable Integer Id){
        logger.info("Inicia controlador de búsqueda de estante por id");
        Shelf shelf = shelfService.findById(Id);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, shelf);
        logger.info("Finaliza controlador de búsqueda de estante por id");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @PostMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteShelfById(@RequestBody GetShelf getShelf){
        logger.info("Inicia controlador de eliminación de estantes");
        String shelfResponse = shelfService.deleteShelfById(getShelf);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, shelfResponse);
        logger.info("Finaliza controlador de eliminación de estantes");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @PatchMapping("/update")
    public ResponseEntity<Map<String, Object>> patchShelf(@RequestBody ShelfDto shelfDto){
        logger.info("Inicia controlador de actualización de estantes");
        Shelf shelfResponse = shelfService.patchShelf(shelfDto);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, shelfResponse);
        logger.info("Finaliza controlador de actualización de estantes");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
}
