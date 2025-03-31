package org.example.medcareappointmentmanager.business.validators;

import org.example.medcareappointmentmanager.data.Appointment;

public class AppointmentValidator implements Validator<Appointment> {

    @Override
    public void validate(Appointment appointment) {

        if(!(appointment.getTime().compareTo(appointment.getDoctor().getStartOfProgram()) > 0 && appointment.getTime().compareTo(appointment.getDoctor().getEndOfProgram()) < 0)){
            throw new IllegalArgumentException("The appointment should be during the doctor's office hours.");
        }
    }
}
