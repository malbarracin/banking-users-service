package io.banking.whatsapp.users.domain.mapper;

import io.banking.whatsapp.users.domain.User;
import io.banking.whatsapp.users.domain.dto.UserRequestDTO;
import io.banking.whatsapp.users.domain.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "documentNumber", source = "dni")
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "dni", source = "documentNumber")
    UserResponseDTO toDto(User user);
}