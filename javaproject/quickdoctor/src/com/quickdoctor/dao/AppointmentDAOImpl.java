package com.quickdoctor.dao;
import com.quickdoctor.model.Appointment;
import com.quickdoctor.model.AppointmentStatus;
import com.quickdoctor.util.FileUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class AppointmentDAOImpl implements AppointmentDAO {
    private static final String DATA_FILE = "data/appointments.dat";
    private List<Appointment> appointments;
    public AppointmentDAOImpl() {
        loadAppointments();
    }
    private void loadAppointments() {
        try {
            appointments = FileUtil.loadListFromFile(DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            appointments = new ArrayList<>();
        }
    }
    
    private void saveAppointments() {
        try {
            FileUtil.saveListToFile(appointments, DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error saving appointments: " + e.getMessage());
        }
    }
    
    @Override
    public void save(Appointment appointment) {
        appointments.add(appointment);
        saveAppointments();
    }
    
    @Override
    public Appointment findById(String appointmentId) {
        return appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(appointments);
    }
    
    @Override
    public void update(Appointment appointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentId().equals(appointment.getAppointmentId())) {
                appointments.set(i, appointment);
                saveAppointments();
                return;
            }
        }
    }
    
    @Override
    public void delete(String appointmentId) {
        appointments = appointments.stream()
                .filter(a -> !a.getAppointmentId().equals(appointmentId))
                .collect(Collectors.toList());
        saveAppointments();
    }
    
    @Override
    public List<Appointment> findByPatientId(String patientId) {
        return appointments.stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Appointment> findByDoctorId(String doctorId) {
        return appointments.stream()
                .filter(a -> a.getDoctorId().equals(doctorId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Appointment> findByDate(LocalDate date) {
        return appointments.stream()
                .filter(a -> a.getAppointmentDate().equals(date))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Appointment> findByStatus(AppointmentStatus status) {
        return appointments.stream()
                .filter(a -> a.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Appointment> findByDoctorAndDate(String doctorId, LocalDate date) {
        return appointments.stream()
                .filter(a -> a.getDoctorId().equals(doctorId) && 
                           a.getAppointmentDate().equals(date))
                .collect(Collectors.toList());
    }
}
