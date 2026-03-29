package com.quickdoctor.service;

import com.quickdoctor.dao.DoctorDAO;
import com.quickdoctor.dao.DoctorDAOImpl;
import com.quickdoctor.dao.AppointmentDAO;
import com.quickdoctor.dao.AppointmentDAOImpl;
import com.quickdoctor.model.Doctor;
import com.quickdoctor.model.Appointment;
import com.quickdoctor.model.TimeSlot;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class DoctorService {
    
    private final DoctorDAO doctorDAO;
    private final AppointmentDAO appointmentDAO;
    
    public DoctorService() {
        this.doctorDAO = new DoctorDAOImpl();
        this.appointmentDAO = new AppointmentDAOImpl();
    }
    
    
    public boolean registerDoctor(String userId, String firstName, String lastName,
                                  String specialization, String qualification,
                                  int yearsOfExperience, double consultationFee) {
        String doctorId = "DOC-" + UUID.randomUUID().toString().substring(0, 8);
        Doctor doctor = new Doctor(doctorId, userId, firstName, lastName,
                                  specialization, qualification, yearsOfExperience,
                                  consultationFee);
        doctorDAO.save(doctor);
        System.out.println("Doctor registered successfully! Doctor ID: " + doctorId);
        return true;
    }
    
    
    public Doctor getDoctorByUserId(String userId) {
        return doctorDAO.findByUserId(userId);
    }
    
   
    public Doctor getDoctorById(String doctorId) {
        return doctorDAO.findById(doctorId);
    }
    
   
    public boolean updateDoctor(Doctor doctor) {
        doctorDAO.update(doctor);
        System.out.println("Doctor profile updated successfully");
        return true;
    }
    
   
    public List<Appointment> getDoctorAppointments(String doctorId) {
        return appointmentDAO.findByDoctorId(doctorId);
    }
    
   
    public List<Appointment> getDoctorAppointmentsByDate(String doctorId, LocalDate date) {
        return appointmentDAO.findByDoctorAndDate(doctorId, date);
    }
    
   
    public List<Doctor> getAllDoctors() {
        return doctorDAO.findAll();
    }
    
    
    public List<Doctor> searchBySpecialization(String specialization) {
        return doctorDAO.searchBySpecialization(specialization);
    }
    
    
    public List<Doctor> searchByName(String name) {
        return doctorDAO.searchByName(name);
    }
    
    
    public boolean updateAvailableSlots(String doctorId, List<TimeSlot> slots) {
        Doctor doctor = doctorDAO.findById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found");
            return false;
        }
        doctor.setAvailableSlots(slots);
        doctorDAO.update(doctor);
        System.out.println("Available slots updated successfully");
        return true;
    }
}
