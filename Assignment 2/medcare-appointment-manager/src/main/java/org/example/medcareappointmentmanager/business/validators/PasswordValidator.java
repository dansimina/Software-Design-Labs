package org.example.medcareappointmentmanager.business.validators;

import org.example.medcareappointmentmanager.business.dto.CreateUserDTO;

public class PasswordValidator implements Validator<CreateUserDTO> {

    @Override
    public void validate(CreateUserDTO createUserDTO) {
        if (createUserDTO.password() == null || createUserDTO.password().length() < 5) {
            throw new IllegalArgumentException("Password must be at least 5 characters long");
        }
    }
}
