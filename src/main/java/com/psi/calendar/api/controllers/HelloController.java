package com.psi.calendar.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public ResponseEntity<String> handle() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello world!");
    }
}
