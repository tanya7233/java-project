package com.quickdoctor.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;


public class TimeSlot implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean available;
    
    public TimeSlot() {
    }
    
    public TimeSlot(LocalTime startTime, LocalTime endTime, boolean available) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(startTime, timeSlot.startTime) &&
               Objects.equals(endTime, timeSlot.endTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }
    
    @Override
    public String toString() {
        return startTime + " - " + endTime + (available ? " (Available)" : " (Booked)");
    }
}
