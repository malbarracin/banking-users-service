package io.banking.whatsapp.users.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "User Request Data")
public class UserRequestDTO {
    @Schema(description = "User's first name", example = "John")
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @Schema(description = "User's last name", example = "Doe")
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @Schema(description = "User's email address", example = "john.doe@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @Schema(description = "User's phone number", example = "+1234567890")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    
    @Schema(description = "User's DNI (National ID)", example = "12345678")
    @NotBlank(message = "DNI is required")
    private String dni;
}