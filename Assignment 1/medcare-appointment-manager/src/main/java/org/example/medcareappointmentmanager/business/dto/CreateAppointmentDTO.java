package org.example.medcareappointmentmanager.business.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateAppointmentDTO(Long id, String patientName, Long doctorId, Long serviceId, LocalDate date, LocalTime time, String status) {
}
