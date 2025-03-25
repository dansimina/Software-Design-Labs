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
        Optional<User> user = userRepository.findByUsername(loginDTO.username());

        if (user.isPresent() && passwordEncoder.matches(loginDTO.password(), user.get().getPassword())) {
            return userMapper.toDTO(user.get());
        }

        return null;
    }

    public UserDTO register(CreateUserDTO userDTO, String type) {
        Optional<UserType> userType = userTypeRepository.findByType(type);

        if(userType.isPresent()) {
            User user = createUserMapper.toEntity(userDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setType(userType.get());

            User newUser = userRepository.save(user);
            return userMapper.toDTO(newUser);
        }

        return null;
    }
}
