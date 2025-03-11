package io.banking.whatsapp.users.service.impl;

import io.banking.whatsapp.users.domain.User;
import io.banking.whatsapp.users.domain.dto.UserRequestDTO;
import io.banking.whatsapp.users.domain.dto.UserResponseDTO;
import io.banking.whatsapp.users.domain.mapper.UserMapper;
import io.banking.whatsapp.users.exception.NotFoundException;
import io.banking.whatsapp.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        user = User.builder()
                .id("1")
                .dni("12345678")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("+1234567890")
                .status("ACTIVE")
                .createdAt(now)
                .updatedAt(now)
                .build();

        userRequestDTO = new UserRequestDTO();
        // Set necessary fields for userRequestDTO

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId("1");
        userResponseDTO.setDni("12345678");
        // Set other necessary fields for userResponseDTO
    }

    @Test
    void createUser_Success() {
        when(userMapper.toEntity(any(UserRequestDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        StepVerifier.create(userService.createUser(userRequestDTO))
                .expectNext(userResponseDTO)
                .verifyComplete();

        verify(userMapper).toEntity(userRequestDTO);
        verify(userRepository).save(user);
        verify(userMapper).toDto(user);
    }

    @Test
    void getUserById_Success() {
        when(userRepository.findById("1")).thenReturn(Mono.just(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        StepVerifier.create(userService.getUserById("1"))
                .expectNext(userResponseDTO)
                .verifyComplete();

        verify(userRepository).findById("1");
        verify(userMapper).toDto(user);
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById("1")).thenReturn(Mono.empty());

        StepVerifier.create(userService.getUserById("1"))
                .expectError(NotFoundException.class)
                .verify();

        verify(userRepository).findById("1");
        verify(userMapper, never()).toDto(any());
    }

    @Test
    void getUserByDni_Success() {
        when(userRepository.findByDni("12345678")).thenReturn(Mono.just(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        StepVerifier.create(userService.getUserByDni("12345678"))
                .expectNext(userResponseDTO)
                .verifyComplete();

        verify(userRepository).findByDni("12345678");
        verify(userMapper).toDto(user);
    }

    @Test
    void getUserByDni_NotFound() {
        when(userRepository.findByDni("12345678")).thenReturn(Mono.empty());

        StepVerifier.create(userService.getUserByDni("12345678"))
                .expectError(NotFoundException.class)
                .verify();

        verify(userRepository).findByDni("12345678");
        verify(userMapper, never()).toDto(any());
    }

    @Test
    void getAllUsers_Success() {
        when(userRepository.findAll()).thenReturn(Flux.just(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        StepVerifier.create(userService.getAllUsers())
                .expectNext(userResponseDTO)
                .verifyComplete();

        verify(userRepository).findAll();
        verify(userMapper).toDto(user);
    }

    @Test
    void updateUser_Success() {
        LocalDateTime now = LocalDateTime.now();
        User updatedUser = User.builder()
                .id("1")
                .dni("12345678")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("+1234567890")
                .status("ACTIVE")
                .createdAt(user.getCreatedAt())
                .updatedAt(now)
                .build();

        when(userRepository.findById("1")).thenReturn(Mono.just(user));
        when(userMapper.toEntity(any(UserRequestDTO.class))).thenReturn(updatedUser);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(updatedUser));
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        StepVerifier.create(userService.updateUser("1", userRequestDTO))
                .expectNext(userResponseDTO)
                .verifyComplete();

        verify(userRepository).findById("1");
        verify(userMapper).toEntity(userRequestDTO);
        verify(userRepository).save(any(User.class));
        verify(userMapper).toDto(updatedUser);
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.deleteById("1")).thenReturn(Mono.empty());

        StepVerifier.create(userService.deleteUser("1"))
                .verifyComplete();

        verify(userRepository).deleteById("1");
    }
} 