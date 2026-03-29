package com.quickdoctor.ui;

import com.quickdoctor.model.*;
import com.quickdoctor.service.*;
import com.quickdoctor.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


public class PatientUI {
    
    private final Scanner scanner;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final AuthenticationService authService;
    
    public PatientUI(Scanner scanner, PatientService patientService,
                    DoctorService doctorService, AppointmentService appointmentService,
                    AuthenticationService authService) {
        this.scanner = scanner;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.authService = authService;
    }
    
    
    public void displayPatientMenu() {
        Patient patient = patientService.getPatientByUserId(
            authService.getCurrentUser().getUserId());
        
        if (patient == null) {
            System.out.println("Patient profile not found!");
            return;
        }
        
        while (true) {
            System.out.println("\n========== PATIENT MENU ==========");
            System.out.println("Welcome, " + patient.getFullName());
            System.out.println("1. Search Doctors");
            System.out.println("2. Book Appointment");
            System.out.println("3. View My Appointments");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. Update Profile");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    searchDoctors();
                    break;
                case 2:
                    bookAppointment(patient.getPatientId());
                    break;
                case 3:
                    viewMyAppointments(patient.getPatientId());
                    break;
                case 4:
                    cancelAppointment();
                    break;
                case 5:
                    updateProfile(patient);
                    break;
                case 6:
                    authService.logout();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
   
    private void searchDoctors() {
        System.out.println("\n========== SEARCH DOCTORS ==========");
        System.out.println("1. View All Doctors");
        System.out.println("2. Search by Specialization");
        System.out.println("3. Search by Name");
        System.out.print("Choose an option: ");
        
        int choice = getIntInput();
        List<Doctor> doctors = null;
        
        switch (choice) {
            case 1:
                doctors = doctorService.getAllDoctors();
                break;
            case 2:
                System.out.print("Enter specialization: ");
                String specialization = scanner.nextLine();
                doctors = doctorService.searchBySpecialization(specialization);
                break;
            case 3:
                System.out.print("Enter doctor name: ");
                String name = scanner.nextLine();
                doctors = doctorService.searchByName(name);
                break;
            default:
                System.out.println("Invalid option");
                return;
        }
        
        if (doctors == null || doctors.isEmpty()) {
            System.out.println("No doctors found");
            return;
        }
        
        System.out.println("\n========== DOCTORS LIST ==========");
        for (Doctor doctor : doctors) {
            System.out.println("\nDoctor ID: " + doctor.getDoctorId());
            System.out.println("Name: " + doctor.getFullName());
            System.out.println("Specialization: " + doctor.getSpecialization());
            System.out.println("Qualification: " + doctor.getQualification());
            System.out.println("Experience: " + doctor.getYearsOfExperience() + " years");
            System.out.println("Consultation Fee: $" + doctor.getConsultationFee());
            System.out.println("-----------------------------------");
        }
    }
    
   
    private void bookAppointment(String patientId) {
        System.out.println("\n========== BOOK APPOINTMENT ==========");
        System.out.print("Enter Doctor ID: ");
        String doctorId = scanner.nextLine();
        
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found");
            return;
        }
        
        System.out.print("Enter appointment date (DD-MM-YYYY): ");
        String dateStr = scanner.nextLine();
        
        try {
            LocalDate date = DateTimeUtil.parseDate(dateStr);
            
            if (!DateTimeUtil.isTodayOrFuture(date)) {
                System.out.println("Cannot book appointment for past dates");
                return;
            }
            
            
            List<TimeSlot> slots = appointmentService.getAvailableSlots(doctorId, date);
            if (slots == null || slots.isEmpty()) {
                System.out.println("No slots available");
                return;
            }
            
            System.out.println("\nAvailable Time Slots:");
            int index = 1;
            for (TimeSlot slot : slots) {
                if (slot.isAvailable()) {
                    System.out.println(index + ". " + slot);
                    index++;
                }
            }
            
            System.out.print("\nSelect slot number: ");
            int slotChoice = getIntInput() - 1;
            
            int availableIndex = 0;
            TimeSlot selectedSlot = null;
            for (TimeSlot slot : slots) {
                if (slot.isAvailable()) {
                    if (availableIndex == slotChoice) {
                        selectedSlot = slot;
                        break;
                    }
                    availableIndex++;
                }
            }
            
            if (selectedSlot == null) {
                System.out.println("Invalid slot selection");
                return;
            }
            
            System.out.print("Reason for visit: ");
            String reason = scanner.nextLine();
            
            appointmentService.bookAppointment(patientId, doctorId, date,
                selectedSlot.getStartTime(), selectedSlot.getEndTime(), reason);
            
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format");
        }
    }
    
   
    private void viewMyAppointments(String patientId) {
        List<Appointment> appointments = patientService.getPatientAppointments(patientId);
        
        if (appointments.isEmpty()) {
            System.out.println("\nNo appointments found");
            return;
        }
        
        System.out.println("\n========== MY APPOINTMENTS ==========");
        for (Appointment apt : appointments) {
            Doctor doctor = doctorService.getDoctorById(apt.getDoctorId());
            System.out.println("\nAppointment ID: " + apt.getAppointmentId());
            System.out.println("Doctor: " + (doctor != null ? doctor.getFullName() : "Unknown"));
            System.out.println("Date: " + DateTimeUtil.formatDate(apt.getAppointmentDate()));
            System.out.println("Time: " + DateTimeUtil.formatTime(apt.getStartTime()) + 
                             " - " + DateTimeUtil.formatTime(apt.getEndTime()));
            System.out.println("Status: " + apt.getStatus().getDisplayName());
            System.out.println("Reason: " + apt.getReasonForVisit());
            if (!apt.getNotes().isEmpty()) {
                System.out.println("Notes: " + apt.getNotes());
            }
            System.out.println("-----------------------------------");
        }
    }
    
   
    private void cancelAppointment() {
        System.out.print("\nEnter Appointment ID to cancel: ");
        String appointmentId = scanner.nextLine();
        appointmentService.cancelAppointment(appointmentId);
    }
    
    
    private void updateProfile(Patient patient) {
        System.out.println("\n========== UPDATE PROFILE ==========");
        System.out.println("Leave blank to keep current value");
        
        System.out.print("Address [" + patient.getAddress() + "]: ");
        String address = scanner.nextLine();
        if (!address.trim().isEmpty()) {
            patient.setAddress(address);
        }
        
        System.out.print("Medical History [" + patient.getMedicalHistory() + "]: ");
        String medicalHistory = scanner.nextLine();
        if (!medicalHistory.trim().isEmpty()) {
            patient.setMedicalHistory(medicalHistory);
        }
        
        patientService.updatePatient(patient);
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
