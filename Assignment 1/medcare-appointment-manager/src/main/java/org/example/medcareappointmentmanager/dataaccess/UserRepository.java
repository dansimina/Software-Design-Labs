package org.example.medcareappointmentmanager.dataaccess;

import org.example.medcareappointmentmanager.data.User;
import org.example.medcareappointmentmanager.data.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<List<User>> findUsersByType(UserType type);
}
