package org.example.medcareappointmentmanager.business.dto;

import org.example.medcareappointmentmanager.data.AppointmentStatus;

import java.sql.Time;
import java.util.Date;

public record AppointmentDTO(Long id, String patientName, DoctorDTO doctor, MedicalServiceDTO medicalService, Date data, Time time, AppointmentStatus status) {
}
