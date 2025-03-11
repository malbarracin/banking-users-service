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

/**
 * Test class for UserServiceImpl.
 * Contains unit tests for verifying the business logic implementation
 * of the user service layer using reactive programming.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
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

    /**
     * Sets up the test environment before each test.
     * Initializes test data including User entity, request DTO, and response DTO.
     */
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
        userRequestDTO.setFirstName("John");
        userRequestDTO.setLastName("Doe");
        userRequestDTO.setEmail("john.doe@example.com");
        userRequestDTO.setPhoneNumber("+1234567890");
        userRequestDTO.setDni("12345678");

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId("1");
        userResponseDTO.setFirstName("John");
        userResponseDTO.setLastName("Doe");
        userResponseDTO.setEmail("john.doe@example.com");
        userResponseDTO.setPhoneNumber("+1234567890");
        userResponseDTO.setDni("12345678");
        userResponseDTO.setStatus("ACTIVE");
    }

    /**
     * Tests successful user creation.
     * Verifies that the service properly maps and saves a new user.
     */
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

    /**
     * Tests successful user retrieval by ID.
     * Verifies that the service properly retrieves and maps an existing user.
     */
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

    /**
     * Tests user retrieval by ID when user is not found.
     * Verifies that the service properly handles the not found scenario.
     */
    @Test
    void getUserById_NotFound() {
        when(userRepository.findById("1")).thenReturn(Mono.empty());

        StepVerifier.create(userService.getUserById("1"))
                .expectError(NotFoundException.class)
                .verify();

        verify(userRepository).findById("1");
        verify(userMapper, never()).toDto(any());
    }

    /**
     * Tests successful user retrieval by DNI.
     * Verifies that the service properly retrieves and maps a user by DNI.
     */
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

    /**
     * Tests user retrieval by DNI when user is not found.
     * Verifies that the service properly handles the not found scenario.
     */
    @Test
    void getUserByDni_NotFound() {
        when(userRepository.findByDni("12345678")).thenReturn(Mono.empty());

        StepVerifier.create(userService.getUserByDni("12345678"))
                .expectError(NotFoundException.class)
                .verify();

        verify(userRepository).findByDni("12345678");
        verify(userMapper, never()).toDto(any());
    }

    /**
     * Tests successful retrieval of all users.
     * Verifies that the service properly retrieves and maps multiple users.
     */
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

    /**
     * Tests successful user update.
     * Verifies that the service properly updates and saves existing user data.
     */
    @Test
    void updateUser_Success() {
        when(userRepository.findById("1")).thenReturn(Mono.just(user));
        when(userMapper.toEntity(any(UserRequestDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        StepVerifier.create(userService.updateUser("1", userRequestDTO))
                .expectNext(userResponseDTO)
                .verifyComplete();

        verify(userRepository).findById("1");
        verify(userMapper).toEntity(userRequestDTO);
        verify(userRepository).save(any(User.class));
        verify(userMapper).toDto(any(User.class));
    }

    /**
     * Tests update user when user is not found.
     * Verifies that the service properly handles the not found scenario during update.
     */
    @Test
    void updateUser_NotFound() {
        when(userRepository.findById("1")).thenReturn(Mono.empty());

        StepVerifier.create(userService.updateUser("1", userRequestDTO))
                .expectError(NotFoundException.class)
                .verify();

        verify(userRepository).findById("1");
        verify(userMapper, never()).toEntity(any());
        verify(userRepository, never()).save(any());
    }

    /**
     * Test successful user deletion when the user exists.
     */
    @Test
    void deleteUser_WhenUserExists_ShouldDelete() {
        String userId = "existingId";
        User existingUser = User.builder()
            .id(userId)
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@example.com")
            .phoneNumber("+1234567890")
            .dni("12345678")
            .status("ACTIVE")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        when(userRepository.findById(userId)).thenReturn(Mono.just(existingUser));
        when(userRepository.deleteById(userId)).thenReturn(Mono.empty());

        StepVerifier.create(userService.deleteUser(userId))
                .verifyComplete();

        verify(userRepository).findById(userId);
        verify(userRepository).deleteById(userId);
    }

    /**
     * Test user deletion when the user doesn't exist, should throw NotFoundException.
     */
    @Test
    void deleteUser_WhenUserNotExists_ShouldThrowNotFoundException() {
        String userId = "nonExistingId";
        
        when(userRepository.findById(userId)).thenReturn(Mono.empty());

        StepVerifier.create(userService.deleteUser(userId))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotFoundException &&
                    throwable.getMessage().equals("User not found with ID: " + userId))
                .verify();

        verify(userRepository).findById(userId);
        verify(userRepository, never()).deleteById(userId);
    }
} 