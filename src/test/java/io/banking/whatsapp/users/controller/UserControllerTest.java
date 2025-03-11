package io.banking.whatsapp.users.controller;

import io.banking.whatsapp.users.domain.dto.UserRequestDTO;
import io.banking.whatsapp.users.domain.dto.UserResponseDTO;
import io.banking.whatsapp.users.exception.NotFoundException;
import io.banking.whatsapp.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private WebTestClient webTestClient;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(userController).build();

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

    @Test
    void createUser_Success() {
        when(userService.createUser(any(UserRequestDTO.class)))
                .thenReturn(Mono.just(userResponseDTO));

        webTestClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDTO.class)
                .isEqualTo(userResponseDTO);

        verify(userService).createUser(any(UserRequestDTO.class));
    }

    @Test
    void getUserById_Success() {
        String userId = "1";
        when(userService.getUserById(userId))
                .thenReturn(Mono.just(userResponseDTO));

        webTestClient.get()
                .uri("/api/v1/users/{id}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .isEqualTo(userResponseDTO);

        verify(userService).getUserById(userId);
    }

    @Test
    void getUserById_NotFound() {
        String userId = "1";
        when(userService.getUserById(userId))
                .thenReturn(Mono.error(new NotFoundException("User not found with ID: " + userId)));

        webTestClient.get()
                .uri("/api/v1/users/{id}", userId)
                .exchange()
                .expectStatus().isNotFound();

        verify(userService).getUserById(userId);
    }

    @Test
    void getUserByDni_Success() {
        String dni = "12345678";
        when(userService.getUserByDni(dni))
                .thenReturn(Mono.just(userResponseDTO));

        webTestClient.get()
                .uri("/api/v1/users/dni/{dni}", dni)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .isEqualTo(userResponseDTO);

        verify(userService).getUserByDni(dni);
    }

    @Test
    void getUserByDni_NotFound() {
        String dni = "12345678";
        when(userService.getUserByDni(dni))
                .thenReturn(Mono.error(new NotFoundException("User not found with DNI: " + dni)));

        webTestClient.get()
                .uri("/api/v1/users/dni/{dni}", dni)
                .exchange()
                .expectStatus().isNotFound();

        verify(userService).getUserByDni(dni);
    }

    @Test
    void getAllUsers_Success() {
        when(userService.getAllUsers())
                .thenReturn(Flux.just(userResponseDTO));

        webTestClient.get()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponseDTO.class)
                .hasSize(1)
                .contains(userResponseDTO);

        verify(userService).getAllUsers();
    }

    @Test
    void updateUser_Success() {
        String userId = "1";
        when(userService.updateUser(eq(userId), any(UserRequestDTO.class)))
                .thenReturn(Mono.just(userResponseDTO));

        webTestClient.put()
                .uri("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .isEqualTo(userResponseDTO);

        verify(userService).updateUser(eq(userId), any(UserRequestDTO.class));
    }

    @Test
    void updateUser_NotFound() {
        String userId = "1";
        when(userService.updateUser(eq(userId), any(UserRequestDTO.class)))
                .thenReturn(Mono.error(new NotFoundException("User not found with ID: " + userId)));

        webTestClient.put()
                .uri("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequestDTO)
                .exchange()
                .expectStatus().isNotFound();

        verify(userService).updateUser(eq(userId), any(UserRequestDTO.class));
    }

    @Test
    void deleteUser_Success() {
        String userId = "1";
        when(userService.deleteUser(userId))
                .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/users/{id}", userId)
                .exchange()
                .expectStatus().isNoContent();

        verify(userService).deleteUser(userId);
    }

    @Test
    void deleteUser_NotFound() {
        String userId = "1";
        when(userService.deleteUser(userId))
                .thenReturn(Mono.error(new NotFoundException("User not found with ID: " + userId)));

        webTestClient.delete()
                .uri("/api/v1/users/{id}", userId)
                .exchange()
                .expectStatus().isNotFound();

        verify(userService).deleteUser(userId);
    }
} 