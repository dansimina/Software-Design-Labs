package org.example.medcareappointmentmanager.business.dto;

import java.util.List;

public record UserDTO (Long id, String type, List<UserDTO> users) {}
