package com.example.HospitalManagement.repository;

import com.example.HospitalManagement.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    //Here we are passing the entity type and the primary key type
    //Spring JPA will automatically create the methods for crud operations
}
