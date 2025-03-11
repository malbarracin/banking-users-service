package io.banking.whatsapp.users.domain.mapper;

import io.banking.whatsapp.users.domain.User;
import io.banking.whatsapp.users.domain.dto.UserRequestDTO;
import io.banking.whatsapp.users.domain.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between User entity and DTOs.
 * This interface uses MapStruct to automatically generate the implementation
 * for converting between the domain model and data transfer objects.
 *
 * @author Marcelo Alejandro Albarracín - marceloalejandro.albarracin@gmail.com
 * @version 1.0.0
 * @since 2024-03-19
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a UserRequestDTO to a User entity.
     * During conversion:
     * - Sets initial status as "ACTIVE"
     * - Ignores id, createdAt, and updatedAt fields (managed by the system)
     * - Maps dni to documentNumber field
     *
     * @param dto the UserRequestDTO containing the user data
     * @return a new User entity with the mapped data
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    User toEntity(UserRequestDTO dto);

    /**
     * Converts a User entity to a UserResponseDTO.
     * During conversion:
     * - Maps documentNumber to dni field
     * - Includes all other fields from the User entity
     *
     * @param user the User entity to convert
     * @return a UserResponseDTO containing the user data
     */
    UserResponseDTO toDto(User user);
}