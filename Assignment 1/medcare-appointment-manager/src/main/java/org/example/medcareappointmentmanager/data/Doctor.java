package org.example.medcareappointmentmanager.data;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor extends AbstractEntity {
    @NotNull(message = "Name is required!")
    private String name;

    @NotNull(message = "Specialization is required!")
    private String specialization;

    @NotNull(message = "Time is required!")
    private Time startOfProgram;

    @NotNull(message = "Time is required!")
    private Time endOfProgram;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor() {
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

    public Time getStartOfProgram() {
        return startOfProgram;
    }

    public void setStartOfProgram(Time startOfProgram) {
        this.startOfProgram = startOfProgram;
    }

    public Time getEndOfProgram() {
        return endOfProgram;
    }

    public void setEndOfProgram(Time endOfProgram) {
        this.endOfProgram = endOfProgram;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
