package com.crawler.buscador.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
//Esto captura todas las excepciones en la aplicación y las formatea adecuadamente.
public class GlobalExceptionHandler {
    @ExceptionHandler(ScraperException.class)
    //i hay alguna excepcion al hacer el scrapring
    public ResponseEntity<Map<String, Object>> handleScraperException(ScraperException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Scraping failed");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
//excpciones mas genericas
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
      Map<String, Object> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Invalid Input");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
