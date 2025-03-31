package org.example.medcareappointmentmanager.business.validators;

import org.example.medcareappointmentmanager.business.dto.CreateUserDTO;

public class UsernameValidator implements Validator<CreateUserDTO> {

    @Override
    public void validate(CreateUserDTO createUserDTO) {
        if (createUserDTO.username() == null || createUserDTO.username().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
    }
}
