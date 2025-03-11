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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Mono<UserResponseDTO> createUser(UserRequestDTO userRequest) {
        return userRepository.save(userMapper.toEntity(userRequest))
                .map(userMapper::toDto);
    }

    @Override
    public Mono<UserResponseDTO> getUserById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with ID: " + id)));
    }

    @Override
    public Mono<UserResponseDTO> getUserByDni(String dni) {
        return userRepository.findByDni(dni)
                .map(userMapper::toDto)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found with DNI: " + dni)));
    }

    @Override
    public Flux<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .map(userMapper::toDto);
    }

    @Override
    public Mono<UserResponseDTO> updateUser(String id, UserRequestDTO userRequest) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    User updatedUser = userMapper.toEntity(userRequest);
                    updatedUser.setId(existingUser.getId());
                    updatedUser.setCreatedAt(existingUser.getCreatedAt());
                    updatedUser.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(updatedUser);
                })
                .map(userMapper::toDto);
    }

    @Override
    public Mono<Void> deleteUser(String id) {
        return userRepository.deleteById(id);
    }
}