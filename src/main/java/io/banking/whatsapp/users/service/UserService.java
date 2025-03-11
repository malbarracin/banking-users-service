package io.banking.whatsapp.users.service;

import io.banking.whatsapp.users.domain.dto.UserRequestDTO;
import io.banking.whatsapp.users.domain.dto.UserResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResponseDTO> createUser(UserRequestDTO userRequest);
    Mono<UserResponseDTO> getUserById(String id);
    Mono<UserResponseDTO> getUserByDni(String dni);
    Flux<UserResponseDTO> getAllUsers();
    Mono<UserResponseDTO> updateUser(String id, UserRequestDTO userRequest);
    Mono<Void> deleteUser(String id);
}