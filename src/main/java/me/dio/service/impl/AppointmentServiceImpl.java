package me.dio.service.impl;

import me.dio.domain.model.Appointment;
import me.dio.domain.model.Doctor;
import me.dio.domain.model.TimeSlot;
import me.dio.domain.repository.AppointmentRepository;
import me.dio.service.AppointmentService;
import me.dio.service.dto.AppointmentDTO;
import me.dio.service.mapper.AppointmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorServiceImpl doctorService;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorServiceImpl doctorService){
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
    }

    @Transactional
    public Appointment create(Appointment appointmentToCreate){
        return this.appointmentRepository.save(appointmentToCreate);
    }

    @Transactional
    public Appointment update(Long id, Appointment appointmentToUpdate){
        Appointment dbAppointment = this.findById(id);
        if(!dbAppointment.getId().equals(appointmentToUpdate.getId())){
            throw new IllegalArgumentException("Update IDs must be the same.");
        }

        dbAppointment.setDate(appointmentToUpdate.getDate());
        dbAppointment.setTime(appointmentToUpdate.getTime());
        dbAppointment.setPatient(appointmentToUpdate.getPatient());
        dbAppointment.setDoctor(appointmentToUpdate.getDoctor());

        return this.appointmentRepository.save(dbAppointment);
    }

    @Transactional(readOnly = true)
    public Appointment findById(Long id){
        return this.appointmentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Appointment> findAll(){
        return this.appointmentRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        Appointment dbAppointment = this.findById(id);
        this.appointmentRepository.delete(dbAppointment);
    }

    public Appointment confirm(Appointment appointmentToCreate) throws IllegalArgumentException {
        if(appointmentToCreate.getId() == null){
            this.create(appointmentToCreate);
        }
        else{
            this.update(appointmentToCreate.getId(), appointmentToCreate);
        }

        Doctor dbDoctor = this.doctorService.findById(appointmentToCreate.getDoctor().getId());
        for(TimeSlot timeSlot: dbDoctor.getAvailableSlotsList()){
            if(appointmentToCreate.getDate().getDayOfWeek().equals(timeSlot.getDayOfWeek())
                    && appointmentToCreate.getTime().getHour() >= timeSlot.getStartTime().getHour()
                    && appointmentToCreate.getTime().getHour() < timeSlot.getEndTime().getHour()
            ) timeSlot.setStatus(Boolean.TRUE);
        }
        return appointmentToCreate;
    }
}
