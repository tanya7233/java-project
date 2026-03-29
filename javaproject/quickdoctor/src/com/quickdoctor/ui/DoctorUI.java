package com.quickdoctor.ui;

import com.quickdoctor.model.*;
import com.quickdoctor.service.*;
import com.quickdoctor.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


public class DoctorUI {
    
    private final Scanner scanner;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final AuthenticationService authService;
    
    public DoctorUI(Scanner scanner, DoctorService doctorService,
                   PatientService patientService, AppointmentService appointmentService,
                   AuthenticationService authService) {
        this.scanner = scanner;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.authService = authService;
    }
    
  
    public void displayDoctorMenu() {
        Doctor doctor = doctorService.getDoctorByUserId(
            authService.getCurrentUser().getUserId());
        
        if (doctor == null) {
            System.out.println("Doctor profile not found!");
            return;
        }
        
        while (true) {
            System.out.println("\n========== DOCTOR MENU ==========");
            System.out.println("Welcome, " + doctor.getFullName());
            System.out.println("1. View All Appointments");
            System.out.println("2. View Today's Appointments");
            System.out.println("3. View Appointments by Date");
            System.out.println("4. Confirm Appointment");
            System.out.println("5. Complete Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. Update Profile");
            System.out.println("8. Logout");
            System.out.print("Choose an option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    viewAllAppointments(doctor.getDoctorId());
                    break;
                case 2:
                    viewTodaysAppointments(doctor.getDoctorId());
                    break;
                case 3:
                    viewAppointmentsByDate(doctor.getDoctorId());
                    break;
                case 4:
                    confirmAppointment();
                    break;
                case 5:
                    completeAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 7:
                    updateProfile(doctor);
                    break;
                case 8:
                    authService.logout();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    
    private void viewAllAppointments(String doctorId) {
        List<Appointment> appointments = doctorService.getDoctorAppointments(doctorId);
        displayAppointments(appointments);
    }
    
   
    private void viewTodaysAppointments(String doctorId) {
        List<Appointment> appointments = doctorService.getDoctorAppointmentsByDate(
            doctorId, LocalDate.now());
        displayAppointments(appointments);
    }
    
    
    private void viewAppointmentsByDate(String doctorId) {
        System.out.print("\nEnter date (DD-MM-YYYY): ");
        String dateStr = scanner.nextLine();
        
        try {
            LocalDate date = DateTimeUtil.parseDate(dateStr);
            List<Appointment> appointments = doctorService.getDoctorAppointmentsByDate(
                doctorId, date);
            displayAppointments(appointments);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format");
        }
    }
    
    
    private void displayAppointments(List<Appointment> appointments) {
        if (appointments.isEmpty()) {
            System.out.println("\nNo appointments found");
            return;
        }
        
        System.out.println("\n========== APPOINTMENTS ==========");
        for (Appointment apt : appointments) {
            Patient patient = patientService.getPatientById(apt.getPatientId());
            System.out.println("\nAppointment ID: " + apt.getAppointmentId());
            System.out.println("Patient: " + (patient != null ? patient.getFullName() : "Unknown"));
            System.out.println("Date: " + DateTimeUtil.formatDate(apt.getAppointmentDate()));
            System.out.println("Time: " + DateTimeUtil.formatTime(apt.getStartTime()) + 
                             " - " + DateTimeUtil.formatTime(apt.getEndTime()));
            System.out.println("Status: " + apt.getStatus().getDisplayName());
            System.out.println("Reason: " + apt.getReasonForVisit());
            if (patient != null && !patient.getMedicalHistory().isEmpty()) {
                System.out.println("Medical History: " + patient.getMedicalHistory());
            }
            if (!apt.getNotes().isEmpty()) {
                System.out.println("Notes: " + apt.getNotes());
            }
            System.out.println("-----------------------------------");
        }
    }
    
   
    private void confirmAppointment() {
        System.out.print("\nEnter Appointment ID to confirm: ");
        String appointmentId = scanner.nextLine();
        appointmentService.confirmAppointment(appointmentId);
    }
    
   
    private void completeAppointment() {
        System.out.print("\nEnter Appointment ID to complete: ");
        String appointmentId = scanner.nextLine();
        System.out.print("Enter notes (optional): ");
        String notes = scanner.nextLine();
        appointmentService.completeAppointment(appointmentId, notes);
    }
    
    
    private void cancelAppointment() {
        System.out.print("\nEnter Appointment ID to cancel: ");
        String appointmentId = scanner.nextLine();
        appointmentService.cancelAppointment(appointmentId);
    }
    
  
    private void updateProfile(Doctor doctor) {
        System.out.println("\n========== UPDATE PROFILE ==========");
        System.out.println("Leave blank to keep current value");
        
        System.out.print("Consultation Fee [" + doctor.getConsultationFee() + "]: ");
        String feeStr = scanner.nextLine();
        if (!feeStr.trim().isEmpty()) {
            try {
                double fee = Double.parseDouble(feeStr);
                doctor.setConsultationFee(fee);
            } catch (NumberFormatException e) {
                System.out.println("Invalid fee amount");
            }
        }
        
        doctorService.updateDoctor(doctor);
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
