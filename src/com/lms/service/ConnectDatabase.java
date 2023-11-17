package com.lms.service;

import com.lms.repository.DatabaseManager;

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
                create table rents ( Date VARCHAR(25) NOT NULL, BookId VARCHAR(10) NOT NULL, 
                UserId VARCHAR(10) NOT NULL, Rent INTEGER, 
                PRIMARY KEY( Date, BookId, UserId ) )
                """);
    }

    private void AddData(String tableName, String idValue, String nameValue) throws Exception {
        databaseManager.executeUpdate("insert into " + tableName + " VALUES('" +
                idValue + "', '" + nameValue + "')");
    }
    private void AddData(String userId, String bookId) throws Exception {
//        databaseManager.executeUpdate("insert into rents VALUES('" +
//                idValue + "', '" + nameValue + "')");
    }
}
