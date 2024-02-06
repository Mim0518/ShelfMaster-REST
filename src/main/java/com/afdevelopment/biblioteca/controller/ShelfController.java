package com.afdevelopment.biblioteca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/shelf")
@RestController
public class ShelfController {
    private final String OPCORRECTA = "Operación correcta";
    private final String DETAIL = "detailResponse";
    private final String SUCCESSCODE = "SUC-003";
    private final String RESPONSEDETAIL = "shelfDetail";
    private static final Logger logger = LoggerFactory.getLogger(ShelfController.class);
}
