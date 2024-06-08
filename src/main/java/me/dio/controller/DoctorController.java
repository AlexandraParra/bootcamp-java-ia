package me.dio.controller;

import me.dio.service.DoctorService;
import me.dio.service.dto.DoctorDTO;
import me.dio.service.mapper.DoctorMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> create(@RequestBody DoctorDTO doctorDTO){
        var doctor = this.doctorService.create(DoctorMapper.INSTANCE.toEntity(doctorDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(doctor.getId())
                .toUri();
        return ResponseEntity.created(location).body(DoctorMapper.INSTANCE.toDTO(doctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> update(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        var doctor = this.doctorService.update(id, DoctorMapper.INSTANCE.toEntity(doctorDTO));
        return ResponseEntity.ok(DoctorMapper.INSTANCE.toDTO(doctor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> findById(@PathVariable Long id) {
        var doctor = this.doctorService.findById(id);
        return ResponseEntity.ok(DoctorMapper.INSTANCE.toDTO(doctor));
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> findAll() {
        var doctorList = this.doctorService.findAll();
        var doctorDTOList = doctorList.stream().map(DoctorMapper.INSTANCE::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(doctorDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
