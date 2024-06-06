package me.dio.service.dto;

import me.dio.domain.model.Doctor;
import me.dio.domain.model.Patient;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDTO {

    private LocalDate date;

    private LocalTime time;

    private Patient patient;

    private Doctor doctor;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
