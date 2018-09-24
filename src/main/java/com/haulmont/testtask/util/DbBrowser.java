package com.haulmont.testtask.util;

import com.haulmont.testtask.db.ConnectionManager;

import java.sql.*;

public class DbBrowser {
    Connection connection = null;

    public static void main(String[] args) {
        DbBrowser browser = new DbBrowser();
        browser.getConnection();
        browser.showAllTables();
        browser.closeConnection();
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
        final String query = "SHUTDOWN";
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Failed to close connection! Error " + e.getErrorCode());
        }
        return true;
    }

    private void printTableFromResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData;
        String columnValue = "";
        int columnNumber;

        resultSetMetaData = resultSet.getMetaData();
        columnNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnNumber; i++) {
                if (i > 1) System.out.print(", ");
                columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + resultSetMetaData.getColumnName(i));
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private void showAllTables() {
        final String GET_AUTHORS_TABLE_QUERY = "SELECT * FROM authors";
        final String GET_GENRES_TABLE_QUERY = "SELECT * FROM genres";
        final String GET_BOOKS_TABLE_QUERY = "SELECT * FROM books";
        Statement statement;
        ResultSet resultSet;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_AUTHORS_TABLE_QUERY);
            printTableFromResultSet(resultSet);
            resultSet = statement.executeQuery(GET_GENRES_TABLE_QUERY);
            printTableFromResultSet(resultSet);
            resultSet = statement.executeQuery(GET_BOOKS_TABLE_QUERY);
            printTableFromResultSet(resultSet);
        } catch (SQLException e) {
            System.out.println("Can't show a table! Error " + e.getErrorCode());
            e.printStackTrace();
        }
    }
}
