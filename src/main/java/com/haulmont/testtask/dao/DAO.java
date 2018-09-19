package com.haulmont.testtask.dao;

import java.util.List;

public interface DAO<T> {
    boolean update(T t);
    boolean add(T t);
    boolean delete(long id);
    List<T> getAll();
    T getById(long id);
}
