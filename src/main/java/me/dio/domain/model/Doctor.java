package me.dio.domain.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "tb_doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "tb_relation_doctor_specialty",
            joinColumns = @JoinColumn(
                    name = "doctor_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "specialty_id",
                    referencedColumnName = "id"
            )
    )
    private List<Specialty> specialtyList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "doctor")
    private List<TimeSlot> availableSlotsList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "doctor")
    private List<Appointment> bookedAppointmentList;

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
