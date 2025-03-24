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
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeData(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        return args -> {
            UserType adminType = userTypeRepository.findByType("admin")
                    .orElseGet(() -> {
                        UserType newType = new UserType();
                        newType.setType("admin");
                        return userTypeRepository.save(newType);
                    });

            if (userRepository.findByUsername("admin").isEmpty()) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword("admin"); // Ar trebui criptată în practică
                adminUser.setType(adminType);
                userRepository.save(adminUser);
                System.out.println("Admin user created.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
