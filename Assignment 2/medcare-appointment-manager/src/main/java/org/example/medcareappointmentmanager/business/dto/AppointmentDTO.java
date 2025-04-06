package org.example.medcareappointmentmanager.business.dto;

import org.example.medcareappointmentmanager.data.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentDTO(Long id, String patientName, DoctorDTO doctor, MedicalServiceDTO medicalService, LocalDate date, LocalTime time, AppointmentStatus status) {
}
