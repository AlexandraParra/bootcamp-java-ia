package me.dio.service.impl;

import me.dio.domain.model.Specialty;
import me.dio.domain.repository.SpecialtyRepository;
import me.dio.service.SpecialtyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository){
        this.specialtyRepository = specialtyRepository;
    }

    @Transactional
    public Specialty create(Specialty specialtyToCreate){
        return this.specialtyRepository.save(specialtyToCreate);
    }

    @Transactional
    public Specialty update(Long id, Specialty specialtyToUpdate){
        Specialty dbSpecialty = this.findById(id);
        if(!dbSpecialty.getId().equals(specialtyToUpdate.getId())){
            throw new IllegalArgumentException("Update IDs must be the same.");
        }

        dbSpecialty.setName(specialtyToUpdate.getName());

        return this.specialtyRepository.save(dbSpecialty);
    }

    @Transactional(readOnly = true)
    public Specialty findById(Long id){
        return this.specialtyRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Specialty> findAll(){
        return this.specialtyRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        Specialty dbSpecialty = this.findById(id);
        this.specialtyRepository.delete(dbSpecialty);
    }
}
