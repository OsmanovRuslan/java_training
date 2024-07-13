package org.example.services;

import java.util.Collection;

public interface CRUDService<T> {
    T getById(Long id);
    Collection<T> getAll();
    void create(T it);
    void update(Long id, T item);
    void deleteById(Long id);
}

