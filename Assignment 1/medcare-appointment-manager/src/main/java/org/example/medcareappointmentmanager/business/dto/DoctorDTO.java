package org.example.medcareappointmentmanager.business.dto;

import java.sql.Time;

public record DoctorDTO(Integer id, String name, String specialization, Time startOfProgram, Time endOfProgram) {
}
