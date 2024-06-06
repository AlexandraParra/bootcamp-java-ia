package me.dio.service.impl;

import me.dio.domain.model.TimeSlot;
import me.dio.domain.repository.TimeSlotRepository;
import me.dio.service.TimeSlotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotServiceImpl(TimeSlotRepository timeSlotRepository){
        this.timeSlotRepository = timeSlotRepository;
    }

    @Transactional
    public TimeSlot create(TimeSlot timeSlotToCreate){
        return this.timeSlotRepository.save(timeSlotToCreate);
    }

    @Transactional
    public TimeSlot update(Long id, TimeSlot timeSlotToUpdate){
        TimeSlot dbTimeSlot = this.findById(id);
        if(!dbTimeSlot.getId().equals(timeSlotToUpdate.getId())){
            throw new IllegalArgumentException("Update IDs must be the same.");
        }

        dbTimeSlot.setDayOfWeek(timeSlotToUpdate.getDayOfWeek());
        dbTimeSlot.setStartTime(timeSlotToUpdate.getStartTime());
        dbTimeSlot.setEndTime(timeSlotToUpdate.getEndTime());
        dbTimeSlot.setStatus(timeSlotToUpdate.isStatus());

        return this.timeSlotRepository.save(dbTimeSlot);
    }

    @Transactional(readOnly = true)
    public TimeSlot findById(Long id){
        return this.timeSlotRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<TimeSlot> findAll(){
        return this.timeSlotRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        TimeSlot dbTimeSlot = this.findById(id);
        this.timeSlotRepository.delete(dbTimeSlot);
    }
}
