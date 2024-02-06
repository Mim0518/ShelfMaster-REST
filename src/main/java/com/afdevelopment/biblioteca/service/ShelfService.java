package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.repository.ShelfMapper;
import com.afdevelopment.biblioteca.repository.ShelfRepository;
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


}
