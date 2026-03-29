package com.quickdoctor.dao;

import com.quickdoctor.model.Doctor;
import com.quickdoctor.util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class DoctorDAOImpl implements DoctorDAO {
    
    private static final String DATA_FILE = "data/doctors.dat";
    private List<Doctor> doctors;
    
    public DoctorDAOImpl() {
        loadDoctors();
    }
    
    private void loadDoctors() {
        try {
            doctors = FileUtil.loadListFromFile(DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            doctors = new ArrayList<>();
        }
    }
    
    private void saveDoctors() {
        try {
            FileUtil.saveListToFile(doctors, DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error saving doctors: " + e.getMessage());
        }
    }
    
    @Override
    public void save(Doctor doctor) {
        doctors.add(doctor);
        saveDoctors();
    }
    
    @Override
    public Doctor findById(String doctorId) {
        return doctors.stream()
                .filter(d -> d.getDoctorId().equals(doctorId))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public Doctor findByUserId(String userId) {
        return doctors.stream()
                .filter(d -> d.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Doctor> findAll() {
        return new ArrayList<>(doctors);
    }
    
    @Override
    public void update(Doctor doctor) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getDoctorId().equals(doctor.getDoctorId())) {
                doctors.set(i, doctor);
                saveDoctors();
                return;
            }
        }
    }
    
    @Override
    public void delete(String doctorId) {
        doctors = doctors.stream()
                .filter(d -> !d.getDoctorId().equals(doctorId))
                .collect(Collectors.toList());
        saveDoctors();
    }
    
    @Override
    public List<Doctor> searchBySpecialization(String specialization) {
        String lowerSpec = specialization.toLowerCase();
        return doctors.stream()
                .filter(d -> d.getSpecialization().toLowerCase().contains(lowerSpec))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Doctor> searchByName(String name) {
        String lowerName = name.toLowerCase();
        return doctors.stream()
                .filter(d -> d.getFullName().toLowerCase().contains(lowerName))
                .collect(Collectors.toList());
    }
}
