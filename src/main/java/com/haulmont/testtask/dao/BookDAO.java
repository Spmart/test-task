package com.haulmont.testtask.dao;

import com.haulmont.testtask.db.ConnectionManager;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.entity.Genre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BookDAO implements DAO<Book> {

    private static final String UPDATE_BOOK_QUERY = "UPDATE books " +
            "SET name = ?, author_id = ?, genre_id = ?, publisher = ?, year = ?, city = ? WHERE id = ?";
    private static final String ADD_BOOK_QUERY = "INSERT INTO books " +
            "(name, author_id, genre_id, publisher, year, city) VALUES (?,?,?,?,?,?)";
    private static final String DELETE_BOOK_QUERY = "DELETE FROM book WHERE id = ?";
    private static final String GET_ALL_BOOKS_QUERY = "SELECT * FROM books";
    private static final String GET_BOOK_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_BOOKS_BY_AUTHOR = " WHERE author_id = ?";
    private static final String FIND_BOOKS_BY_GENRE = " WHERE genre_id = ?";

    private static final String ID_LABEL = "id";
    private static final String BOOK_NAME_LABEL = "name";
    private static final String AUTHOR_ID_LABEL = "author_id";
    private static final String GENRE_ID_LABEL = "genre_id";
    private static final String PUBLISHER_LABEL = "publisher";
    private static final String YEAR_LABEL = "year";
    private static final String CITY_LABEL = "city";

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean update(Book book) {

        return false;
    }

    @Override
    public boolean add(Book book) {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(ADD_BOOK_QUERY);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setLong(2, book.getAuthor().getId());
            preparedStatement.setLong(3, book.getGenre().getId());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setShort(5, book.getYear());
            preparedStatement.setString(6, book.getCity());
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
            preparedStatement = connection.prepareStatement(DELETE_BOOK_QUERY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e);
        }
        return false;
    }

    @Override
    public List<Book> getAll() {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Book> list = new LinkedList<>();

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_BOOKS_QUERY);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(ID_LABEL);
                String name = resultSet.getString(BOOK_NAME_LABEL);
                long authorId = resultSet.getLong(AUTHOR_ID_LABEL);
                long genreId = resultSet.getLong(GENRE_ID_LABEL);
                String publisher = resultSet.getString(PUBLISHER_LABEL);
                short year = resultSet.getShort(YEAR_LABEL);
                String city = resultSet.getString(CITY_LABEL);

                Author author = new AuthorDAO().getById(authorId);
                Genre genre = new GenreDAO().getById(genreId);
                list.add(new Book(id, name, author, genre, publisher, year, city));
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return list;
    }

    @Override
    public Book getById(long id) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_BOOK_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String name = resultSet.getString(BOOK_NAME_LABEL);
            long authorId = resultSet.getLong(AUTHOR_ID_LABEL);
            long genreId = resultSet.getLong(GENRE_ID_LABEL);
            String publisher = resultSet.getString(PUBLISHER_LABEL);
            short year = resultSet.getShort(YEAR_LABEL);
            String city = resultSet.getString(CITY_LABEL);

            Author author = new AuthorDAO().getById(authorId);
            Genre genre = new GenreDAO().getById(genreId);
            return new Book(id, name, author, genre, publisher, year, city);
        } catch (SQLException e) {
            logger.error(e);
        }
        return null;
    }
}
