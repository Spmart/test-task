package com.haulmont.testtask.dao;

import com.haulmont.testtask.entity.Author;

import java.util.List;

public class AuthorDAO implements DAO<Author> {
    @Override
    public boolean update(Author author) {
        return false;
    }

    @Override
    public boolean add(Author author) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public List<Author> getAll() {
        return null;
    }

    @Override
    public Author getById(long id) {
        return null;
    }
}
