package me.dio.controller;

import me.dio.service.SpecialtyService;
import me.dio.service.dto.SpecialtyDTO;
import me.dio.service.mapper.SpecialtyMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/specialtys")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService){
        this.specialtyService = specialtyService;
    }

    @PostMapping
    public ResponseEntity<SpecialtyDTO> create(@RequestBody SpecialtyDTO specialtyDTO){
        var specialty = this.specialtyService.create(SpecialtyMapper.INSTANCE.toEntity(specialtyDTO));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(specialty.getId())
                .toUri();
        return ResponseEntity.created(location).body(SpecialtyMapper.INSTANCE.toDTO(specialty));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> update(@PathVariable Long id, @RequestBody SpecialtyDTO specialtyDTO) {
        var specialty = this.specialtyService.update(id, SpecialtyMapper.INSTANCE.toEntity(specialtyDTO));
        return ResponseEntity.ok(SpecialtyMapper.INSTANCE.toDTO(specialty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable Long id) {
        var specialty = this.specialtyService.findById(id);
        return ResponseEntity.ok(SpecialtyMapper.INSTANCE.toDTO(specialty));
    }

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> findAll() {
        var specialtyList = this.specialtyService.findAll();
        var specialtyDTOList = specialtyList.stream().map(SpecialtyMapper.INSTANCE::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(specialtyDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
