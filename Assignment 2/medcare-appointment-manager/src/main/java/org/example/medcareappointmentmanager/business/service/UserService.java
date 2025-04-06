package org.example.medcareappointmentmanager.business.service;

import org.example.medcareappointmentmanager.business.dto.CreateUserDTO;
import org.example.medcareappointmentmanager.business.dto.LoginDTO;
import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.mapper.CreateUserMapper;
import org.example.medcareappointmentmanager.business.mapper.UserMapper;
import org.example.medcareappointmentmanager.business.validators.PasswordValidator;
import org.example.medcareappointmentmanager.business.validators.UsernameValidator;
import org.example.medcareappointmentmanager.business.validators.Validator;
import org.example.medcareappointmentmanager.data.User;
import org.example.medcareappointmentmanager.data.UserType;
import org.example.medcareappointmentmanager.dataaccess.UserRepository;
import org.example.medcareappointmentmanager.dataaccess.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CreateUserMapper createUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private List<Validator> validators = new ArrayList<>();

    public UserService() {
        validators.add(new UsernameValidator(this));
        validators.add(new PasswordValidator());
    }

    public UserDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.username()).orElse(null);

        if (user != null && passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            return userMapper.toDTO(user);
        }

        return null;
    }

    public UserDTO save(CreateUserDTO userDTO, String type) {
        UserType userType = userTypeRepository.findByType(type).orElse(null);

        if(userType != null) {
            for(Validator validator : validators) {
                validator.validate(userDTO);
            }

            User user = createUserMapper.toEntity(userDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setType(userType);

            User newUser = userRepository.save(user);
            return userMapper.toDTO(newUser);
        }

        throw new IllegalArgumentException("Usertype not found");
    }

    public List<UserDTO> getByType(String type) {
        UserType userType = userTypeRepository.findByType(type).orElse(null);
        if (userType == null) {
            return Collections.emptyList();
        }

        List<User> users = userRepository.findUsersByType(userType).orElse(Collections.emptyList());
        return userMapper.toDTO(users);
    }

    public List<UserDTO> getByUsername(String username) {
        return userMapper.toDTO(userRepository.findUsersByUsername(username).orElse(Collections.emptyList()));
    }
}
