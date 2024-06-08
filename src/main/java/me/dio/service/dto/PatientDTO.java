package me.dio.service.dto;

import java.util.List;

public class PatientDTO {

    private Long id;

    private String name;

    private String cpf;

    private List<AppointmentDTO> appointmentList;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<AppointmentDTO> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<AppointmentDTO> appointmentList) {
        this.appointmentList = appointmentList;
    }
}
