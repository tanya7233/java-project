package com.quickdoctor.service;

import com.quickdoctor.dao.UserDAO;
import com.quickdoctor.dao.UserDAOImpl;
import com.quickdoctor.model.Appointment;
import com.quickdoctor.model.AppointmentStatus;
import com.quickdoctor.model.Doctor;
import com.quickdoctor.model.Patient;

import java.util.List;


public class AdminService {
    
    private final UserDAO userDAO;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    
    public AdminService() {
        this.userDAO = new UserDAOImpl();
        this.doctorService = new DoctorService();
        this.patientService = new PatientService();
        this.appointmentService = new AppointmentService();
    }
    
   
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
    
 
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }
   
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
    public void generateSystemReport() {
        List<Doctor> doctors = getAllDoctors();
        List<Patient> patients = getAllPatients();
        List<Appointment> appointments = getAllAppointments();
        
        long pendingCount = appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.PENDING)
                .count();
        long confirmedCount = appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.CONFIRMED)
                .count();
        long completedCount = appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.COMPLETED)
                .count();
        long cancelledCount = appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.CANCELLED)
                .count();
        
        System.out.println("\n========== SYSTEM REPORT ==========");
        System.out.println("Total Doctors: " + doctors.size());
        System.out.println("Total Patients: " + patients.size());
        System.out.println("Total Appointments: " + appointments.size());
        System.out.println("\nAppointment Status Breakdown:");
        System.out.println("  Pending: " + pendingCount);
        System.out.println("  Confirmed: " + confirmedCount);
        System.out.println("  Completed: " + completedCount);
        System.out.println("  Cancelled: " + cancelledCount);
        System.out.println("===================================\n");
    }
    
    
    public void viewDoctorDetails(String doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor != null) {
            System.out.println(doctor);
        } else {
            System.out.println("Doctor not found");
        }
    }
    
   
    public void viewPatientDetails(String patientId) {
        Patient patient = patientService.getPatientById(patientId);
        if (patient != null) {
            System.out.println(patient);
        } else {
            System.out.println("Patient not found");
        }
    }
}
