package org.example.medcareappointmentmanager.business.service;

import org.example.medcareappointmentmanager.business.dto.AppointmentDTO;
import org.example.medcareappointmentmanager.business.dto.CreateAppointmentDTO;
import org.example.medcareappointmentmanager.business.mapper.AppointmentMapper;
import org.example.medcareappointmentmanager.business.validators.AppointmentValidator;
import org.example.medcareappointmentmanager.business.validators.Validator;
import org.example.medcareappointmentmanager.data.Appointment;
import org.example.medcareappointmentmanager.data.AppointmentStatus;
import org.example.medcareappointmentmanager.data.Doctor;
import org.example.medcareappointmentmanager.data.MedicalService;
import org.example.medcareappointmentmanager.dataaccess.AppointmentRepository;
import org.example.medcareappointmentmanager.dataaccess.DoctorRepository;
import org.example.medcareappointmentmanager.dataaccess.MedicalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MedicalServiceRepository medicalServiceRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    private List<Validator> validators = new ArrayList<>();

    public AppointmentService() {
        validators.add(new AppointmentValidator());
    }

    public AppointmentDTO save(CreateAppointmentDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.doctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        MedicalService service = medicalServiceRepository.findById(dto.serviceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Appointment appointment = new Appointment(
                dto.id(),
                dto.patientName(),
                doctor,
                service,
                dto.date(),
                dto.time(),
                AppointmentStatus.fromDisplayName(dto.status())
        );

        for(Validator validator : validators){
            validator.validate(appointment);
        }

        return appointmentMapper.toDTO(appointmentRepository.save(appointment));
    }

    public List<AppointmentDTO> findAll() {
        return appointmentMapper.toDTO(appointmentRepository.findAll());
    }
}
