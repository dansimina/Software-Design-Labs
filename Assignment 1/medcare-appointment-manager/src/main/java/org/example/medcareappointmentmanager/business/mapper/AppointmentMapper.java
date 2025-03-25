package org.example.medcareappointmentmanager.business.mapper;

import org.example.medcareappointmentmanager.business.dto.AppointmentDTO;
import org.example.medcareappointmentmanager.data.Appointment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentDTO toDTO(Appointment appointment);
    Appointment toEntity(AppointmentDTO appointmentDTO);

    List<AppointmentDTO> toDTO(List<Appointment> appointments);
    List<Appointment> toEntity(List<AppointmentDTO> appointmentDTOs);
}
