package org.example.medcareappointmentmanager.business.service;

import org.example.medcareappointmentmanager.business.dto.MedicalServiceDTO;
import org.example.medcareappointmentmanager.business.mapper.MedicalServiceMapper;
import org.example.medcareappointmentmanager.business.validators.Validator;
import org.example.medcareappointmentmanager.data.MedicalService;
import org.example.medcareappointmentmanager.dataaccess.MedicalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalServiceService {
    @Autowired
    private MedicalServiceRepository medicalServiceRepository;

    @Autowired
    private MedicalServiceMapper medicalServiceMapper;

    private List<Validator> validators = new ArrayList<>();

    public MedicalServiceService() {
    }

    public MedicalServiceDTO save(MedicalServiceDTO medicalServiceDTO) {
        for(Validator validator : validators) {
            validator.validate(medicalServiceDTO);
        }

        MedicalService medicalService = medicalServiceMapper.toEntity(medicalServiceDTO);
        MedicalService savedMedicalService = medicalServiceRepository.save(medicalService);
        return medicalServiceMapper.toDTO(savedMedicalService);
    }

    public List<MedicalServiceDTO> findAll() {
        return medicalServiceMapper.toDTO((List<MedicalService>) medicalServiceRepository.findAll());
    }

    public void delete(MedicalServiceDTO medicalServiceDTO) {
        medicalServiceRepository.delete(medicalServiceMapper.toEntity(medicalServiceDTO));
    }

    public MedicalServiceDTO findById(Long id) {
        return medicalServiceMapper.toDTO(medicalServiceRepository.findById(id).get());
    }
}
