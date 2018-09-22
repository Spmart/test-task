package com.haulmont.testtask.util;

import com.haulmont.testtask.db.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManagementUtil {
    Connection connection = null;

    public static void main(String[] args) {
        DbManagementUtil util = new DbManagementUtil();
        util.getConnection();
        util.createTables();
        util.fillAuthorsTable();
        util.fillGenresTable();
        util.fillBooksTable();
        util.closeConnection();
    }

    private boolean getConnection() {  //TODO: Should I make "return false" in catch statement?
        try {
            connection = ConnectionManager.getConnection();
        } catch (SQLException e) {
            System.out.println("Connection failed! Error " + e.getErrorCode());
        }
        return true;
    }

    private boolean closeConnection() {
        Statement statement;
        final String sql = "SHUTDOWN";
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Failed to close connection! Error " + e.getErrorCode());
        }
        return true;
    }

    private void createTables() {
        Statement statement;
        String query = "";
        int linesUpdated = 0;
        try {
            statement = connection.createStatement();

            query = "CREATE TABLE IF NOT EXISTS authors (\n" +
                    "\tid BIGINT IDENTITY PRIMARY KEY,\n" +
                    "\tfirst_name VARCHAR(30) NOT NULL,\n" +
                    "\tlast_name VARCHAR(30) NOT NULL,\n" +
                    "\tmiddle_name VARCHAR(30) NOT NULL\n" +
                    ")";
            statement.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS genres (\n" +
                    "\tid BIGINT IDENTITY PRIMARY KEY,\n" +
                    "\tname VARCHAR(50) NOT NULL\n" +
                    ")";
            statement.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS books (\n" +
                    "\tid BIGINT IDENTITY PRIMARY KEY,\n" +
                    "\tname VARCHAR(100) NOT NULL,\n" +
                    "\tauthor_id BIGINT NOT NULL,\n" +
                    "\tgenre_id BIGINT NOT NULL,\n" +
                    "\tpublisher VARCHAR(10) NOT NULL,\n" +
                    "\tyear SMALLINT NOT NULL,\n" +
                    "\tcity VARCHAR(30) NOT NULL,\n" +
                    "\tFOREIGN KEY (author_id) REFERENCES authors (id),\n" +
                    "\tFOREIGN KEY (genre_id) REFERENCES genres (id)\n" +
                    ")";
            statement.executeUpdate(query);
            System.out.println("Tables created.");
        } catch (SQLException e) {
            System.out.println("Can't create tables! Error " + e.getErrorCode());
            e.printStackTrace();
        }
    }

    private void fillAuthorsTable() {
        Statement statement;
        String query = "";
        try {
            statement = connection.createStatement();
            query = "INSERT INTO authors (first_name, last_name, middle_name) VALUES ('Клиффорд', 'Саймак', '')";
            statement.executeUpdate(query);
            query = "INSERT INTO authors (first_name, last_name, middle_name) VALUES ('Гарри', 'Гаррисон', '')";
            statement.executeUpdate(query);
            query = "INSERT INTO authors (first_name, last_name, middle_name) VALUES ('Владимир', 'Цесевич', 'Платонович')";
            statement.executeUpdate(query);
            query = "INSERT INTO authors (first_name, last_name, middle_name) VALUES ('Валерий', 'Комягин', 'Борисович')";
            statement.executeUpdate(query);
            System.out.println("Authors table is filled.");
        } catch (SQLException e) {
            System.out.println("Can't add authors! Error " + e.getErrorCode());
            e.printStackTrace();
        }
    }

    private void fillGenresTable() {
        Statement statement;
        String query = "";
        try {
            statement = connection.createStatement();
            query = "INSERT INTO genres (name) VALUES ('Фантастика')";
            statement.executeUpdate(query);
            query = "INSERT INTO genres (name) VALUES ('Астрономия')";
            statement.executeUpdate(query);
            query = "INSERT INTO genres (name) VALUES ('Компьютерная литература')";
            statement.executeUpdate(query);
            System.out.println("Genres table is filled.");
        } catch (SQLException e) {
            System.out.println("Can't add genres! Error " + e.getErrorCode());
            e.printStackTrace();
        }
    }

    private void fillBooksTable() {
        final String FICTION_ID = "1";  //Not safe, but a little safer than magic numbers
        final String ASTRONOMY_ID = "2";
        final String COMPUTER_LITERATURE_ID = "3";

        final String SIMAK_ID = "1";
        final String HARRISON_ID = "2";
        final String CESEVICH_ID = "3";
        final String KOMYAGIN_ID = "4";

        Statement statement;
        String query = "";
        try {
            statement = connection.createStatement();
            query = "INSERT INTO books (name, author_id, genre_id, publisher, year, city) " +
                    "VALUES ('Коллекционер', "+ SIMAK_ID + ", " + FICTION_ID + ", 'Москва', '1990', 'Москва')";
            statement.executeUpdate(query);
            query = "INSERT INTO books (name, author_id, genre_id, publisher, year, city) " +
                    "VALUES ('Запад Эдема', "+ HARRISON_ID + ", " + FICTION_ID + ", 'Питер', '1995', 'Санкт-Петербург')";
            statement.executeUpdate(query);
            query = "INSERT INTO books (name, author_id, genre_id, publisher, year, city) " +
                    "VALUES ('Что и как наблюдать на небе', "+ CESEVICH_ID + ", " + ASTRONOMY_ID + ", 'Москва', '1970', 'Москва')";
            statement.executeUpdate(query);
            query = "INSERT INTO books (name, author_id, genre_id, publisher, year, city) " +
                    "VALUES ('3DS STUDIO', "+ KOMYAGIN_ID + ", " + COMPUTER_LITERATURE_ID + ", 'Москва', '1992', 'Москва')";
            System.out.println("Books table is filled.");
        } catch (SQLException e) {
            System.out.println("Can't add books! Error " + e.getErrorCode());
            e.printStackTrace();
        }
    }
}
