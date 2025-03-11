package io.banking.whatsapp.users.repository;

import io.banking.whatsapp.users.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Reactive MongoDB repository for User entities.
 * This interface provides reactive CRUD operations for User entities and
 * additional custom query methods for finding users by unique identifiers.
 * Extends ReactiveMongoRepository to inherit standard reactive MongoDB operations.
 *
 * @author Marcelo Alejandro Albarracín
 * @email marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    
    /**
     * Finds a user by their email address.
     * Email addresses are unique in the system.
     *
     * @param email the email address to search for
     * @return a Mono containing the user if found, or an empty Mono if not found
     */
    Mono<User> findByEmail(String email);

    /**
     * Finds a user by their phone number.
     * Phone numbers are unique in the system and are used for WhatsApp communication.
     *
     * @param phoneNumber the phone number to search for
     * @return a Mono containing the user if found, or an empty Mono if not found
     */
    Mono<User> findByPhoneNumber(String phoneNumber);

    /**
     * Finds a user by their DNI (National ID).
     * DNI numbers are unique in the system and serve as a business identifier.
     *
     * @param dni the DNI number to search for
     * @return a Mono containing the user if found, or an empty Mono if not found
     */
    Mono<User> findByDni(String dni);
}