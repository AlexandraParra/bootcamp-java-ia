package me.dio.service;

import java.util.List;

public interface InterfaceService<T, ID> {

    T create(T entity);

    T update(ID id, T entity);

    T findById(ID id);

    List<T> findAll();

    void delete(ID id);
}
