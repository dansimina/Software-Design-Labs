package org.example.medcareappointmentmanager.business.mapper;

import org.example.medcareappointmentmanager.business.dto.CreateUserDTO;
import org.example.medcareappointmentmanager.data.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateUserMapper {
    CreateUserDTO toDTO(User user);
    User toEntity(CreateUserDTO userWithPasswordDTO);
}
