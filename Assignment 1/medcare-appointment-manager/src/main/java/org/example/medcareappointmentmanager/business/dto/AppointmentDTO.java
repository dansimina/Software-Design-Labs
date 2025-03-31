package org.example.medcareappointmentmanager.business.dto;

import org.example.medcareappointmentmanager.data.AppointmentStatus;

import java.sql.Time;
import java.time.LocalDate;

public record AppointmentDTO(Long id, String patientName, DoctorDTO doctor, MedicalServiceDTO medicalService, LocalDate date, Time time, AppointmentStatus status) {
}
