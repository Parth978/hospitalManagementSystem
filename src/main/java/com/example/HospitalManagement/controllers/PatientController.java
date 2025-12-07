package com.example.HospitalManagement.controllers;

import com.example.HospitalManagement.models.Patient;
import com.example.HospitalManagement.service.PatientService;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
//Controller forms the first layer of out application
//Controller -- service -- repository

public class PatientController {

    //@Autowired (Not Recommended)
    private final PatientService _patientService;

    public PatientController(PatientService patientService){
        this._patientService = patientService;
    }
    @GetMapping
    public Page<Patient> getAllPatients(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size){
       return _patientService.getAllPatient(page, size);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient newPatient){
        return _patientService.createPatient(newPatient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@RequestBody Patient newPatient, @PathVariable long id){
        return _patientService.updatePatient(newPatient, id);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable long id) throws InterruptedException {
        try {
            return _patientService.getPatientById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable long id){
        _patientService.deletePatient(id);
    }
}
