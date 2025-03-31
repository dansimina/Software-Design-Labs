package org.example.medcareappointmentmanager.dataaccess;

import org.example.medcareappointmentmanager.data.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query ("SELECT a FROM Appointment a WHERE a.date BETWEEN :startDate AND :endDate")
    public List<Appointment> findBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.doctor.id = :doctorId AND a.date BETWEEN :startDate AND :endDate")
    public long countByDoctorId(@Param("doctorId") Long doctorId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.medicalService.id = :medicalServiceId AND a.date BETWEEN :startDate AND :endDate")
    public long countByMedicalServiceIdId(@Param("medicalServiceId") Long medicalServiceId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
