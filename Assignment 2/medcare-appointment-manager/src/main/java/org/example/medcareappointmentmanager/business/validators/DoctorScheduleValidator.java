package org.example.medcareappointmentmanager.business.validators;

import org.example.medcareappointmentmanager.business.dto.DoctorDTO;

public class DoctorScheduleValidator implements Validator<DoctorDTO> {

    @Override
    public void validate(DoctorDTO doctorDTO) {
        if(doctorDTO.startOfProgram().compareTo(doctorDTO.endOfProgram()) >= 0) {
            throw new IllegalArgumentException("The schedule is incorrect!");
        }
    }
}
