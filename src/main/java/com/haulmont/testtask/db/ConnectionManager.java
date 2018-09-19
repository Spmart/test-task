package com.haulmont.testtask.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final Logger logger = LogManager.getLogger();

    private static final String URL = "jdbc:hsqldb:file:src/main/resources/db/testdb";
    private static final String DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    private static final String USER = "SA";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
