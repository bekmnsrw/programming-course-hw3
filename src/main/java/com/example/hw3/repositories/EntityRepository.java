package com.example.hw3.repositories;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<T> {
    List<T> findAll();

    Optional<T> findById(Long id);

    void insert(T entity);

    void update(T entity);

    void delete(Long id);

    List<T> filterAllByFirstName(String firstName);

    List<T> filterAllBySecondName(String secondName);
}
