package com.quickdoctor.dao;

import com.quickdoctor.model.Doctor;
import java.util.List;
public interface DoctorDAO {
    void save(Doctor doctor);
    Doctor findById(String doctorId);
    Doctor findByUserId(String userId);
    List<Doctor> findAll();
    void update(Doctor doctor);
    void delete(String doctorId);
    List<Doctor> searchBySpecialization(String specialization);
    List<Doctor> searchByName(String name);
}
