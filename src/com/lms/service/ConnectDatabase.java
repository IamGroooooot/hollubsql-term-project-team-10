package com.lms.service;

import com.lms.repository.DatabaseManager;

import java.util.Date;

public class ConnectDatabase {
    private DatabaseManager databaseManager;
    public ConnectDatabase(String name) throws Exception {
        databaseManager = new DatabaseManager();
        databaseManager.openDatabase(name);
        AddTables();
    }

    private void AddTables() throws Exception {
        databaseManager.executeUpdate("""
                create table users ( Id VARCHAR(10) NOT NULL, Name VARCHAR(10) NOT NULL, 
                PRIMARY KEY( Id ) )
                """);
        databaseManager.executeUpdate("""
                create table books ( Id VARCHAR(10) NOT NULL, Name VARCHAR(10) NOT NULL, 
                PRIMARY KEY( Id ) )
                """);
        databaseManager.executeUpdate("""
                create table rents ( Date INTEGER NOT NULL, BookId VARCHAR(10) NOT NULL, 
                UserId VARCHAR(10) NOT NULL, Rent INTEGER, 
                PRIMARY KEY( Date, BookId, UserId ) )
                """);
    }

    public void AddData(String tableName, String idValue, String nameValue) throws Exception {
        databaseManager.executeUpdate("insert into " + tableName + " VALUES('" +
                idValue + "', '" + nameValue + "')");
    }
    public void AddData(String userId, String bookId) throws Exception {
        long date = new Date().getTime();
        int int_date = (int)(date / 1000 / 60 / 60 / 24);
        databaseManager.executeUpdate("insert into rents VALUES(" + int_date + ", '" +
                 userId + "', '" + bookId + "', 1)");
    }
}
