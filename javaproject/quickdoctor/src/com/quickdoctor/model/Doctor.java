package com.quickdoctor.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String doctorId;
    private String userId; 
    private String firstName;
    private String lastName;
    private String specialization;
    private String qualification;
    private int yearsOfExperience;
    private double consultationFee;
    private List<TimeSlot> availableSlots;
    
    public Doctor() {
        this.availableSlots = new ArrayList<>();
    }
    
    public Doctor(String doctorId, String userId, String firstName, String lastName,
                  String specialization, String qualification, int yearsOfExperience, 
                  double consultationFee) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.qualification = qualification;
        this.yearsOfExperience = yearsOfExperience;
        this.consultationFee = consultationFee;
        this.availableSlots = new ArrayList<>();
        initializeDefaultSlots();
    }
    
    
    private void initializeDefaultSlots() {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        
        while (startTime.isBefore(endTime)) {
            LocalTime slotEnd = startTime.plusMinutes(30);
            availableSlots.add(new TimeSlot(startTime, slotEnd, true));
            startTime = slotEnd;
        }
    }
    

    public String getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFullName() {
        return "Dr. " + firstName + " " + lastName;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getQualification() {
        return qualification;
    }
    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
    
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    public List<TimeSlot> getAvailableSlots() {
        return availableSlots;
    }
    
    public void setAvailableSlots(List<TimeSlot> availableSlots) {
        this.availableSlots = availableSlots;
    }
    
    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId='" + doctorId + '\'' +
                ", name='" + getFullName() + '\'' +
                ", specialization='" + specialization + '\'' +
                ", experience=" + yearsOfExperience + " years" +
                ", fee=$" + consultationFee +
                '}';
    }
}
