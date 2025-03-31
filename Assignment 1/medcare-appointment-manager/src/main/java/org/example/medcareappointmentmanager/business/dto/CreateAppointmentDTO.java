package org.example.medcareappointmentmanager.business.dto;

import java.sql.Time;
import java.util.Date;

public record CreateAppointmentDTO(String patientName, Long doctorId, Long serviceId, Date date, Time time, String status) {
}
