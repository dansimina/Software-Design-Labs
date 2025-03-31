package org.example.medcareappointmentmanager.business.dto;

import java.sql.Time;

public record DoctorDTO(Long id, String name, String specialization, Time startOfProgram, Time endOfProgram) {
}
