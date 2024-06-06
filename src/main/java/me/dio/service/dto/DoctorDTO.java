package me.dio.service.dto;

import me.dio.domain.model.Appointment;
import me.dio.domain.model.Specialty;
import me.dio.domain.model.TimeSlot;

import java.util.List;

public class DoctorDTO {

    private String name;

    private List<Specialty> specialtyList;

    private List<TimeSlot> availableSlotsList;

    private List<Appointment> bookedAppointmentList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Specialty> getSpecialtyList() {
        return specialtyList;
    }

    public void setSpecialtyList(List<Specialty> specialtyList) {
        this.specialtyList = specialtyList;
    }

    public List<TimeSlot> getAvailableSlotsList() {
        return availableSlotsList;
    }

    public void setAvailableSlotsList(List<TimeSlot> availableSlotsList) {
        this.availableSlotsList = availableSlotsList;
    }

    public List<Appointment> getBookedAppointmentList() {
        return bookedAppointmentList;
    }

    public void setBookedAppointmentList(List<Appointment> bookedAppointmentList) {
        this.bookedAppointmentList = bookedAppointmentList;
    }
}
