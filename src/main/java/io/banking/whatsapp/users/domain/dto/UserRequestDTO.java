package io.banking.whatsapp.users.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object for user creation and update requests.
 * This class represents the data structure used when creating or updating a user in the system.
 * It includes all the necessary fields with their respective validations.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
@Data
@Schema(description = "User Request Data")
public class UserRequestDTO {

    /**
     * The user's first name.
     * Must not be blank or null.
     */
    @Schema(description = "User's first name", example = "John")
    @NotBlank(message = "First name is required")
    private String firstName;
    
    /**
     * The user's last name.
     * Must not be blank or null.
     */
    @Schema(description = "User's last name", example = "Doe")
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    /**
     * The user's email address.
     * Must be a valid email format and not blank or null.
     * Used for communication and as a unique identifier.
     */
    @Schema(description = "User's email address", example = "john.doe@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    /**
     * The user's phone number.
     * Must not be blank or null.
     * Used for WhatsApp communication.
     */
    @Schema(description = "User's phone number", example = "+1234567890")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    
    /**
     * The user's DNI (National ID).
     * Must not be blank or null.
     * Used as a unique identifier for the user in the system.
     */
    @Schema(description = "User's DNI (National ID)", example = "12345678")
    @NotBlank(message = "DNI is required")
    private String dni;
}