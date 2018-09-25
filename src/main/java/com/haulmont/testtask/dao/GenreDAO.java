package com.haulmont.testtask.dao;

import com.haulmont.testtask.db.ConnectionManager;
import com.haulmont.testtask.entity.Genre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GenreDAO implements DAO<Genre> {

    private static final String UPDATE_GENRE_QUERY = "UPDATE genres SET name = ?, WHERE id = ?";
    private static final String ADD_GENRE_QUERY = "INSERT INTO genres (name) VALUES (?)";
    private static final String DELETE_GENRE_QUERY = "DELETE FROM genres WHERE id = ?";
    private static final String GET_ALL_GENRES_QUERY = "SELECT * FROM genres";
    private static final String GET_GENRE_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";

    private static final String ID_LABEL = "id";
    private static final String GENRE_LABEL = "name";

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean update(Genre genre) {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_GENRE_QUERY);
            preparedStatement.setString(1, genre.getName());
            preparedStatement.setLong(2, genre.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public boolean add(Genre genre) {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(ADD_GENRE_QUERY);
            preparedStatement.setString(1, genre.getName());
            preparedStatement.executeUpdate();  //TODO: Check, can I did this without statement in one line?
            return true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_GENRE_QUERY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public List<Genre> getAll() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Genre> list = new LinkedList<>();

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_GENRES_QUERY);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(ID_LABEL);
                String name = resultSet.getString(GENRE_LABEL);
                list.add(new Genre(id, name));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return list;
    }

    @Override
    public Genre getById(long id) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_GENRE_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString(GENRE_LABEL);
            return new Genre(id, name);
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;  //TODO: How to be safe with null?
    }
}
