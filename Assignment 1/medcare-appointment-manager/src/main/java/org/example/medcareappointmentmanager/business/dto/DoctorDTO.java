package org.example.medcareappointmentmanager.business.dto;

import org.example.medcareappointmentmanager.data.Appointment;

import java.sql.Time;
import java.util.List;

public record DoctorDTO(String name, String specialization, Time startOfProgram, Time endOfProgram, List<AppointmentDTO> appointments) {
}
