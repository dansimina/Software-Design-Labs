package org.example.medcareappointmentmanager.business.mapper;

import org.example.medcareappointmentmanager.business.dto.UserTypeDTO;
import org.example.medcareappointmentmanager.data.UserType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {
    UserTypeDTO toDTO(UserType userType);
    UserType toEntity(UserTypeDTO userTypeDTO);
}
