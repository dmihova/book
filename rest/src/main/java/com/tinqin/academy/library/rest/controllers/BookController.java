package com.tinqin.academy.library.rest.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class BookController {


    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }
}
