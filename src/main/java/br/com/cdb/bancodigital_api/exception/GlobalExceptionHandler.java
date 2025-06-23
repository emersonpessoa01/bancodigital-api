package br.com.cdb.bancodigital_api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(404).body(Map.of(
                "erro", ex.getMessage(),
                "timestamp", LocalDateTime.now()
        ));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception ex){
        return ResponseEntity.status(500).body(Map.of(
                "erro", "Ocorreu um erro inesperado do servidor",
                "detalhes", ex.getMessage(),
                "timestamp", LocalDateTime.now()
        ));
    }
}
