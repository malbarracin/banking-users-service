package io.banking.whatsapp.users.utils;

import java.time.LocalDateTime;

import io.banking.whatsapp.users.domain.User;

/**
 * Utility class for creating test data objects.
 * Provides static helper methods to create test entities with predefined values.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
public class TestUtils {

    /**
     * Creates a test User instance with predefined values.
     * The user will have default test data with the specified ID:
     * - First Name: "John"
     * - Last Name: "Doe"
     * - Email: "john.doe@example.com"
     * - Phone: "+1234567890"
     * - DNI: "12345678"
     * - Status: "ACTIVE"
     * - Created/Updated timestamps: current time
     *
     * @param id the ID to set for the test user
     * @return a User instance populated with test data
     */
    public static User createTestUser(String id) {
        return User.builder()
            .id(id)
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@example.com")
            .phoneNumber("+1234567890")
            .dni("12345678")
            .status("ACTIVE")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }
} 