package io.banking.whatsapp.users.exception;

import io.banking.whatsapp.users.domain.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserException(UserException ex) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .code("USER_ERROR")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(WebExchangeBindException ex) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message("Validation error: " + ex.getMessage())
                .code("VALIDATION_ERROR")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}