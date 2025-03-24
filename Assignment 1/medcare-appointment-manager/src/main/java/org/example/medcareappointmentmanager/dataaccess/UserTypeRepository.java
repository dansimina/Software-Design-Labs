package org.example.medcareappointmentmanager.dataaccess;

import org.example.medcareappointmentmanager.data.UserType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends CrudRepository<UserType, Long> {
    Optional<UserType> findByType(String type);
}
