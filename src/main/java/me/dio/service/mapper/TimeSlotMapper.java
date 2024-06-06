package me.dio.service.mapper;

import me.dio.domain.model.TimeSlot;
import me.dio.service.dto.TimeSlotDTO;
import org.mapstruct.Mapper;

@Mapper
public interface TimeSlotMapper extends InterfaceMapper<TimeSlotDTO, TimeSlot> {

    TimeSlotMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TimeSlotMapper.class);
}
