package org.example.medcareappointmentmanager.business.validators;

import org.example.medcareappointmentmanager.business.dto.CreateUserDTO;
import org.example.medcareappointmentmanager.business.service.UserService;
import org.springframework.stereotype.Component;


@Component
public class UsernameValidator implements Validator<CreateUserDTO> {
    private final UserService userService;

    public UsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void validate(CreateUserDTO createUserDTO) {
        if (createUserDTO == null || createUserDTO.username() == null || createUserDTO.username().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (!userService.getByUsername(createUserDTO.username()).isEmpty()) {
            throw new IllegalArgumentException("Username already exists");
        }
    }
}

