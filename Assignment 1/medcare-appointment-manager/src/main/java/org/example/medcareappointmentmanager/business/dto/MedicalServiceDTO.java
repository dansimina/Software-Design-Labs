package org.example.medcareappointmentmanager.business.dto;

import java.util.List;

public record MedicalServiceDTO(String name, Integer price, Integer duration, List<AppointmentDTO> appointments) {
}
