package com.afdevelopment.biblioteca.controller;

import com.afdevelopment.biblioteca.model.Book;
import com.afdevelopment.biblioteca.model.Lent;
import com.afdevelopment.biblioteca.model.Shelf;
import com.afdevelopment.biblioteca.request.GetLent;
import com.afdevelopment.biblioteca.response.DetailResponse;
import com.afdevelopment.biblioteca.service.LentService;
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
@RequestMapping("/lent")
public class LentController {
    private final String OPCORRECTA = "Operación correcta";
    private final String DETAIL = "detailResponse";
    private final String SUCCESSCODE = "SUC-004";
    private final String RESPONSEDETAIL = "lentDetail";
    private static final Logger logger = LoggerFactory.getLogger(LentService.class);

    private final LentService lentService;

    public LentController(LentService lentService) {
        this.lentService = lentService;
    }

    @GetMapping("/id/{Id}")
    public ResponseEntity<Map<String, Object>> getLentsById(@PathVariable Integer Id){
        logger.info("Inicia controlador de búsqueda de préstamos por id");
        List<Lent> lents = lentService.findByUserId(Id);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, lents);
        logger.info("Finaliza controlador de búsqueda de préstamos por id");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> saveLent(@RequestBody GetLent lent){
        logger.info("Inicia controlador de guardado de préstamos");
        Lent lentResponse = lentService.saveLent(lent);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, lentResponse);
        logger.info("Finaliza controlador de guardado de préstamos");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
}
