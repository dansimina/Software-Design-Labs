package org.example.medcareappointmentmanager.business.service;

import org.example.medcareappointmentmanager.business.dto.AppointmentDTO;
import org.example.medcareappointmentmanager.business.dto.DoctorReportDTO;
import org.example.medcareappointmentmanager.business.dto.MedicalServiceReportDTO;
import org.example.medcareappointmentmanager.business.mapper.AppointmentMapper;
import org.example.medcareappointmentmanager.data.Appointment;
import org.example.medcareappointmentmanager.data.Doctor;
import org.example.medcareappointmentmanager.data.MedicalService;
import org.example.medcareappointmentmanager.dataaccess.AppointmentRepository;
import org.example.medcareappointmentmanager.dataaccess.DoctorRepository;
import org.example.medcareappointmentmanager.dataaccess.MedicalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MedicalServiceRepository medicalServiceRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    public List<AppointmentDTO> getAppointmentsReport(LocalDate startDate, LocalDate endDate) {
        return appointmentMapper.toDTO((List<Appointment>) appointmentRepository.findBetweenDates(startDate, endDate));
    }

    public List<DoctorReportDTO> getDoctorsReport(LocalDate startDate, LocalDate endDate) {
        List<DoctorReportDTO> doctorsReport= new ArrayList<>();
        List<Doctor> doctors = doctorRepository.findAll();

        for (Doctor doctor : doctors) {
            doctorsReport.add(new DoctorReportDTO(doctor.getId(), doctor.getName(), appointmentRepository.countByDoctorId(doctor.getId(), startDate, endDate)));
        }

        return doctorsReport;
    }

    public List<MedicalServiceReportDTO> getMedicalServicesReport(LocalDate startDate, LocalDate endDate) {
        List<MedicalServiceReportDTO> medicalServicesReport= new ArrayList<>();
        List<MedicalService> medicalServices = medicalServiceRepository.findAll();

        for(MedicalService medicalService : medicalServices) {
            medicalServicesReport.add(new MedicalServiceReportDTO(medicalService.getId(), medicalService.getName(), appointmentRepository.countByMedicalServiceIdId(medicalService.getId(), startDate, endDate)));
        }

        return medicalServicesReport;
    }
}
