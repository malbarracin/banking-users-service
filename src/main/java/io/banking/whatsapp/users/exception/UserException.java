package io.banking.whatsapp.users.exception;

/**
 * Custom exception for user-related business logic errors.
 * This exception is used to indicate various user operation failures such as
 * validation errors, business rule violations, or other user-specific issues.
 * It is handled by the GlobalExceptionHandler and mapped to an HTTP 400 (Bad Request) response.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 * @see io.banking.whatsapp.users.exception.GlobalExceptionHandler
 */
public class UserException extends RuntimeException {
    
    /**
     * Constructs a new UserException with the specified detail message.
     * 
     * @param message the detail message describing the specific business rule violation
     *               or user operation error (e.g., "User email already exists",
     *               "Invalid phone number format")
     */
    public UserException(String message) {
        super(message);
    }
}