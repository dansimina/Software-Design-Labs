package org.example.medcareappointmentmanager.presentation;

import org.example.medcareappointmentmanager.business.dto.*;
import org.example.medcareappointmentmanager.business.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private MedicalServiceService medicalServiceService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/receptionists")
    public ResponseEntity<List<UserDTO>> getAllReceptionists() {
        List<UserDTO> receptionists = userService.getByType("receptionist");
        return new ResponseEntity<>(receptionists, HttpStatus.OK);
    }

    @PostMapping("/receptionists")
    public ResponseEntity<UserDTO> createReceptionist(@RequestBody CreateUserDTO createUserDTO) {
        try {
            UserDTO user = userService.save(createUserDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.findAll();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @PostMapping("/doctors")
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        try {
            DoctorDTO doctor = doctorService.save(doctorDTO);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/services")
    public ResponseEntity<List<MedicalServiceDTO>> getAllMedicalServices() {
        List<MedicalServiceDTO> services = medicalServiceService.findAll();
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @PostMapping("/services")
    public ResponseEntity<MedicalServiceDTO> createMedicalService(@RequestBody MedicalServiceDTO medicalServiceDTO) {
        try {
            MedicalServiceDTO medicalService = medicalServiceService.save(medicalServiceDTO);
            return new ResponseEntity<>(medicalService, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments(@RequestBody DateIntervalDTO dateInterval) {
        List<AppointmentDTO> appointments = reportService.getAppointmentsReport(dateInterval);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PostMapping("/doctors/report")
    public ResponseEntity<List<DoctorReportDTO>> getAllDoctorReports(@RequestBody DateIntervalDTO dateInterval) {
        List<DoctorReportDTO> doctorReport = reportService.getDoctorsReport(dateInterval);
        return new ResponseEntity<>(doctorReport, HttpStatus.OK);
    }

    @PostMapping("/services/report")
    public ResponseEntity<List<MedicalServiceReportDTO>> getAllServiceReports(@RequestBody DateIntervalDTO dateInterval) {
        List<MedicalServiceReportDTO> medicalServiceReport = reportService.getMedicalServicesReport(dateInterval);
        return new ResponseEntity<>(medicalServiceReport, HttpStatus.OK);
    }
}
