package com.alura.alura_foro.controller;


import com.alura.alura_foro.servicio.ValidacionDeIntegridad;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity<String> manejarErrorIntegridad(ValidacionDeIntegridad ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // Opcional: manejar otros errores genéricos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarErrorGenerico(Exception ex) {
        return ResponseEntity.internalServerError().body("Error interno: " + ex.getMessage());
    }
}

