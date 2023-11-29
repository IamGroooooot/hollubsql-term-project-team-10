package com.main.lms.repository;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.sql.*;

public class DatabaseManager {
    private static final String driverName = "com.main.holub.database.jdbc.JDBCDriver";
    private Connection connection = null;
    private Statement statement = null;

    public DatabaseManager() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Class.forName(driverName).newInstance();
        } catch (ClassNotFoundException e) {
            displayException("Couldn't find driver: " + driverName, e);
        } catch (InstantiationException e) {
            displayException("Couldn't load driver: " + driverName, e);
        } catch (IllegalAccessException e) {
            displayException("Couldn't access driver: " + driverName, e);
        } catch (UnsupportedLookAndFeelException e) {
            displayException("Couldn't set look and feel: " + driverName, e);
        }
    }

    public int executeUpdate(String sqlString) throws SQLException {
        if (connection == null || statement == null) throw new SQLException();

        return statement.executeUpdate(sqlString);
    }

    public ResultSet executeQuery(String sqlQuery) throws SQLException {
        if (connection == null || statement == null) throw new SQLException();

        return statement.executeQuery(sqlQuery);
    }

    public void openDatabase(String databaseName) throws SQLException, IOException {
        if (databaseName == null)
            System.exit(1);

        File database = new File(databaseName);

        if (!database.exists())
            database.mkdir();

        String path = System.getProperty("user.dir")+"\\"+database.getPath();

        try {
            connection = DriverManager.getConnection(Path.of(path).toUri().toString(), "harpo", "swordfish");
            statement = connection.createStatement();
        } catch (SQLException e) {
            displayException("Couldn't open database: " + databaseName, e);
            throw e;
        }
    }

    public void closeDatabase() throws SQLException {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            throw e;
        }
    }

    private void displayException(String message, Exception e) {
        System.out.println("Exception: " + message);
        e.printStackTrace();
    }


}
