package me.dio.service.mapper;

import java.util.List;

public interface InterfaceMapper<D, E> {
    E toEntity(D dto);
    D toDTO(E entity);
    List<E> toEntityList(List<D> dtoList);
    List<D> toDTOList(List<E> entityList);
}
