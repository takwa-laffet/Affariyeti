package com.example.test.services;

import java.util.List;

public interface Fonctions<T> {
        void Create(T entity);
        void delete(T entity);
        void update(T entity);

        List<T> findAll();
        T findById(int id);
    }

