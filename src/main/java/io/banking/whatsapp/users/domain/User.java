package io.banking.whatsapp.users.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String documentNumber;
    
    private String firstName;
    private String lastName;
    
    @Indexed(unique = true)
    private String email;
    
    @Indexed(unique = true)
    private String phoneNumber;
    
    @Indexed(unique = true)
    private String dni;
    
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}