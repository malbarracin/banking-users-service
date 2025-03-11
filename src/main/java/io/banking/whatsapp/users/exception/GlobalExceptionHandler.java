package io.banking.whatsapp.users.exception;

import io.banking.whatsapp.users.domain.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import java.time.LocalDateTime;

/**
 * Global exception handler for the banking WhatsApp users service.
 * This class provides centralized exception handling across all controllers
 * and converts exceptions into standardized API responses.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles UserException instances.
     * Converts user-related exceptions into a standardized error response
     * with BAD_REQUEST (400) status.
     *
     * @param ex the UserException that was thrown
     * @return ResponseEntity containing error details
     */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserException(UserException ex) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .code("USER_ERROR")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Handles validation exceptions from request binding.
     * Converts validation errors into a standardized error response
     * with BAD_REQUEST (400) status.
     *
     * @param ex the WebExchangeBindException that was thrown during validation
     * @return ResponseEntity containing validation error details
     */
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