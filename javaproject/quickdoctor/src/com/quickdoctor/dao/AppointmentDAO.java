package com.quickdoctor.dao;
import com.quickdoctor.model.Appointment;
import com.quickdoctor.model.AppointmentStatus;
import java.time.LocalDate;
import java.util.List;
public interface AppointmentDAO {
    void save(Appointment appointment);
    Appointment findById(String appointmentId);
    List<Appointment> findAll();
    void update(Appointment appointment);
    void delete(String appointmentId);
    List<Appointment> findByPatientId(String patientId);
    List<Appointment> findByDoctorId(String doctorId);
    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByStatus(AppointmentStatus status);
    List<Appointment> findByDoctorAndDate(String doctorId, LocalDate date);
}
