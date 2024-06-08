package me.dio.controller;

import me.dio.service.AppointmentService;
import me.dio.service.DoctorService;
import me.dio.service.PatientService;
import me.dio.service.dto.AppointmentDTO;
import me.dio.service.dto.PatientDTO;
import me.dio.service.mapper.AppointmentMapper;
import me.dio.service.mapper.DoctorMapper;
import me.dio.service.mapper.PatientMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    private final DoctorService doctorService;

    public PatientController(PatientService patientService, DoctorService doctorService){
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<PatientDTO> create(@RequestBody PatientDTO patientDTO){

        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();
        for(var appointment : patientDTO.getAppointmentList()){
            var doctor = DoctorMapper.INSTANCE.toDTO(this.doctorService.findById(appointment.getDoctorId()));
            appointment.setDoctor(doctor);
            appointmentDTOList.add(appointment);
        }

        patientDTO.setAppointmentList(appointmentDTOList);

        var patient = this.patientService.create(PatientMapper.INSTANCE.toEntity(patientDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(patient.getId())
                .toUri();
        return ResponseEntity.created(location).body(PatientMapper.INSTANCE.toDTO(patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> update(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        var patient = this.patientService.update(id, PatientMapper.INSTANCE.toEntity(patientDTO));
        return ResponseEntity.ok(PatientMapper.INSTANCE.toDTO(patient));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable Long id) {
        var patient = this.patientService.findById(id);
        return ResponseEntity.ok(PatientMapper.INSTANCE.toDTO(patient));
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll() {
        var patientList = this.patientService.findAll();
        var patientDTOList = patientList.stream().map(PatientMapper.INSTANCE::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(patientDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
