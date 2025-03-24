package org.example.medcareappointmentmanager.dataaccess;

import org.example.medcareappointmentmanager.data.MedicalService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalServiceRepository extends CrudRepository<MedicalService, Long> {
}
