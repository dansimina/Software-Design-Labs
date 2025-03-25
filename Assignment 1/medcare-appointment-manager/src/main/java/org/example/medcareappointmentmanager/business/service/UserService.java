package org.example.medcareappointmentmanager.business.service;

import org.example.medcareappointmentmanager.business.dto.CreateUserDTO;
import org.example.medcareappointmentmanager.business.dto.LoginDTO;
import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.mapper.CreateUserMapper;
import org.example.medcareappointmentmanager.business.mapper.UserMapper;
import org.example.medcareappointmentmanager.data.User;
import org.example.medcareappointmentmanager.data.UserType;
import org.example.medcareappointmentmanager.dataaccess.UserRepository;
import org.example.medcareappointmentmanager.dataaccess.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public UserDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.username()).orElse(null);

        if (user != null && passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            return userMapper.toDTO(user);
        }

        return null;
    }

    public UserDTO register(CreateUserDTO userDTO, String type) {
        UserType userType = userTypeRepository.findByType(type).orElse(null);

        if(userType != null) {
            User user = createUserMapper.toEntity(userDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setType(userType);

            User newUser = userRepository.save(user);
            return userMapper.toDTO(newUser);
        }

        return null;
    }

    public List<UserDTO> getUsersByType(String type) {
        UserType userType = userTypeRepository.findByType(type).orElse(null);
        if (userType == null) {
            return Collections.emptyList();
        }

        List<User> users = userRepository.findUsersByType(userType).orElse(Collections.emptyList());
        return userMapper.toDTO(users);
    }

}
