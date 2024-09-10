package com.afdevelopment.biblioteca.controller;

import com.afdevelopment.biblioteca.model.Lent;
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
    private static final Logger logger = LoggerFactory.getLogger(LentController.class);

    private final LentService lentService;

    public LentController(LentService lentService) {
        this.lentService = lentService;
    }

    @GetMapping("/user/{Id}")
    public ResponseEntity<Map<String, Object>> getLentsByUserId(@PathVariable Integer Id){
        logger.info("Inicia controlador de búsqueda de préstamos por id de usuario");
        List<Lent> lents = lentService.findByUserId(Id);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, lents);
        logger.info("Finaliza controlador de búsqueda de préstamos por id de usuario");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @GetMapping("/id/{Id}")
    public ResponseEntity<Map<String, Object>> getLentsByLentId(@PathVariable Integer Id){
        logger.info("Inicia controlador de búsqueda de préstamos por id del préstamo");
        Lent lent = lentService.findByLentId(Id);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, lent);
        logger.info("Finaliza controlador de búsqueda de préstamos por id del préstamo");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> lentBook(@RequestBody GetLent lent){
        logger.info("Inicia controlador de guardado de préstamos");
        Lent lentResponse = lentService.lentBook(lent);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, lentResponse);
        logger.info("Finaliza controlador de guardado de préstamos");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
    @PostMapping("/return")
    public ResponseEntity<Map<String, Object>> returnBook(@RequestBody GetLent lent){
        logger.info("Inicia controlador de regreso de préstamos");
        Lent lentResponse = lentService.returnBook(lent);
        DetailResponse responseOk = new DetailResponse();
        responseOk.setCode(SUCCESSCODE);
        responseOk.setBussinessMeaning(OPCORRECTA);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put(DETAIL, responseOk);
        jsonResponse.put(RESPONSEDETAIL, lentResponse);
        logger.info("Finaliza controlador de regreso de préstamos");
        return (new ResponseEntity<>(jsonResponse, new HttpHeaders(), HttpStatus.OK));
    }
}
