package org.example.medcareappointmentmanager.business.dto;

import java.sql.Time;
import java.time.LocalDate;

public record CreateAppointmentDTO(Long id, String patientName, Long doctorId, Long serviceId, LocalDate date, Time time, String status) {
}
