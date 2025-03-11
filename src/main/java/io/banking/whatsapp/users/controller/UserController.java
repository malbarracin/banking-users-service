package io.banking.whatsapp.users.controller;

import io.banking.whatsapp.users.domain.dto.UserRequestDTO;
import io.banking.whatsapp.users.domain.dto.UserResponseDTO;
import io.banking.whatsapp.users.domain.dto.ErrorResponseDTO;
import io.banking.whatsapp.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for managing User operations.
 * This controller provides endpoints for CRUD operations on users in the banking WhatsApp system.
 *
* @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management APIs")
public class UserController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * Creates a new user.
     *
     * @param request The user data to create
     * @return The created user information
     */
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request) {
        return userService.createUser(request);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve
     * @return The user information if found
     */
    @Operation(summary = "Get user by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public Mono<UserResponseDTO> getUserById(
            @Parameter(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id) {
        return userService.getUserById(id);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return A flux of all users
     */
    @Operation(summary = "Get all users")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of users found")
    })
    @GetMapping
    public Flux<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Updates an existing user.
     *
     * @param id The ID of the user to update
     * @param request The updated user data
     * @return The updated user information
     */
    @Operation(summary = "Update user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public Mono<UserResponseDTO> updateUser(
            @Parameter(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id,
            @Valid @RequestBody UserRequestDTO request) {
        return userService.updateUser(id, request);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete
     * @return A Mono<Void> when completed
     */
    @Operation(summary = "Delete user")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(
            @Parameter(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id) {
        return userService.deleteUser(id);
    }

    /**
     * Retrieves a user by their DNI (National ID).
     *
     * @param dni The DNI of the user to retrieve
     * @return The user information if found
     */
    @Operation(summary = "Get user by DNI")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/dni/{dni}")
    public Mono<UserResponseDTO> getUserByDni(
            @Parameter(description = "User DNI (National ID)", example = "12345678")
            @PathVariable String dni) {
        return userService.getUserByDni(dni)
            .doOnNext(response -> log.debug("Returning user: {}", response))
            .doOnError(error -> log.error("Error getting user by DNI: {}", error.getMessage()));
    }
}