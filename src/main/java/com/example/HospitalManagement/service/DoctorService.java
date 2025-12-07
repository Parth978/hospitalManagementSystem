package com.example.HospitalManagement.service;

import com.example.HospitalManagement.models.Appointment;
import com.example.HospitalManagement.models.Doctor;
import com.example.HospitalManagement.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DoctorService {

    public static final Logger logger = LoggerFactory.getLogger(DoctorService.class);
    private final DoctorRepository _doctorRepository;

    public DoctorService(DoctorRepository doctorRepository){
        this._doctorRepository = doctorRepository;
    }

    public Page<Doctor> getAllDoctorProfile(int page, int size){
        try{
            Pageable pageable = PageRequest.of(page, size);
            return  _doctorRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Facing Issue in fetching all Doctors : {}", e.getMessage());
            return null;
        }
    }

    public Doctor getDoctorProfileById(long id){
        try{
            Optional<Doctor> doctor = _doctorRepository.findById(id);
            return doctor.orElse(null);
        }catch (Exception e){
            logger.error("Facing Issue in fetching Doctor with {} : {}", id, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Doctor createDoctorProfile(Doctor newDoctor){
        try{
           _doctorRepository.save(newDoctor);
            return newDoctor;
        } catch (Exception e) {
            logger.error("Facing Issue while creating a new appointment : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Doctor updateDoctorProfile(long id, Doctor updatedDoctor){
        try{
            Optional<Doctor> existingDoctor = _doctorRepository.findById(id);
            if(existingDoctor.isPresent()){
                Doctor d = existingDoctor.get();
                d.setAge(updatedDoctor.getAge());
                d.setName(updatedDoctor.getName());
                d.setSpeciality(updatedDoctor.getSpeciality());
                _doctorRepository.save(d);
                return updatedDoctor;
            }else{
                logger.error("Unable to find Doctor with id : {}", id);
                return null;
            }

        } catch (Exception e) {
            logger.error("Facing issue while updating Doctor {} : {}", id, e.getMessage());
            return null;
        }
    }

    public void deleteDoctorProfile(long id){
        try{
            _doctorRepository.deleteById(id);
        }catch (Exception e){
            logger.error("Facing some issue while deleting doctor profile {} : {}", id, e.getMessage());
        }
    }
}
