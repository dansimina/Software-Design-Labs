package org.example.medcareappointmentmanager.business.service;

import org.example.medcareappointmentmanager.business.dto.DoctorDTO;
import org.example.medcareappointmentmanager.business.mapper.DoctorMapper;
import org.example.medcareappointmentmanager.business.validators.DoctorScheduleValidator;
import org.example.medcareappointmentmanager.business.validators.Validator;
import org.example.medcareappointmentmanager.data.Doctor;
import org.example.medcareappointmentmanager.dataaccess.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    List<Validator> validators = new ArrayList<Validator>();

    private DoctorService() {
        validators.add(new DoctorScheduleValidator());
    }

    public DoctorDTO save(DoctorDTO doctorDTO) {
        for(Validator validator : validators) {
            validator.validate(doctorDTO);
        }

        Doctor doctor = doctorMapper.toEntity(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDTO(savedDoctor);
    }

    public List<DoctorDTO> findAll() {
        return doctorMapper.toDTO((List<Doctor>) doctorRepository.findAll());
    }

    public void delete(DoctorDTO doctorDTO) {
        doctorRepository.delete(doctorMapper.toEntity(doctorDTO));
    }

    public DoctorDTO findById(Long id) {
        return doctorMapper.toDTO(doctorRepository.findById(id).get());
    }
}
