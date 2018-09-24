package com.haulmont.testtask.dao;

import com.haulmont.testtask.db.ConnectionManager;
import com.haulmont.testtask.entity.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AuthorDAO implements DAO<Author> {

    private static final String UPDATE_AUTHOR_QUERY = "UPDATE authors SET first_name = ?, last_name = ?, middle_name = ? WHERE id = ?";
    private static final String ADD_AUTHOR_QUERY = "INSERT INTO authors (first_name, last_name, middle_name) VALUES (?,?,?)";
    private static final String DELETE_AUTHOR_QUERY = "DELETE FROM authors WHERE id = ?";
    private static final String GET_ALL_AUTHORS_QUERY = "SELECT * FROM authors";
    private static final String GET_AUTHOR_BY_ID_QUERY = "SELECT * FROM authors WHERE id = ?";
    private static final String GET_AUTHOR_ID_BY_LAST_NAME = "SELECT id FROM authors WHERE last_name = ?";  //TODO: Think about that

    private static final String ID_LABEL = "id";
    private static final String FIRST_NAME_LABEL = "first_name";
    private static final String LAST_NAME_LABEL = "last_name";
    private static final String MIDDLE_NAME_LABEL = "middle_name";

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean update(Author author) {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_AUTHOR_QUERY);
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setString(3, author.getMiddleName());
            preparedStatement.setLong(4, author.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public boolean add(Author author) {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(ADD_AUTHOR_QUERY);
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setString(3, author.getMiddleName());
            preparedStatement.executeUpdate();
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
            preparedStatement = connection.prepareStatement(DELETE_AUTHOR_QUERY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public List<Author> getAll() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Author> list = new LinkedList<>();

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_AUTHORS_QUERY);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(ID_LABEL);
                String firstName = resultSet.getString(FIRST_NAME_LABEL);
                String lastName = resultSet.getString(LAST_NAME_LABEL);
                String middleName = resultSet.getString(MIDDLE_NAME_LABEL);
                list.add(new Author(id, firstName, lastName, middleName));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return list;
    }

    @Override
    public Author getById(long id) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String firstName = resultSet.getString(FIRST_NAME_LABEL);
            String lastName = resultSet.getString(LAST_NAME_LABEL);
            String middleName = resultSet.getString(MIDDLE_NAME_LABEL);
            return new Author(id, firstName, lastName, middleName);
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;  //TODO: How to be safe with null?
    }
}
