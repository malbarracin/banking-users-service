package io.banking.whatsapp.users.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "User Response Data")
public class UserResponseDTO {
    @Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;
    
    @Schema(description = "User's first name", example = "John")
    private String firstName;
    
    @Schema(description = "User's last name", example = "Doe")
    private String lastName;
    
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "User's phone number", example = "+1234567890")
    private String phoneNumber;
    
    @Schema(description = "User's DNI (National ID)", example = "12345678")
    private String dni;
    
    @Schema(description = "User status", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE", "BLOCKED"})
    private String status;
    
    @Schema(description = "Creation timestamp", example = "2024-02-15T10:30:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update timestamp", example = "2024-02-15T10:30:00")
    private LocalDateTime updatedAt;
}