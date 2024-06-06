package me.dio.service.mapper;

import me.dio.domain.model.Appointment;
import me.dio.service.dto.AppointmentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AppointmentMapper extends InterfaceMapper<AppointmentDTO, Appointment> {

    AppointmentMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(AppointmentMapper.class);
}
