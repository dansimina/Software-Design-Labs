package org.example.medcareappointmentmanager.business.validators;

import org.example.medcareappointmentmanager.business.dto.AppointmentDTO;

public class PatientNameValidator implements Validator<AppointmentDTO>{
    @Override
    public void validate(AppointmentDTO appointmentDTO) {
        if(appointmentDTO.patientName().isEmpty()){
            throw new IllegalArgumentException("Patient name cannot be empty");
        }
    }
}
