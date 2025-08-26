package com.curso.dao;

import com.curso.exception.DAOException;

import java.util.List;

public interface DAO<T, K>{
    void save(T a) throws DAOException;

    void update(T a);

    void delete(T a);

    List<T> getAll();

    T getById(K id);

}
