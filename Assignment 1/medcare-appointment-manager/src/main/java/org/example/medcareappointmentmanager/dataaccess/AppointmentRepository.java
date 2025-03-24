package org.example.medcareappointmentmanager.dataaccess;

import org.example.medcareappointmentmanager.data.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
