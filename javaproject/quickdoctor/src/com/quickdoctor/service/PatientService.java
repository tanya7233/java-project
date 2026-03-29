package com.quickdoctor.service;

import com.quickdoctor.dao.PatientDAO;
import com.quickdoctor.dao.PatientDAOImpl;
import com.quickdoctor.dao.AppointmentDAO;
import com.quickdoctor.dao.AppointmentDAOImpl;
import com.quickdoctor.model.Patient;
import com.quickdoctor.model.Appointment;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class PatientService {
    
    private final PatientDAO patientDAO;
    private final AppointmentDAO appointmentDAO;
    
    public PatientService() {
        this.patientDAO = new PatientDAOImpl();
        this.appointmentDAO = new AppointmentDAOImpl();
    }
    
    
    public boolean registerPatient(String userId, String firstName, String lastName,
                                   LocalDate dateOfBirth, String gender, String address,
                                   String medicalHistory) {
        String patientId = "PAT-" + UUID.randomUUID().toString().substring(0, 8);
        Patient patient = new Patient(patientId, userId, firstName, lastName,
                                     dateOfBirth, gender, address, medicalHistory);
        patientDAO.save(patient);
        System.out.println("Patient registered successfully! Patient ID: " + patientId);
        return true;
    }
    
    
    public Patient getPatientByUserId(String userId) {
        return patientDAO.findByUserId(userId);
    }
    
   
    public Patient getPatientById(String patientId) {
        return patientDAO.findById(patientId);
    }
    
    
    public boolean updatePatient(Patient patient) {
        patientDAO.update(patient);
        System.out.println("Patient profile updated successfully");
        return true;
    }
    
    
    public List<Appointment> getPatientAppointments(String patientId) {
        return appointmentDAO.findByPatientId(patientId);
    }
    
   
    public List<Patient> getAllPatients() {
        return patientDAO.findAll();
    }
    
    
    public List<Patient> searchPatients(String name) {
        return patientDAO.searchByName(name);
    }
}
