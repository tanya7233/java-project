package com.quickdoctor.ui;

import com.quickdoctor.model.*;
import com.quickdoctor.service.*;

import java.util.List;
import java.util.Scanner;

public class AdminUI {
    
    private final Scanner scanner;
    private final AdminService adminService;
    private final AuthenticationService authService;
    
    public AdminUI(Scanner scanner, AdminService adminService,
                  AuthenticationService authService) {
        this.scanner = scanner;
        this.adminService = adminService;
        this.authService = authService;
    }
    

    public void displayAdminMenu() {
        while (true) {
            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("Welcome, Administrator");
            System.out.println("1. View All Doctors");
            System.out.println("2. View All Patients");
            System.out.println("3. View All Appointments");
            System.out.println("4. Generate System Report");
            System.out.println("5. View Doctor Details");
            System.out.println("6. View Patient Details");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    viewAllDoctors();
                    break;
                case 2:
                    viewAllPatients();
                    break;
                case 3:
                    viewAllAppointments();
                    break;
                case 4:
                    adminService.generateSystemReport();
                    break;
                case 5:
                    viewDoctorDetails();
                    break;
                case 6:
                    viewPatientDetails();
                    break;
                case 7:
                    authService.logout();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    
    private void viewAllDoctors() {
        List<Doctor> doctors = adminService.getAllDoctors();
        
        if (doctors.isEmpty()) {
            System.out.println("\nNo doctors found");
            return;
        }
        
        System.out.println("\n========== ALL DOCTORS ==========");
        for (Doctor doctor : doctors) {
            System.out.println("\nDoctor ID: " + doctor.getDoctorId());
            System.out.println("Name: " + doctor.getFullName());
            System.out.println("Specialization: " + doctor.getSpecialization());
            System.out.println("Qualification: " + doctor.getQualification());
            System.out.println("Experience: " + doctor.getYearsOfExperience() + " years");
            System.out.println("Fee: $" + doctor.getConsultationFee());
            System.out.println("-----------------------------------");
        }
    }
    
   
    private void viewAllPatients() {
        List<Patient> patients = adminService.getAllPatients();
        
        if (patients.isEmpty()) {
            System.out.println("\nNo patients found");
            return;
        }
        
        System.out.println("\n========== ALL PATIENTS ==========");
        for (Patient patient : patients) {
            System.out.println("\nPatient ID: " + patient.getPatientId());
            System.out.println("Name: " + patient.getFullName());
            System.out.println("DOB: " + patient.getDateOfBirth());
            System.out.println("Gender: " + patient.getGender());
            System.out.println("Registration Date: " + patient.getRegistrationDate());
            System.out.println("-----------------------------------");
        }
    }
    
  
    private void viewAllAppointments() {
        List<Appointment> appointments = adminService.getAllAppointments();
        
        if (appointments.isEmpty()) {
            System.out.println("\nNo appointments found");
            return;
        }
        
        System.out.println("\n========== ALL APPOINTMENTS ==========");
        for (Appointment apt : appointments) {
            System.out.println("\nAppointment ID: " + apt.getAppointmentId());
            System.out.println("Patient ID: " + apt.getPatientId());
            System.out.println("Doctor ID: " + apt.getDoctorId());
            System.out.println("Date: " + apt.getAppointmentDate());
            System.out.println("Time: " + apt.getStartTime() + " - " + apt.getEndTime());
            System.out.println("Status: " + apt.getStatus().getDisplayName());
            System.out.println("-----------------------------------");
        }
    }
    
   
    private void viewDoctorDetails() {
        System.out.print("\nEnter Doctor ID: ");
        String doctorId = scanner.nextLine();
        adminService.viewDoctorDetails(doctorId);
    }
    
    
    private void viewPatientDetails() {
        System.out.print("\nEnter Patient ID: ");
        String patientId = scanner.nextLine();
        adminService.viewPatientDetails(patientId);
    }
    
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
