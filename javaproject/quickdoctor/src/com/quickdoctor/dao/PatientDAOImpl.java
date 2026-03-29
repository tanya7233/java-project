package com.quickdoctor.dao;
import com.quickdoctor.model.Patient;
import com.quickdoctor.util.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class PatientDAOImpl implements PatientDAO {
    private static final String DATA_FILE = "data/patients.dat";
    private List<Patient> patients;
    
    public PatientDAOImpl() {
        loadPatients();
    }
    
    private void loadPatients() {
        try {
            patients = FileUtil.loadListFromFile(DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            patients = new ArrayList<>();
        }
    }
    
    private void savePatients() {
        try {
            FileUtil.saveListToFile(patients, DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error saving patients: " + e.getMessage());
        }
    }
    
    @Override
    public void save(Patient patient) {
        patients.add(patient);
        savePatients();
    }
    
    @Override
    public Patient findById(String patientId) {
        return patients.stream()
                .filter(p -> p.getPatientId().equals(patientId))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public Patient findByUserId(String userId) {
        return patients.stream()
                .filter(p -> p.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(patients);
    }
    
    @Override
    public void update(Patient patient) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientId().equals(patient.getPatientId())) {
                patients.set(i, patient);
                savePatients();
                return;
            }
        }
    }
    
    @Override
    public void delete(String patientId) {
        patients = patients.stream()
                .filter(p -> !p.getPatientId().equals(patientId))
                .collect(Collectors.toList());
        savePatients();
    }
    
    @Override
    public List<Patient> searchByName(String name) {
        String lowerName = name.toLowerCase();
        return patients.stream()
                .filter(p -> p.getFullName().toLowerCase().contains(lowerName))
                .collect(Collectors.toList());
    }
}
