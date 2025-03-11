package io.banking.whatsapp.users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class for web-related beans and settings.
 * Provides configuration for web filters and logging capabilities.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
@Configuration
public class WebConfig {
    
    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    /**
     * Creates a WebFilter bean that logs HTTP request and response details.
     * This filter logs the HTTP method, URI, and response status for each request.
     *
     * @return WebFilter instance that handles request/response logging
     */
    @Bean
    public WebFilter loggingFilter() {
        return (exchange, chain) -> {
            log.debug("Request: {} {}", 
                exchange.getRequest().getMethod(), 
                exchange.getRequest().getURI());
            
            return chain.filter(exchange)
                .doOnSuccess(v -> {
                    log.debug("Response Status: {}", 
                        exchange.getResponse().getStatusCode());
                });
        };
    }
} 