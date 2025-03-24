package org.example.medcareappointmentmanager.data;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.Date;

@Entity
public class Appointment extends AbstractEntity {
    @NotNull(message = "Patient name is required!")
    private String patientName;

    @NotNull(message = "Name is required!")
    private String name;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private MedicalService medicalService;

    @NotNull
    private Date date;

    @NotNull
    private Time time;

    @NotNull
    private AppointmentStatus status;

    public Appointment() {
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
