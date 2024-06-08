package me.dio.controller;

import me.dio.service.AppointmentService;
import me.dio.service.dto.AppointmentDTO;
import me.dio.service.mapper.AppointmentMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> create(@RequestBody AppointmentDTO appointmentDTO){
        var appointment = this.appointmentService.create(AppointmentMapper.INSTANCE.toEntity(appointmentDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(appointment.getId())
                .toUri();
        return ResponseEntity.created(location).body(AppointmentMapper.INSTANCE.toDTO(appointment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> update(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) {
        var appointment = this.appointmentService.update(id, AppointmentMapper.INSTANCE.toEntity(appointmentDTO));
        return ResponseEntity.ok(AppointmentMapper.INSTANCE.toDTO(appointment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) {
        var appointment = this.appointmentService.findById(id);
        return ResponseEntity.ok(AppointmentMapper.INSTANCE.toDTO(appointment));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> findAll() {
        var appointmentList = this.appointmentService.findAll();
        var appointmentDTOList = appointmentList.stream().map(AppointmentMapper.INSTANCE::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(appointmentDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
