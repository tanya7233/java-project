package com.quickdoctor.ui;

import com.quickdoctor.model.UserRole;
import com.quickdoctor.service.AuthenticationService;
import com.quickdoctor.service.PatientService;
import com.quickdoctor.service.DoctorService;
import com.quickdoctor.model.Patient;
import com.quickdoctor.model.Doctor;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class LoginUI {
    
    private final Scanner scanner;
    private final AuthenticationService authService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    
    public LoginUI(Scanner scanner, AuthenticationService authService,
                  PatientService patientService, DoctorService doctorService) {
        this.scanner = scanner;
        this.authService = authService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }
    
    
    public boolean displayLoginMenu() {
        System.out.println("\n========== QUICKDOCTOR LOGIN ==========");
        System.out.println("1. Login");
        System.out.println("2. Register as Patient");
        System.out.println("3. Register as Doctor");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
        
        int choice = getIntInput();
        
        switch (choice) {
            case 1:
                return login();
            case 2:
                return registerPatient();
            case 3:
                return registerDoctor();
            case 4:
                System.out.println("Thank you for using QuickDoctor!");
                return false;
            default:
                System.out.println("Invalid option. Please try again.");
                return displayLoginMenu();
        }
    }
    
  
    private boolean login() {
        System.out.print("\nUsername: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        return authService.login(username, password);
    }
    
   
    private boolean registerPatient() {
        System.out.println("\n========== PATIENT REGISTRATION ==========");
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password (min 6 characters): ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone Number (10 digits): ");
        String phone = scanner.nextLine();
        
        
        if (!authService.register(username, password, email, phone, UserRole.PATIENT)) {
            return false;
        }
        
        
        String userId = authService.getCurrentUser() != null ? 
                       authService.getCurrentUser().getUserId() : 
                       username;         
       
        authService.login(username, password);
        userId = authService.getCurrentUser().getUserId();
        authService.logout();
        
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Date of Birth (DD-MM-YYYY): ");
        String dobStr = scanner.nextLine();
        System.out.print("Gender (M/F/Other): ");
        String gender = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Medical History (optional): ");
        String medicalHistory = scanner.nextLine();
        
        try {
            LocalDate dob = LocalDate.parse(dobStr, 
                com.quickdoctor.util.DateTimeUtil.DATE_FORMATTER);
            patientService.registerPatient(userId, firstName, lastName, dob, 
                                          gender, address, medicalHistory);
            System.out.println("\nPatient registration complete! You can now login.");
            return false;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Registration failed.");
            return false;
        }
    }
    
   
    private boolean registerDoctor() {
        System.out.println("\n========== DOCTOR REGISTRATION ==========");
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password (min 6 characters): ");
        String password = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone Number (10 digits): ");
        String phone = scanner.nextLine();
        
        
        if (!authService.register(username, password, email, phone, UserRole.DOCTOR)) {
            return false;
        }
        
       
        authService.login(username, password);
        String userId = authService.getCurrentUser().getUserId();
        authService.logout();
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Specialization: ");
        String specialization = scanner.nextLine();
        System.out.print("Qualification: ");
        String qualification = scanner.nextLine();
        System.out.print("Years of Experience: ");
        int experience = getIntInput();
        System.out.print("Consultation Fee: ");
        double fee = getDoubleInput();
        
        doctorService.registerDoctor(userId, firstName, lastName, specialization,
                                    qualification, experience, fee);
        System.out.println("\nDoctor registration complete! You can now login.");
        return false;
    }
    
    private int getIntInput() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    private double getDoubleInput() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
