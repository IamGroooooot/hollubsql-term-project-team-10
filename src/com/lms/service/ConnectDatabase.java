package com.lms.service;

import com.lms.repository.DatabaseManager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectDatabase {
    private DatabaseManager databaseManager;
    public ConnectDatabase(String name) throws Exception {
        databaseManager = new DatabaseManager();
        databaseManager.openDatabase(name);
        AddTables();
    }

    private void AddTables(){
        try {
            databaseManager.executeUpdate("""
                    create table users ( Id VARCHAR(10) NOT NULL, Name VARCHAR(10) NOT NULL, 
                    PRIMARY KEY( Id ) )""");
            databaseManager.executeUpdate("""
                    create table books ( Id VARCHAR(10) NOT NULL, Name VARCHAR(10) NOT NULL, PRIMARY KEY( Id ) )
                    """);

            databaseManager.executeUpdate("""
                    create table rents ( TimeCheck VARCHAR(25) NOT NULL, BookId VARCHAR(10) NOT NULL, 
                    UserId VARCHAR(25) NOT NULL, Rent INTEGER, 
                    PRIMARY KEY( TimeCheck ) )
                    """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int AddData(String tableName, String idValue, String nameValue){
        try {
            ResultSet resultSet = databaseManager.executeQuery("select Id from " + tableName + " where Id='" + idValue + "'");
            if (resultSet.next()) return -7026;
            return databaseManager.executeUpdate("insert into " + tableName + " VALUES('" +
                    idValue + "', '" + nameValue + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int AddData(String userId, String bookId) {
        try {
            Date today = new Date();
            SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return databaseManager.executeUpdate("insert into rents VALUES('" + date.format(today) + "', '" +
                    userId + "', '" + bookId + "', 1)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int SearchData(String search) {
        try {
            ResultSet resultSet = databaseManager.executeQuery("select * from " + search);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("Id") + ", " + resultSet.getString("Name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
