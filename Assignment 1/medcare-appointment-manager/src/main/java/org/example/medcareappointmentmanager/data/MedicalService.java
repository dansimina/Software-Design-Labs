package org.example.medcareappointmentmanager.data;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class MedicalService extends AbstractEntity {
    @NotNull(message = "Name is required!")
    private String name;

    @NotNull(message = "Price is required!")
    private Integer price;

    @NotNull(message = "Duration is required!")
    private Integer duration;

    @OneToMany(mappedBy = "medicalService")
    private List<Appointment> appointments;

    public MedicalService() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
