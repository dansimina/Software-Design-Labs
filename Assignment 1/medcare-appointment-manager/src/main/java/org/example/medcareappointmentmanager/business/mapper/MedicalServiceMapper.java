package org.example.medcareappointmentmanager.business.mapper;

import org.example.medcareappointmentmanager.data.MedicalService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicalServiceMapper {
    MedicalServiceDTO toDTO(MedicalService medicalService);
    MedicalService toEntity(MedicalServiceDTO medicalServiceDTO);

    List<MedicalServiceDTO> toDTO(List<MedicalService> medicalServices);
    List<MedicalService> toEntity(List<MedicalServiceDTO> medicalServiceDTOs);
}
