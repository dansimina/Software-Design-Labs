package org.example.medcareappointmentmanager.data;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.time.LocalDate;

@Entity
public class Appointment extends AbstractEntity {
    @NotNull(message = "Patient name is required!")
    private String patientName;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private MedicalService medicalService;

    @NotNull
    private LocalDate date;

    @NotNull
    private Time time;

    @NotNull
    private AppointmentStatus status;

    public Appointment() {
    }

    public Appointment(Long id, String patientName, Doctor doctor, MedicalService medicalService, LocalDate date, Time time, AppointmentStatus status) {
        this.id = id;
        this.patientName = patientName;
        this.doctor = doctor;
        this.medicalService = medicalService;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }

    public void setMedicalService(MedicalService medicalService) {
        this.medicalService = medicalService;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
