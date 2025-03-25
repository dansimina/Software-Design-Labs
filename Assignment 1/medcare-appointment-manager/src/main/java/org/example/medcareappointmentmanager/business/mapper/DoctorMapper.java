package org.example.medcareappointmentmanager.business.mapper;

import org.example.medcareappointmentmanager.business.dto.DoctorDTO;
import org.example.medcareappointmentmanager.data.Doctor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO toDTO(Doctor doctor);
    Doctor toEntity(DoctorDTO doctorDTO);

    List<DoctorDTO> toDTO(List<Doctor> doctors);
    List<Doctor> toEntity(List<DoctorDTO> doctorDTOs);
}
