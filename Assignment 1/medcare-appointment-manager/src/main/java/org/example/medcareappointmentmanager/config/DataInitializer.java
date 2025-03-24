package org.example.medcareappointmentmanager.config;

import org.example.medcareappointmentmanager.data.User;
import org.example.medcareappointmentmanager.data.UserType;
import org.example.medcareappointmentmanager.dataaccess.UserRepository;
import org.example.medcareappointmentmanager.dataaccess.UserTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, UserTypeRepository userTypeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        UserType adminType = userTypeRepository.findByType("admin").orElse(null);
        if (adminType == null) {
            adminType = new UserType();
            adminType.setType("admin");
            userTypeRepository.save(adminType);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setType(adminType);
            userRepository.save(admin);
        }
    }
}
