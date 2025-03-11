package io.banking.whatsapp.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource is not found in the system.
 * This exception is automatically mapped to an HTTP 404 (Not Found) response.
 * Used primarily for user-related resources that cannot be found by their identifiers.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    
    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message the detail message explaining what resource was not found
     *               (e.g., "User not found with ID: 123")
     */
    public NotFoundException(String message) {
        super(message);
    }
}