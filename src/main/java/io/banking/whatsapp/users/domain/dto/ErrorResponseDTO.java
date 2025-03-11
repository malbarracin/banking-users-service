package io.banking.whatsapp.users.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error Response Data")
public class ErrorResponseDTO {
    @Schema(description = "HTTP Status code", example = "404")
    private int status;
    
    @Schema(description = "Error code", example = "USER_NOT_FOUND")
    private String code;
    
    @Schema(description = "Error message", example = "User not found")
    private String message;
    
    @Schema(description = "Error details", example = "User with ID 123e4567-e89b-12d3-a456-426614174000 was not found")
    private String details;
    
    @Schema(description = "Path where the error occurred", example = "/api/v1/users/123e4567-e89b-12d3-a456-426614174000")
    private String path;
    
    @Schema(description = "Timestamp when the error occurred", example = "2024-02-15T10:30:00")
    private LocalDateTime timestamp;
}