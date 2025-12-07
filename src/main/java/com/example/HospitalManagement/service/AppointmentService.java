package com.example.HospitalManagement.service;

import com.example.HospitalManagement.models.Appointment;
import com.example.HospitalManagement.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository _appointmentRepository;
    public static final Logger logger =  LoggerFactory.getLogger(AppointmentService.class);

    public AppointmentService(AppointmentRepository appointmentRepository){
        this._appointmentRepository = appointmentRepository;
    }

    public Page<Appointment> getAllAppointments(int page, int size){
        try{
            Pageable pageable = PageRequest.of(page, size);
            return  _appointmentRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Facing Issue in fetching all appointments : {}", e.getMessage());
            return null;
        }
    }

    public Appointment getAppointmentById(long id){
        try{
            Optional<Appointment> appointment = _appointmentRepository.findById(id);
            return appointment.orElse(null);
        }catch (Exception e){
            logger.error("Facing Issue in fetching appointment with {} : {}", id, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Appointment createAppointment(Appointment newAppointment){
        try{
            _appointmentRepository.save(newAppointment);
            return newAppointment;
        } catch (Exception e) {
            logger.error("Facing Issue while creating a new appointment : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Appointment updateAppointment(long id, Appointment updatedAppointment){
        try{
            Optional<Appointment> existingAppointment = _appointmentRepository.findById(id);
            if(existingAppointment.isPresent()){
                Appointment a = existingAppointment.get();
                a.setPatientId(updatedAppointment.getPatientId());
                a.setDoctorId(updatedAppointment.getDoctorId());
                a.setAppointmentTime(updatedAppointment.getAppointmentTime());
                _appointmentRepository.save(a);
                return updatedAppointment;
            }else{
                logger.error("Unable to find repository with id : {}", id);
                return null;
            }

        } catch (Exception e) {
            logger.error("Facing issue while updating Appointment {} : {}", id, e.getMessage());
            return null;
        }
    }

    public void deleteAppointment(long id){
        try{
            _appointmentRepository.deleteById(id);
        }catch (Exception e){
            logger.error("Facing some issue while deleting appointment {} : {}", id, e.getMessage());
        }
    }
}
