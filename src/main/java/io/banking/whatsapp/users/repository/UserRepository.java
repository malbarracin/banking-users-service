package io.banking.whatsapp.users.repository;

import io.banking.whatsapp.users.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByEmail(String email);
    Mono<User> findByPhoneNumber(String phoneNumber);
    Mono<User> findByDni(String dni);
}