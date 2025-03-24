package org.example.medcareappointmentmanager.business.mapper;

import org.example.medcareappointmentmanager.business.dto.MedicalServiceDTO;
import org.example.medcareappointmentmanager.data.MedicalService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalServiceMapper {
    MedicalServiceDTO toDTO(MedicalService medicalService);
    MedicalService toEntity(MedicalServiceDTO medicalServiceDTO);
}
