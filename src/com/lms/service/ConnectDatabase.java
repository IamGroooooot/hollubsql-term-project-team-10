package com.lms.service;

import com.lms.SqlCommands.*;
import com.lms.repository.DatabaseManager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

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
            SqlCommand sqlCommand = new SelectSql(tableName, "Id");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "Id", idValue);
            ResultSet resultSet = databaseManager.executeQuery(sqlCommand.command());
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
            SqlCommand sqlCommand = new SelectSql("books", "Id");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "Id", bookId);
            ResultSet resultBk = databaseManager.executeQuery(sqlCommand.command());
            sqlCommand = new SelectSql("users", "Id");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "Id", userId);
            ResultSet resultUsr = databaseManager.executeQuery(sqlCommand.command());
            if (!resultBk.next() || !resultUsr.next()) return -7026;
            Date today = new Date();
            SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return databaseManager.executeUpdate("insert into rents VALUES('" + date.format(today) + "', '" +
                    userId + "', '" + bookId + "', 1)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int ReturnUpdate(String userId, String bookId) {
        try {
            SqlCommand sqlCommand = new SelectSql("rents", "UserId");
            sqlCommand = AllEqual(sqlCommand, userId, bookId);
            ResultSet resultRt = databaseManager.executeQuery(sqlCommand.command());
            if (!resultRt.next()) return -7026;
            sqlCommand = new UpdateSql("rents");
            sqlCommand = new EqualIntSql(sqlCommand, "Rent", 0);
            sqlCommand = AllEqual(sqlCommand, userId, bookId);
            return databaseManager.executeUpdate(sqlCommand.command());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public SqlCommand AllEqual(SqlCommand sqlCommand, String userId, String bookId) {
        sqlCommand = new ConjunctionSql(sqlCommand, "where");
        sqlCommand = new EqualStrSql(sqlCommand, "UserId", userId);
        sqlCommand = new ConjunctionSql(sqlCommand, "and");
        sqlCommand = new EqualStrSql(sqlCommand, "BookId", bookId);
        sqlCommand = new ConjunctionSql(sqlCommand, "and");
        sqlCommand = new EqualIntSql(sqlCommand, "Rent", 1);
        return sqlCommand;
    }

    public String[][] SearchData(String tableName, String data) {
        List<String[]> results = new ArrayList<>();
        try {
            SqlCommand sqlCommand = new SelectSql(tableName, "*");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "Name", data);
            ResultSet resultSet = databaseManager.executeQuery(sqlCommand.command());
            while (resultSet.next()) {
                String[] singleResult = {resultSet.getString("Id"), resultSet.getString("Name") };
                results.add(singleResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results.toArray(new String[results.size()][]);
    }
    public String[][] SearchRentData(String data) {
        List<String[]> results = new ArrayList<String[]>();
        try {
            SqlCommand sqlCommand = new SelectSql("users", "Id");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "Name", data);
            ResultSet resultRt = databaseManager.executeQuery(sqlCommand.command());
            String userId;
            if (resultRt.next()) userId = resultRt.getString("Id");
            else return results.toArray(new String[0][]);
            sqlCommand = new SelectSql("rents", "*");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "UserId", userId);
            ResultSet resultSet = databaseManager.executeQuery(sqlCommand.command());
            while (resultSet.next()) {
                String rentInfo;
                if (resultSet.getInt("Rent") == 0) rentInfo = "returned";
                else rentInfo = "rent";
                String[] singleResult = {resultSet.getString("BookId"), rentInfo };
                results.add(singleResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results.toArray(new String[results.size()][]);
    }
}
