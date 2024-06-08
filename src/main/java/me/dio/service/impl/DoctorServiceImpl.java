package me.dio.service.impl;

import me.dio.domain.model.Appointment;
import me.dio.domain.model.Doctor;
import me.dio.domain.model.Specialty;
import me.dio.domain.model.TimeSlot;
import me.dio.domain.repository.DoctorRepository;
import me.dio.service.DoctorService;
import me.dio.service.SpecialtyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final SpecialtyService specialtyService;

    public DoctorServiceImpl(DoctorRepository doctorRepository, SpecialtyService specialtyService){
        this.doctorRepository = doctorRepository;
        this.specialtyService = specialtyService;
    }

    @Transactional
    public Doctor create(Doctor doctorToCreate){

        for(TimeSlot timeSlot : doctorToCreate.getAvailableSlotsList()){
            timeSlot.setDoctor(doctorToCreate);
        }

        List<Specialty> specialtyList = doctorToCreate.getSpecialtyList()
                .stream()
                .map(specialty -> this.specialtyService.findById(specialty.getId()))
                .toList();
        doctorToCreate.setSpecialtyList(specialtyList);

        return this.doctorRepository.save(doctorToCreate);
    }

    @Transactional
    public Doctor update(Long id, Doctor doctorToUpdate){
        Doctor dbDoctor = this.findById(id);
        if(!dbDoctor.getId().equals(doctorToUpdate.getId())){
            throw new IllegalArgumentException("Update IDs must be the same.");
        }

        dbDoctor.setName(doctorToUpdate.getName());
        dbDoctor.setSpecialtyList(doctorToUpdate.getSpecialtyList());

        List<TimeSlot> timeSlotList = new ArrayList<>();
        for(TimeSlot timeSlot : doctorToUpdate.getAvailableSlotsList()){
            timeSlot.setDoctor(dbDoctor);
            timeSlotList.add(timeSlot);
        }
        dbDoctor.setAvailableSlotsList(timeSlotList);
        dbDoctor.setBookedAppointmentList(doctorToUpdate.getBookedAppointmentList());

        return this.doctorRepository.save(dbDoctor);
    }

    @Transactional(readOnly = true)
    public Doctor findById(Long id){
        return this.doctorRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Doctor> findAll(){
        return this.doctorRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        Doctor dbDoctor = this.findById(id);
        this.doctorRepository.delete(dbDoctor);
    }

    public boolean checkAvailability(Appointment appointment) throws IllegalArgumentException {
        Doctor dbDoctor = this.findById(appointment.getDoctor().getId());
        var available = false;
        for (TimeSlot timeSlot : dbDoctor.getAvailableSlotsList()){
            available = appointment.getDoctor().getId().equals(dbDoctor.getId())
                    && !timeSlot.isStatus()
                    && appointment.getDate().getDayOfWeek().equals(timeSlot.getDayOfWeek())
                    && appointment.getTime().getHour() >= timeSlot.getStartTime().getHour()
                    && appointment.getTime().getHour() < timeSlot.getEndTime().getHour();;
        }
        if(!available)throw new IllegalArgumentException("Schedule not available");
        return available;
    }
}
