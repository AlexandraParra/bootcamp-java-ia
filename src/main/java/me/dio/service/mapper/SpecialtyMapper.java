package me.dio.service.mapper;

import me.dio.domain.model.Specialty;
import me.dio.service.dto.SpecialtyDTO;
import org.mapstruct.Mapper;

@Mapper
public interface SpecialtyMapper extends InterfaceMapper<SpecialtyDTO, Specialty> {

    SpecialtyMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(SpecialtyMapper.class);
}
