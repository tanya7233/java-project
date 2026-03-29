package com.quickdoctor.ui;

import com.quickdoctor.model.UserRole;
import com.quickdoctor.service.*;

import java.util.Scanner;


public class ConsoleUI {
    
    private final Scanner scanner;
    private final AuthenticationService authService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final AdminService adminService;
    
    private final LoginUI loginUI;
    private final PatientUI patientUI;
    private final DoctorUI doctorUI;
    private final AdminUI adminUI;
    
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.authService = new AuthenticationService();
        this.patientService = new PatientService();
        this.doctorService = new DoctorService();
        this.appointmentService = new AppointmentService();
        this.adminService = new AdminService();
        
        this.loginUI = new LoginUI(scanner, authService, patientService, doctorService);
        this.patientUI = new PatientUI(scanner, patientService, doctorService, 
                                      appointmentService, authService);
        this.doctorUI = new DoctorUI(scanner, doctorService, patientService,
                                    appointmentService, authService);
        this.adminUI = new AdminUI(scanner, adminService, authService);
    }
    
  
    public void start() {
        displayWelcome();
        
        while (true) {
            if (!authService.isLoggedIn()) {
                boolean loggedIn = loginUI.displayLoginMenu();
                if (!loggedIn && authService.getCurrentUser() == null) {
                  
                    break;
                }
            }
            
            if (authService.isLoggedIn()) {
                UserRole role = authService.getCurrentUser().getRole();
                
                switch (role) {
                    case PATIENT:
                        patientUI.displayPatientMenu();
                        break;
                    case DOCTOR:
                        doctorUI.displayDoctorMenu();
                        break;
                    case ADMIN:
                        adminUI.displayAdminMenu();
                        break;
                }
            }
        }
        
        scanner.close();
    }
    
    
    private void displayWelcome() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║                                        ║");
        System.out.println("║        QUICKDOCTOR APPOINTMENT         ║");
        System.out.println("║           MANAGEMENT SYSTEM            ║");
        System.out.println("║                                        ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
    }
}
