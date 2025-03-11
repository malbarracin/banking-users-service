package io.banking.whatsapp.users.exception;

import io.banking.whatsapp.users.domain.dto.ErrorResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;
    private MethodParameter methodParameter;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        exceptionHandler = new GlobalExceptionHandler();
        Method method = getClass().getDeclaredMethod("setUp");
        methodParameter = new MethodParameter(method, -1);
    }

    @Test
    void handleUserException_ShouldReturnBadRequest() {
        // Arrange
        String errorMessage = "User validation failed";
        UserException userException = new UserException(errorMessage);

        // Act
        ResponseEntity<ErrorResponseDTO> response = exceptionHandler.handleUserException(userException);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        
        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(errorMessage, errorResponse.getMessage());
        assertEquals("USER_ERROR", errorResponse.getCode());
        assertNotNull(errorResponse.getTimestamp());
    }

    @Test
    void handleValidationException_ShouldReturnBadRequest() {
        // Arrange
        String objectName = "userRequestDTO";
        String field = "email";
        String defaultMessage = "must be a valid email";
        
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError(objectName, field, defaultMessage));
        
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), objectName);
        for (FieldError error : fieldErrors) {
            bindingResult.addError(error);
        }
        
        WebExchangeBindException validationException = new WebExchangeBindException(
            methodParameter,
            bindingResult
        );

        // Act
        ResponseEntity<ErrorResponseDTO> response = exceptionHandler.handleValidationException(validationException);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        
        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertTrue(errorResponse.getMessage().contains("Validation error"));
        assertEquals("VALIDATION_ERROR", errorResponse.getCode());
        assertNotNull(errorResponse.getTimestamp());
    }

    @Test
    void handleUserException_ShouldIncludeTimestamp() {
        // Arrange
        UserException userException = new UserException("Test error");

        // Act
        ResponseEntity<ErrorResponseDTO> response = exceptionHandler.handleUserException(userException);

        // Assert
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getTimestamp());
        assertTrue(response.getBody().getTimestamp().isBefore(response.getBody().getTimestamp().plusSeconds(1)));
    }

    @Test
    void handleValidationException_WithMultipleErrors() {
        // Arrange
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("userRequestDTO", "email", "must be a valid email"));
        fieldErrors.add(new FieldError("userRequestDTO", "phoneNumber", "must not be blank"));
        
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "userRequestDTO");
        for (FieldError error : fieldErrors) {
            bindingResult.addError(error);
        }
        
        WebExchangeBindException validationException = new WebExchangeBindException(
            methodParameter,
            bindingResult
        );

        // Act
        ResponseEntity<ErrorResponseDTO> response = exceptionHandler.handleValidationException(validationException);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        
        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertTrue(errorResponse.getMessage().contains("Validation error"));
        assertTrue(errorResponse.getMessage().contains("email"));
        assertTrue(errorResponse.getMessage().contains("phoneNumber"));
        assertEquals("VALIDATION_ERROR", errorResponse.getCode());
    }
} 