package com.quickdoctor.dao;
import com.quickdoctor.model.Patient;
import java.util.List;
public interface PatientDAO {
    void save(Patient patient);
    Patient findById(String patientId);
    Patient findByUserId(String userId);
    List<Patient> findAll();
    void update(Patient patient);
    void delete(String patientId);
    List<Patient> searchByName(String name);
}
