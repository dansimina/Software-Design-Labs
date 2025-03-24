package org.example.medcareappointmentmanager.dataaccess;

import org.example.medcareappointmentmanager.data.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
}
