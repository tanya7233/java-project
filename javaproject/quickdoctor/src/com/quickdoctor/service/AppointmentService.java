package com.quickdoctor.service;

import com.quickdoctor.dao.AppointmentDAO;
import com.quickdoctor.dao.AppointmentDAOImpl;
import com.quickdoctor.dao.DoctorDAO;
import com.quickdoctor.dao.DoctorDAOImpl;
import com.quickdoctor.model.Appointment;
import com.quickdoctor.model.AppointmentStatus;
import com.quickdoctor.model.Doctor;
import com.quickdoctor.model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


public class AppointmentService {
    
    private final AppointmentDAO appointmentDAO;
    private final DoctorDAO doctorDAO;
    
    public AppointmentService() {
        this.appointmentDAO = new AppointmentDAOImpl();
        this.doctorDAO = new DoctorDAOImpl();
    }
    
    
    public boolean bookAppointment(String patientId, String doctorId, LocalDate date,
                                   LocalTime startTime, LocalTime endTime, String reason) {
       
        if (date.isBefore(LocalDate.now())) {
            System.out.println("Cannot book appointment for past dates");
            return false;
        }
        
        
        if (!isTimeSlotAvailable(doctorId, date, startTime, endTime)) {
            System.out.println("Time slot is not available");
            return false;
        }
        
        
        String appointmentId = "APT-" + UUID.randomUUID().toString().substring(0, 8);
        Appointment appointment = new Appointment(appointmentId, patientId, doctorId,
                                                 date, startTime, endTime, reason);
        appointmentDAO.save(appointment);
        
        System.out.println("Appointment booked successfully! Appointment ID: " + appointmentId);
        return true;
    }
    
    
    private boolean isTimeSlotAvailable(String doctorId, LocalDate date, 
                                       LocalTime startTime, LocalTime endTime) {
        List<Appointment> existingAppointments = appointmentDAO.findByDoctorAndDate(doctorId, date);
        
        for (Appointment apt : existingAppointments) {
            
            if (apt.getStatus() == AppointmentStatus.CANCELLED) {
                continue;
            }
            
            
            if (timesOverlap(startTime, endTime, apt.getStartTime(), apt.getEndTime())) {
                return false;
            }
        }
        
        return true;
    }
    
    
    private boolean timesOverlap(LocalTime start1, LocalTime end1, 
                                LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
    
    
    public boolean cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentDAO.findById(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found");
            return false;
        }
        
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentDAO.update(appointment);
        System.out.println("Appointment cancelled successfully");
        return true;
    }
    
   
    public boolean confirmAppointment(String appointmentId) {
        Appointment appointment = appointmentDAO.findById(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found");
            return false;
        }
        
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentDAO.update(appointment);
        System.out.println("Appointment confirmed successfully");
        return true;
    }
    
    
    public boolean completeAppointment(String appointmentId, String notes) {
        Appointment appointment = appointmentDAO.findById(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found");
            return false;
        }
        
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setNotes(notes);
        appointmentDAO.update(appointment);
        System.out.println("Appointment marked as completed");
        return true;
    }
    
   
    public Appointment getAppointmentById(String appointmentId) {
        return appointmentDAO.findById(appointmentId);
    }
    
    
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.findAll();
    }
    
   
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentDAO.findByStatus(status);
    }
    
    
    public List<TimeSlot> getAvailableSlots(String doctorId, LocalDate date) {
        Doctor doctor = doctorDAO.findById(doctorId);
        if (doctor == null) {
            return null;
        }
        
        List<TimeSlot> slots = doctor.getAvailableSlots();
        List<Appointment> bookedAppointments = appointmentDAO.findByDoctorAndDate(doctorId, date);
        
        
        for (TimeSlot slot : slots) {
            slot.setAvailable(true); 
            for (Appointment apt : bookedAppointments) {
                if (apt.getStatus() != AppointmentStatus.CANCELLED &&
                    timesOverlap(slot.getStartTime(), slot.getEndTime(),
                               apt.getStartTime(), apt.getEndTime())) {
                    slot.setAvailable(false);
                    break;
                }
            }
        }
        
        return slots;
    }
}
