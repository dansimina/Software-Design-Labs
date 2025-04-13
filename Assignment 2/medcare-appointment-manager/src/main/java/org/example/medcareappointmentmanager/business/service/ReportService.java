package org.example.medcareappointmentmanager.business.service;

import org.example.medcareappointmentmanager.business.dto.AppointmentDTO;
import org.example.medcareappointmentmanager.business.dto.DateIntervalDTO;
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

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    public List<AppointmentDTO> getAppointmentsReport(DateIntervalDTO dateInterval) {
        return appointmentMapper.toDTO((List<Appointment>) appointmentRepository.findBetweenDates(dateInterval.start(), dateInterval.end()));
    }

    public List<DoctorReportDTO> getDoctorsReport(DateIntervalDTO dateInterval) {
        List<DoctorReportDTO> doctorsReport= new ArrayList<>();
        List<Doctor> doctors = doctorRepository.findAll();

        for (Doctor doctor : doctors) {
            doctorsReport.add(new DoctorReportDTO(doctor.getId(), doctor.getName(), appointmentRepository.countByDoctorId(doctor.getId(), dateInterval.start(), dateInterval.end())));
        }

        doctorsReport.sort(Comparator.comparing(DoctorReportDTO::noOfAppointments).reversed());

        return doctorsReport;
    }

    public List<MedicalServiceReportDTO> getMedicalServicesReport(DateIntervalDTO dateInterval) {
        List<MedicalServiceReportDTO> medicalServicesReport= new ArrayList<>();
        List<MedicalService> medicalServices = medicalServiceRepository.findAll();

        for(MedicalService medicalService : medicalServices) {
            medicalServicesReport.add(new MedicalServiceReportDTO(medicalService.getId(), medicalService.getName(), appointmentRepository.countByMedicalServiceIdId(medicalService.getId(), dateInterval.start(), dateInterval.end())));
        }

        medicalServicesReport.sort(Comparator.comparing(MedicalServiceReportDTO::noOfAppointments).reversed());

        return medicalServicesReport;
    }

    public byte[] generateAppointmentsCsvContent(List<AppointmentDTO> appointments,
                                                 List<DoctorReportDTO> doctors,
                                                 List<MedicalServiceReportDTO> medicalServices) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
             PrintWriter writer = new PrintWriter(osw)) {

            writer.println("ID,Patient,Doctor,Service,Date,Time,Status");

            for (AppointmentDTO appt : appointments) {
                writer.printf("%d,%s,%s,%s,%s,%s,%s%n",
                        appt.id(),
                        appt.patientName(),
                        appt.doctor().name(),
                        appt.medicalService().name(),
                        appt.date(),
                        appt.time(),
                        appt.status());
            }

            writer.println();
            writer.println("ID,Doctor Name,No. of Appointments");
            for (DoctorReportDTO doctor : doctors) {
                writer.printf("%d,%s,%d%n", doctor.id(), doctor.name(), doctor.noOfAppointments());
            }

            writer.println();
            writer.println("ID,Service Name,No. of Appointments");
            for (MedicalServiceReportDTO service : medicalServices) {
                writer.printf("%d,%s,%d%n", service.id(), service.name(), service.noOfAppointments());
            }

            writer.flush();
            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate CSV", e);
        }
    }
}
