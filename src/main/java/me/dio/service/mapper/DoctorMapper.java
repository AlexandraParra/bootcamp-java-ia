package me.dio.service.mapper;

import me.dio.domain.model.Doctor;
import me.dio.service.dto.DoctorDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DoctorMapper extends InterfaceMapper<DoctorDTO, Doctor> {

    DoctorMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(DoctorMapper.class);
}
