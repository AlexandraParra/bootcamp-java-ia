package me.dio.service.impl;

import me.dio.domain.model.Appointment;
import me.dio.domain.model.Patient;
import me.dio.domain.repository.PatientRepository;
import me.dio.service.PatientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final DoctorServiceImpl doctorService;

    private final AppointmentServiceImpl appointmentService;

    public PatientServiceImpl(PatientRepository patientRepository, DoctorServiceImpl doctorService, AppointmentServiceImpl appointmentService){
        this.patientRepository = patientRepository;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    @Transactional
    public Patient create(Patient patientToCreate){
        List<Appointment> appointmentList = patientToCreate.getAppointmentList()
                .stream()
                .map(this::scheduleAppointment)
                .toList();
        patientToCreate.setAppointmentList(appointmentList);

        return this.patientRepository.save(patientToCreate);
    }

    @Transactional
    public Patient update(Long id, Patient patientToUpdate){
        Patient dbPatient = this.findById(id);
        if(!dbPatient.getId().equals(patientToUpdate.getId())){
            throw new IllegalArgumentException("Update IDs must be the same.");
        }

        dbPatient.setName(patientToUpdate.getName());
        dbPatient.setCpf(patientToUpdate.getCpf());
        List<Appointment> appointmentList = patientToUpdate.getAppointmentList()
                .stream()
                .map(this::scheduleAppointment)
                .toList();

        dbPatient.setAppointmentList(appointmentList);

        return this.patientRepository.save(dbPatient);
    }

    @Transactional(readOnly = true)
    public Patient findById(Long id){
        return this.patientRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Patient> findAll(){
        return this.patientRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        Patient dbPatient = this.findById(id);
        this.patientRepository.delete(dbPatient);
    }

    public Appointment scheduleAppointment(Appointment appointmentToCreate){
        var available = this.doctorService.checkAvailability(appointmentToCreate);

        if(available){
            return this.appointmentService.confirm(appointmentToCreate);
        }
        else{
            return null;
        }
    }
}
