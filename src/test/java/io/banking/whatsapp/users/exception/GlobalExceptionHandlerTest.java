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


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GlobalExceptionHandler.
 * Contains unit tests for verifying the proper handling of different types of exceptions
 * and the generation of appropriate error responses.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;
    private MethodParameter methodParameter;

    /**
     * Sets up the test environment before each test.
     * Initializes the exception handler and method parameter for validation tests.
     *
     * @throws NoSuchMethodException if the setUp method cannot be found
     */
    @BeforeEach
    void setUp() throws NoSuchMethodException {
        exceptionHandler = new GlobalExceptionHandler();
        Method method = getClass().getDeclaredMethod("setUp");
        methodParameter = new MethodParameter(method, -1);
    }

    /**
     * Tests handling of UserException.
     * Verifies that the handler returns a BAD_REQUEST status with appropriate error details.
     */
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

    /**
     * Tests handling of validation exceptions.
     * Verifies that the handler properly processes field validation errors
     * and returns a BAD_REQUEST status with validation details.
     */
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

    /**
     * Tests that UserException handling includes a timestamp.
     * Verifies that the error response contains a valid and recent timestamp.
     */
    @Test
    void handleUserException_ShouldIncludeTimestamp() {
        // Arrange
        UserException userException = new UserException("Test error");

        // Act
        ResponseEntity<ErrorResponseDTO> response = exceptionHandler.handleUserException(userException);

        // Assert
        ErrorResponseDTO errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getTimestamp());
        assertTrue(errorResponse.getTimestamp().isBefore(errorResponse.getTimestamp().plusSeconds(1)));
    }

    /**
     * Tests handling of multiple validation errors.
     * Verifies that the handler properly processes and combines multiple field errors
     * into a single error response.
     */
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