package com.example.HospitalManagement.controllers;

import com.example.HospitalManagement.models.Appointment;
import com.example.HospitalManagement.service.AppointmentService;
import com.example.HospitalManagement.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    private WebhookService webhookService;
    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }
    @GetMapping
    public Page<Appointment> getAllAppointments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return appointmentService.getAllAppointments(page, size);
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable long id){
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment newAppointment){
        Appointment appointment =  appointmentService.createAppointment(newAppointment);
        Map<String, Object> payload = new HashMap<>();
        payload.put("appointmentId", appointment.getId());
        payload.put("patientId", appointment.getPatientId());
        payload.put("doctorId", appointment.getDoctorId());
        payload.put("time", appointment.getAppointmentTime());
        String webhookurl = "http://localhost:8081/webhook";
        webhookService.sendWebhook(webhookurl, payload);

        return appointment;
    }
    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable long id, @RequestBody Appointment updatedAppointment){
        return appointmentService.updateAppointment(id, updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable long id){
        appointmentService.deleteAppointment(id);
        System.out.println("Deleting....");
    }
}
