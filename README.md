# QuickDoctor Appointment System

A comprehensive doctor appointment management system built in **pure Java** without using Spring Boot or any external frameworks. The system features patient management, doctor scheduling, and appointment booking capabilities with a console-based interface.

## Features

### For Patients
- **User Registration & Login**: Secure authentication with password hashing
- **Doctor Search**: Search doctors by name or specialization
- **Appointment Booking**: Book appointments with available time slots
- **Appointment Management**: View and cancel appointments
- **Profile Management**: Update personal information

### For Doctors
- **User Registration & Login**: Secure authentication
- **Appointment Management**: View, confirm, complete, and cancel appointments
- **Schedule Management**: View appointments by date
- **Profile Management**: Update consultation fees and other details

### For Administrators
- **System Overview**: View all doctors, patients, and appointments
- **Reporting**: Generate comprehensive system reports
- **User Management**: View detailed information about doctors and patients

## Technology Stack

- **Language**: Java (JDK 11 or higher)
- **Architecture**: Layered architecture (Model-DAO-Service-UI)
- **Persistence**: File-based storage using Java serialization
- **UI**: Console-based interface
- **Security**: SHA-256 password hashing

## Project Structure

```
quickdoctor/
├── src/
│   └── com/
│       └── quickdoctor/
│           ├── model/              # Domain entities
│           │   ├── User.java
│           │   ├── Patient.java
│           │   ├── Doctor.java
│           │   ├── Appointment.java
│           │   ├── TimeSlot.java
│           │   ├── UserRole.java
│           │   └── AppointmentStatus.java
│           ├── dao/                # Data Access Objects
│           │   ├── UserDAO.java
│           │   ├── UserDAOImpl.java
│           │   ├── PatientDAO.java
│           │   ├── PatientDAOImpl.java
│           │   ├── DoctorDAO.java
│           │   ├── DoctorDAOImpl.java
│           │   ├── AppointmentDAO.java
│           │   └── AppointmentDAOImpl.java
│           ├── service/            # Business logic
│           │   ├── AuthenticationService.java
│           │   ├── PatientService.java
│           │   ├── DoctorService.java
│           │   ├── AppointmentService.java
│           │   └── AdminService.java
│           ├── ui/                 # Console UI
│           │   ├── ConsoleUI.java
│           │   ├── LoginUI.java
│           │   ├── PatientUI.java
│           │   ├── DoctorUI.java
│           │   └── AdminUI.java
│           ├── util/               # Utility classes
│           │   ├── FileUtil.java
│           │   ├── PasswordUtil.java
│           │   ├── DateTimeUtil.java
│           │   └── ValidationUtil.java
│           └── Main.java           # Application entry point
├── data/                           # Data storage files (auto-created)
└── README.md
```

## Setup Instructions

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Command line terminal

### Compilation

1. Navigate to the project directory:
```bash
cd quickdoctor
```

2. Compile all Java files:
```bash
javac -d bin src/com/quickdoctor/**/*.java src/com/quickdoctor/*.java
```

### Running the Application

```bash
java -cp bin com.quickdoctor.Main
```

## Usage Guide

### First Time Setup

1. **Create an Admin Account** (Optional):
   - Register as a patient or doctor first
   - Manually modify the user role in the data file if admin access is needed

2. **Register as a Patient**:
   - Choose option 2 from the login menu
   - Provide username, password, email, and phone number
   - Enter personal details (name, DOB, gender, address)

3. **Register as a Doctor**:
   - Choose option 3 from the login menu
   - Provide username, password, email, and phone number
   - Enter professional details (specialization, qualification, experience, fee)

### Patient Workflow

1. **Login** with your credentials
2. **Search for Doctors** by specialization or name
3. **Book an Appointment**:
   - Enter doctor ID
   - Select appointment date
   - Choose from available time slots
   - Provide reason for visit
4. **View Your Appointments** to track status
5. **Cancel Appointments** if needed

### Doctor Workflow

1. **Login** with your credentials
2. **View Appointments**:
   - All appointments
   - Today's appointments
   - Appointments by specific date
3. **Manage Appointments**:
   - Confirm pending appointments
   - Complete appointments with notes
   - Cancel appointments if necessary
4. **Update Profile** (consultation fee, etc.)

### Admin Workflow

1. **Login** with admin credentials
2. **View System Data**:
   - All doctors
   - All patients
   - All appointments
3. **Generate Reports** for system statistics
4. **View Detailed Information** for specific doctors or patients

## Data Storage

The system uses file-based persistence with Java serialization:
- `data/users.dat` - User accounts
- `data/patients.dat` - Patient profiles
- `data/doctors.dat` - Doctor profiles
- `data/appointments.dat` - Appointment records

**Note**: The `data` directory is automatically created when the application runs.

## Security Features

- **Password Hashing**: All passwords are hashed using SHA-256
- **Input Validation**: Email, phone number, and other inputs are validated
- **Role-Based Access**: Different interfaces for patients, doctors, and admins

## Default Time Slots

Doctors have default time slots from **9:00 AM to 5:00 PM** with **30-minute intervals**:
- 09:00 - 09:30
- 09:30 - 10:00
- ... and so on until 17:00

## Appointment Status Flow

1. **PENDING**: Appointment is booked but not confirmed
2. **CONFIRMED**: Doctor has confirmed the appointment
3. **COMPLETED**: Appointment has been completed
4. **CANCELLED**: Appointment has been cancelled

## Date and Time Formats

- **Date Format**: DD-MM-YYYY (e.g., 25-12-2024)
- **Time Format**: HH:mm (e.g., 14:30)

## Troubleshooting

### Common Issues

1. **Compilation Errors**:
   - Ensure you're using JDK 11 or higher
   - Check that all files are in the correct directory structure

2. **Data Not Persisting**:
   - Ensure the application has write permissions in the directory
   - Check that the `data` folder is created

3. **Invalid Date Format**:
   - Use DD-MM-YYYY format (e.g., 24-11-2024)
   - Ensure dates are valid

## Future Enhancements

Potential improvements for the system:
- Database integration (MySQL/PostgreSQL with JDBC)
- GUI interface using JavaFX or Swing
- Email notifications for appointments
- SMS reminders
- Payment integration
- Medical records management
- Prescription management
- Multi-language support

## Contributing

This is an educational project demonstrating Java fundamentals without external frameworks. Feel free to extend and modify as needed.

## License

This project is open-source and available for educational purposes.

## Contact

For questions or support, please refer to the project documentation.

---

**Built with ❤️ using Pure Java**
