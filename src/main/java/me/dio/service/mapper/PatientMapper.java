package me.dio.service.mapper;

import me.dio.domain.model.Patient;
import me.dio.service.dto.PatientDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PatientMapper extends InterfaceMapper<PatientDTO, Patient> {

    PatientMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(PatientMapper.class);
}
