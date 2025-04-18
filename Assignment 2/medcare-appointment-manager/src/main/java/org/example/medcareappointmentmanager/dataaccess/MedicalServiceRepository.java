package org.example.medcareappointmentmanager.dataaccess;

import org.example.medcareappointmentmanager.data.MedicalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalServiceRepository extends JpaRepository<MedicalService, Long> {
}
