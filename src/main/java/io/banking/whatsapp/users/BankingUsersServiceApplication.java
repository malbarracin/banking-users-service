package io.banking.whatsapp.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Banking Users Service.
 * This is a Spring Boot application that manages user operations for the banking WhatsApp integration.
 * The service provides reactive REST APIs for user management, using MongoDB for persistence.
 *
 * Features:
 * - Reactive endpoints using Spring WebFlux
 * - MongoDB integration
 * - User CRUD operations
 * - Input validation
 * - Exception handling
 * - Swagger/OpenAPI documentation
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
@SpringBootApplication
public class BankingUsersServiceApplication {

    /**
     * Main method that starts the Spring Boot application.
     * Initializes the Spring context and all configured components.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(BankingUsersServiceApplication.class, args);
    }
}