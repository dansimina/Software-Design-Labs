package org.example.medcareappointmentmanager.presentation;

import org.example.medcareappointmentmanager.business.dto.AppointmentDTO;
import org.example.medcareappointmentmanager.business.service.AppointmentService;
import org.example.medcareappointmentmanager.business.service.DoctorService;
import org.example.medcareappointmentmanager.business.service.MedicalServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receptionist")
public class ReceptionistController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private MedicalServiceService medicalServiceService;

    @GetMapping("appointments")
    public ResponseEntity<List<AppointmentDTO>> getAppointments() {
        List<AppointmentDTO> appointments = appointmentService.findAll();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PostMapping("appointments")
    public ResponseEntity<AppointmentDTO> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            AppointmentDTO appointment = appointmentService.save(appointmentDTO);
            return new ResponseEntity<>(appointment, HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
