package org.example.medcareappointmentmanager.dataaccess;

import org.example.medcareappointmentmanager.data.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.doctor.id = :doctorId")
    public long countByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.medicalService.id = :medicalServiceId")
    public long countByMedicalServiceIdId(@Param("medicalServiceId") Long medicalServiceId);
}
