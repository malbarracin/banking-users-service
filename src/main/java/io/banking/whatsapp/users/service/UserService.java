package io.banking.whatsapp.users.service;

import io.banking.whatsapp.users.domain.dto.UserRequestDTO;
import io.banking.whatsapp.users.domain.dto.UserResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for user management operations.
 * Defines the contract for handling user-related business operations
 * in a reactive way using Project Reactor types (Mono and Flux).
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
public interface UserService {

    /**
     * Creates a new user in the system.
     *
     * @param userRequest DTO containing the user information to create
     * @return a Mono containing the created user's data
     */
    Mono<UserResponseDTO> createUser(UserRequestDTO userRequest);

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return a Mono containing the user's data if found, or an error if not found
     */
    Mono<UserResponseDTO> getUserById(String id);

    /**
     * Retrieves a user by their DNI (National ID).
     *
     * @param dni the DNI number to search for
     * @return a Mono containing the user's data if found, or an error if not found
     */
    Mono<UserResponseDTO> getUserByDni(String dni);

    /**
     * Retrieves all users in the system.
     *
     * @return a Flux containing all users' data
     */
    Flux<UserResponseDTO> getAllUsers();

    /**
     * Updates an existing user's information.
     *
     * @param id the unique identifier of the user to update
     * @param userRequest DTO containing the new user information
     * @return a Mono containing the updated user's data, or an error if not found
     */
    Mono<UserResponseDTO> updateUser(String id, UserRequestDTO userRequest);

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return an empty Mono when deletion is complete
     * @throws NotFoundException if the user is not found
     */
    Mono<Void> deleteUser(String id);
}