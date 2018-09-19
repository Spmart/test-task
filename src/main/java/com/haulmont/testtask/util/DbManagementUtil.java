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

        util.closeConnection();
    }

    private boolean getConnection() {
        try {
            connection = ConnectionManager.getConnection();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
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
            System.out.println("Failed to close connection!");
            e.printStackTrace();
        }
        return true;
    }
}
