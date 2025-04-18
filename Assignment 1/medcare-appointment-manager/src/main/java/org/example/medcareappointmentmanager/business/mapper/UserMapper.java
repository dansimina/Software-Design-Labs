package org.example.medcareappointmentmanager.business.mapper;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.data.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);

    List<UserDTO> toDTO(List<User> users);
    List<User> toEntity(List<UserDTO> userDTOs);
}
