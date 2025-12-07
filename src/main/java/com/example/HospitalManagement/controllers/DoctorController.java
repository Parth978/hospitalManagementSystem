package com.example.HospitalManagement.controllers;
import com.example.HospitalManagement.models.Doctor;
import com.example.HospitalManagement.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @GetMapping
    public Page<Doctor> getAllDoctors(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return doctorService.getAllDoctorProfile(page, size);
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable long id){
        return doctorService.getDoctorProfileById(id);
    }

    @PostMapping
    public Doctor createAppointment(@RequestBody Doctor newDoctor){
        return doctorService.createDoctorProfile(newDoctor);
    }
    @PutMapping("/{id}")
    public Doctor updateAppointment(@PathVariable long id, @RequestBody Doctor updatedDoctor){
        return doctorService.updateDoctorProfile(id, updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable long id){
        doctorService.deleteDoctorProfile(id);
        logger.info("Successfully Deleted the Doctor Profile");
    }
}
