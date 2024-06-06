package me.dio.service.impl;

import me.dio.domain.model.Appointment;
import me.dio.domain.model.Doctor;
import me.dio.domain.model.TimeSlot;
import me.dio.domain.repository.DoctorRepository;
import me.dio.service.DoctorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Doctor create(Doctor doctorToCreate){
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
        dbDoctor.setAvailableSlotsList(doctorToUpdate.getAvailableSlotsList());

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
        for (TimeSlot timeSlot : dbDoctor.getAvailableSlotsList()){
            if(appointment.getDate().getDayOfWeek().equals(timeSlot.getDayOfWeek())
                && appointment.getTime().getHour() >= timeSlot.getStartTime().getHour()
                    && appointment.getTime().getHour() < timeSlot.getEndTime().getHour()
                && !timeSlot.isStatus()
                && appointment.getDoctor().equals(dbDoctor)
            ) return true;
        }
        throw new IllegalArgumentException("Schedule not available");
    }
}
