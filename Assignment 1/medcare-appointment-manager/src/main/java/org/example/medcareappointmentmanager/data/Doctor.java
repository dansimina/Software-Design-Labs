package org.example.medcareappointmentmanager.data;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor extends AbstractEntity {
    @NotNull(message = "Name is required!")
    private String name;

    @NotNull(message = "Specialization is required!")
    private String specialization;

    @NotNull(message = "Time is required!")
    private LocalTime startOfProgram;

    @NotNull(message = "Time is required!")
    private LocalTime endOfProgram;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(String name, String specialization, LocalTime startOfProgram, LocalTime endOfProgram, List<Appointment> appointments) {
        this.name = name;
        this.specialization = specialization;
        this.startOfProgram = startOfProgram;
        this.endOfProgram = endOfProgram;
        this.appointments = appointments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalTime getStartOfProgram() {
        return startOfProgram;
    }

    public void setStartOfProgram(LocalTime startOfProgram) {
        this.startOfProgram = startOfProgram;
    }

    public LocalTime getEndOfProgram() {
        return endOfProgram;
    }

    public void setEndOfProgram(LocalTime endOfProgram) {
        this.endOfProgram = endOfProgram;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
