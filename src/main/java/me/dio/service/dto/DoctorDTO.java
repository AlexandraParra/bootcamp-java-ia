package me.dio.service.dto;

import java.util.List;

public class DoctorDTO {

    private Long id;

    private String name;

    private List<SpecialtyDTO> specialtyList;

    private List<TimeSlotDTO> availableSlotsList;

    private List<AppointmentDTO> bookedAppointmentList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpecialtyDTO> getSpecialtyList() {
        return specialtyList;
    }

    public void setSpecialtyList(List<SpecialtyDTO> specialtyList) {
        this.specialtyList = specialtyList;
    }

    public List<TimeSlotDTO> getAvailableSlotsList() {
        return availableSlotsList;
    }

    public void setAvailableSlotsList(List<TimeSlotDTO> availableSlotsList) {
        this.availableSlotsList = availableSlotsList;
    }

    public List<AppointmentDTO> getBookedAppointmentList() {
        return bookedAppointmentList;
    }

    public void setBookedAppointmentList(List<AppointmentDTO> bookedAppointmentList) {
        this.bookedAppointmentList = bookedAppointmentList;
    }
}
