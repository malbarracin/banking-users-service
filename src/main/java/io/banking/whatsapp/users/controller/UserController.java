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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management APIs")
public class UserController {
    private final UserService userService;

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

    @Operation(summary = "Get all users")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of users found")
    })
    @GetMapping
    public Flux<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

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
        return userService.getUserByDni(dni);
    }
}