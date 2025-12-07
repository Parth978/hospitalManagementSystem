package com.example.HospitalManagement.service;

import com.example.HospitalManagement.models.Patient;
import com.example.HospitalManagement.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository _patientRepository;

    public Page<Patient> getAllPatient(int page, int size){
        try {
            Pageable pageable = PageRequest.of(page, size);
            return _patientRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("An error occurred while fetching patient : {}", e.getMessage());
            return null;
        }
    }

    public Patient createPatient(Patient newPatient){
        try{
            return _patientRepository.save(newPatient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Patient getPatientById(long id){
        try{
            Optional<Patient> patient = _patientRepository.findById(id);
            return patient.orElse(null);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deletePatient(long id){
        try{
            logger.info("Deleting patient with id : {}", id);
            _patientRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Patient updatePatient(Patient newPatient, long id){
        try{
            Optional<Patient> existingPatient = _patientRepository.findById(id);
            if(existingPatient.isPresent()){
                Patient p = existingPatient.get();
                p.setName(newPatient.getName());
                p.setAge(newPatient.getAge());
                p.setGender(newPatient.getGender());
                _patientRepository.save(p);
                return newPatient;
            }else{
                logger.error("Patient with Id not found");
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
