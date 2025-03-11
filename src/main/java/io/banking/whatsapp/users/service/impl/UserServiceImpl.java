package io.banking.whatsapp.users.service.impl;

import io.banking.whatsapp.users.domain.User;
import io.banking.whatsapp.users.domain.dto.UserRequestDTO;
import io.banking.whatsapp.users.domain.dto.UserResponseDTO;
import io.banking.whatsapp.users.domain.mapper.UserMapper;
import io.banking.whatsapp.users.repository.UserRepository;
import io.banking.whatsapp.users.service.UserService;
import io.banking.whatsapp.users.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

/**
 * Implementation of the UserService interface.
 * This service provides the business logic for user management operations
 * including creation, retrieval, update, and deletion of users.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Creates a new user in the system.
     * Converts the request DTO to an entity and saves it to the database.
     *
     * @param userRequest the user data for creation
     * @return a Mono containing the created user's data
     */
    @Override
    public Mono<UserResponseDTO> createUser(UserRequestDTO userRequest) {
        return userRepository.save(userMapper.toEntity(userRequest))
                .map(userMapper::toDto);
    }

    /**
     * Retrieves a user by their ID.
     * Throws NotFoundException if the user is not found.
     *
     * @param id the ID of the user to retrieve
     * @return a Mono containing the user's data
     * @throws NotFoundException if no user is found with the given ID
     */
    @Override
    public Mono<UserResponseDTO> getUserById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with ID: " + id)));
    }

    /**
     * Retrieves a user by their DNI (National ID).
     * Throws NotFoundException if the user is not found.
     *
     * @param dni the DNI of the user to retrieve
     * @return a Mono containing the user's data
     * @throws NotFoundException if no user is found with the given DNI
     */
    @Override
    public Mono<UserResponseDTO> getUserByDni(String dni) {
        return userRepository.findByDni(dni)
                .map(userMapper::toDto)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with DNI: " + dni)));
    }

    /**
     * Retrieves all users in the system.
     *
     * @return a Flux containing all users' data
     */
    @Override
    public Flux<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .map(userMapper::toDto);
    }

    /**
     * Updates an existing user's information.
     * Preserves the original creation timestamp and updates the modification timestamp.
     *
     * @param id the ID of the user to update
     * @param userRequest the new user data
     * @return a Mono containing the updated user's data
     * @throws NotFoundException if no user is found with the given ID
     */
    @Override
    public Mono<UserResponseDTO> updateUser(String id, UserRequestDTO userRequest) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with ID: " + id)))
                .flatMap(existingUser -> {
                    User updatedUser = userMapper.toEntity(userRequest);
                    updatedUser.setId(existingUser.getId());
                    updatedUser.setCreatedAt(existingUser.getCreatedAt());
                    updatedUser.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(updatedUser);
                })
                .map(userMapper::toDto);
    }

    /**
     * Deletes a user from the system.
     *
     * @param id the ID of the user to delete
     * @return a Mono that completes when the deletion is done
     */
    @Override
    public Mono<Void> deleteUser(String id) {
        return userRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("User not found with ID: " + id)))
            .flatMap(user -> userRepository.deleteById(id));
    }
}