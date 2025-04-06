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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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

        doctorsReport.sort(Comparator.comparing(DoctorReportDTO::noOfAppointments).reversed());

        return doctorsReport;
    }

    public List<MedicalServiceReportDTO> getMedicalServicesReport(LocalDate startDate, LocalDate endDate) {
        List<MedicalServiceReportDTO> medicalServicesReport= new ArrayList<>();
        List<MedicalService> medicalServices = medicalServiceRepository.findAll();

        for(MedicalService medicalService : medicalServices) {
            medicalServicesReport.add(new MedicalServiceReportDTO(medicalService.getId(), medicalService.getName(), appointmentRepository.countByMedicalServiceIdId(medicalService.getId(), startDate, endDate)));
        }

        medicalServicesReport.sort(Comparator.comparing(MedicalServiceReportDTO::noOfAppointments).reversed());

        return medicalServicesReport;
    }

    public void exportAppointmentsToCsv(String filePath, List<AppointmentDTO> appointments, List<DoctorReportDTO> doctors, List<MedicalServiceReportDTO> medicalServices) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("ID,Patient,Doctor,Service,Date,Time,Status");

            for (AppointmentDTO appt : appointments) {
                writer.printf("%d,%s,%s,%s,%s,%s,%s%n",
                        appt.id(),
                        appt.patientName(),
                        appt.doctor().name(),
                        appt.medicalService().name(),
                        appt.date(),
                        appt.time(),
                        appt.status()
                );
            }

            writer.println();

            writer.println("ID, Doctor Name, No. of Appointments");
            for (DoctorReportDTO doctor : doctors) {
                writer.printf("%d,%s,%d%n",
                        doctor.id(),
                        doctor.name(),
                        doctor.noOfAppointments()
                        );
            }

            writer.println();

            writer.println("ID, Service Name, No. of Appointments");
            for (MedicalServiceReportDTO service : medicalServices) {
                writer.printf("%d,%s,%d%n",
                        service.id(),
                        service.name(),
                        service.noOfAppointments()
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
