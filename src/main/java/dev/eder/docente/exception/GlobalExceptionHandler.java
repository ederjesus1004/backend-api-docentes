package dev.eder.docente.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// RECETA: 404 · 409 · 400 · 400 · 500 + armarRespuesta
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 → el que ya sabes
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        return armarRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), Map.of());
    }

    // 409 → el que ya sabes
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> manejarDuplicado(RecursoDuplicadoException ex) {
        return armarRespuesta(HttpStatus.CONFLICT, ex.getMessage(), Map.of());
    }

    // 400 VALIDACIÓN → el único distinto: mapa vacío → forEach → armarRespuesta con
    // el mapa
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>(); // 1. mapa vacío
        ex.getBindingResult().getFieldErrors() // 2. por cada error...
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage())); // ...campo → mensaje
        return armarRespuesta(HttpStatus.BAD_REQUEST, "Datos inválidos", errores); // 3. con el mapa
    }

    // 400 JSON ROTO → copia del 404: cambias clase, status y texto
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return armarRespuesta(HttpStatus.BAD_REQUEST, "El JSON enviado no es válido", Map.of());
    }

    // 500 → copia del 404: cambias clase, status, texto y agregas log.error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(Exception ex) {
        log.error("Error inesperado", ex);
        return armarRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado", Map.of());
    }

    // El ayudante: arma el JSON de error
    private ResponseEntity<ErrorResponse> armarRespuesta(HttpStatus status, String mensaje,
            Map<String, String> errores) {
        ErrorResponse body = new ErrorResponse(LocalDateTime.now(), status.value(), mensaje, errores);
        return ResponseEntity.status(status).body(body);
    }
}