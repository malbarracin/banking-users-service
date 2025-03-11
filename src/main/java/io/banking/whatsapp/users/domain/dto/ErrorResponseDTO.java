package io.banking.whatsapp.users.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for error responses.
 * This class represents the standardized error response structure used across the application.
 * It includes detailed information about errors that occur during API operations.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error Response Data")
public class ErrorResponseDTO {
    
    /**
     * The HTTP status code of the error.
     * Represents the standard HTTP status code associated with the error response.
     */
    @Schema(description = "HTTP Status code", example = "404")
    private int status;
    
    /**
     * The application-specific error code.
     * A unique identifier for the type of error that occurred.
     */
    @Schema(description = "Error code", example = "USER_NOT_FOUND")
    private String code;
    
    /**
     * A brief message describing the error.
     * Provides a short, human-readable description of what went wrong.
     */
    @Schema(description = "Error message", example = "User not found")
    private String message;
    
    /**
     * Detailed information about the error.
     * Contains more specific information about what caused the error.
     */
    @Schema(description = "Error details", example = "User with ID 123e4567-e89b-12d3-a456-426614174000 was not found")
    private String details;
    
    /**
     * The API endpoint path where the error occurred.
     * Indicates which endpoint was being accessed when the error happened.
     */
    @Schema(description = "Path where the error occurred", example = "/api/v1/users/123e4567-e89b-12d3-a456-426614174000")
    private String path;
    
    /**
     * The timestamp when the error occurred.
     * Records the exact date and time when the error was generated.
     */
    @Schema(description = "Timestamp when the error occurred", example = "2024-02-15T10:30:00")
    private LocalDateTime timestamp;
}