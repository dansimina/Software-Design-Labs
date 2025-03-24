package org.example.medcareappointmentmanager.business.mapper;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.data.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}
