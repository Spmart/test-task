package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Genre;

import java.util.List;

public class GenreDAO implements DAO<Genre> {
    @Override
    public boolean update(Genre genre) {
        return false;
    }

    @Override
    public boolean add(Genre genre) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public Genre getById(long id) {
        return null;
    }
}
