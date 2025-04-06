package org.example.medcareappointmentmanager.business.dto;

import java.time.LocalTime;

public record DoctorDTO(Long id, String name, String specialization, LocalTime startOfProgram, LocalTime endOfProgram) {
}
