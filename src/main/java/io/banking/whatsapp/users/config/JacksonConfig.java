package io.banking.whatsapp.users.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Configuration class for Jackson JSON processing.
 * Provides custom configuration for JSON serialization and deserialization,
 * particularly for handling Java 8 date/time types.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
@Configuration
public class JacksonConfig {

    /**
     * Creates and configures an ObjectMapper bean with custom modules.
     * Includes JavaTimeModule for proper handling of Java 8 date/time types
     * such as LocalDateTime.
     *
     * @return configured ObjectMapper instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .build();
    }
} 