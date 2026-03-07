package com.alura.alura_foro.controller;



import com.alura.alura_foro.servicio.ValidacionDeIntegridad;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Maneja tus validaciones de negocio personalizadas (Ej. "El usuario no existe")
    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity<String> manejarErrorIntegridad(ValidacionDeIntegridad ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // 2. Maneja los errores del @Valid (Ej. "El título no puede estar en blanco")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> manejarErroresDeValidacion(MethodArgumentNotValidException e) {
        // Extraemos solo el campo que falló y el mensaje de error
        var errores = e.getFieldErrors().stream()
                .map(DatosErrorValidacion::new)
                .toList();
        return ResponseEntity.badRequest().body(errores); // Devuelve 400 Bad Request
    }

    // 3. Maneja errores cuando buscas un ID en la BD y no existe
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> manejarError404() {
        return ResponseEntity.notFound().build(); // Devuelve 404 Not Found
    }

    // Opcional: manejar otros errores genéricos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarErrorGenerico(Exception ex) {
        // Tip de seguridad: En producción es mejor no devolver ex.getMessage()
        // para no revelar detalles internos de tu servidor.
        return ResponseEntity.internalServerError().body("Error interno en el servidor.");
    }

    // Record interno para dar formato a los errores del @Valid
    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}


