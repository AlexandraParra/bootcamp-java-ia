package me.dio.controller;

import me.dio.service.TimeSlotService;
import me.dio.service.dto.TimeSlotDTO;
import me.dio.service.mapper.TimeSlotMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService){
        this.timeSlotService = timeSlotService;
    }

    @PostMapping
    public ResponseEntity<TimeSlotDTO> create(@RequestBody TimeSlotDTO timeSlotDTO){
        var timeSlot = this.timeSlotService.create(TimeSlotMapper.INSTANCE.toEntity(timeSlotDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(timeSlot.getId())
                .toUri();
        return ResponseEntity.created(location).body(TimeSlotMapper.INSTANCE.toDTO(timeSlot));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeSlotDTO> update(@PathVariable Long id, @RequestBody TimeSlotDTO timeSlotDTO) {
        var timeSlot = this.timeSlotService.update(id, TimeSlotMapper.INSTANCE.toEntity(timeSlotDTO));
        return ResponseEntity.ok(TimeSlotMapper.INSTANCE.toDTO(timeSlot));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSlotDTO> findById(@PathVariable Long id) {
        var timeSlot = this.timeSlotService.findById(id);
        return ResponseEntity.ok(TimeSlotMapper.INSTANCE.toDTO(timeSlot));
    }

    @GetMapping
    public ResponseEntity<List<TimeSlotDTO>> findAll() {
        var timeSlotList = this.timeSlotService.findAll();
        var timeSlotDTOList = timeSlotList.stream().map(TimeSlotMapper.INSTANCE::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(timeSlotDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.timeSlotService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
