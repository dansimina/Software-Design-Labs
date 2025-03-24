package org.example.medcareappointmentmanager.business.service;

import org.example.medcareappointmentmanager.business.dto.LoginDTO;
import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.dto.UserTypeDTO;
import org.example.medcareappointmentmanager.business.mapper.UserMapper;
import org.example.medcareappointmentmanager.data.User;
import org.example.medcareappointmentmanager.dataaccess.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO login(LoginDTO loginDTO) {
        Optional<User> user = userRepository.findByUsername(loginDTO.username());

        if (user.isPresent() && passwordEncoder.matches(loginDTO.password(), user.get().getPassword())) {
//            UserDTO userDTO = new UserDTO(user.get().getId(), user.get().getUsername(), new UserTypeDTO(user.get().getType().getId(), user.get().getType().getType()));
//            System.out.println(userDTO.username());
            return userMapper.toDTO(user.get());
        }

        return null;
    }

}
