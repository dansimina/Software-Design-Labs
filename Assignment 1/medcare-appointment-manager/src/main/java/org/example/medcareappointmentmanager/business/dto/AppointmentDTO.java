package org.example.medcareappointmentmanager.business.dto;

import org.example.medcareappointmentmanager.data.AppointmentStatus;
import org.example.medcareappointmentmanager.data.Doctor;
import org.example.medcareappointmentmanager.data.MedicalService;

import javax.xml.crypto.Data;
import java.sql.Time;

public record AppointmentDTO(String patientName, DoctorDTO doctor, MedicalServiceDTO medicalService, Data data, Time time, AppointmentStatus status) {
}
