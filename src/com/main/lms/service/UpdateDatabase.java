package com.lms.service;

import com.lms.SqlCommands.*;
import com.lms.repository.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UpdateDatabase {
    private DatabaseManager databaseManager;
    public UpdateDatabase(DatabaseManager databaseManager) throws Exception {
        this.databaseManager = databaseManager;
        AddTables();
    }

    private void AddTables(){
        try {
            // 유저 정보
            databaseManager.executeUpdate("""
                    create table users ( Id VARCHAR(10) NOT NULL, Name VARCHAR(10) NOT NULL, 
                    PRIMARY KEY( Id ) )""");
            // 책 정보
            databaseManager.executeUpdate("""
                    create table books ( Id VARCHAR(10) NOT NULL, Name VARCHAR(10) NOT NULL, PRIMARY KEY( Id ) )
                    """);
            // 대여기록 정보
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
        // 책 정보 / 유저 정보 추가
        try {
            return databaseManager.executeUpdate("insert into " + tableName + " VALUES('" +
                    idValue + "', '" + nameValue + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int AddData(String userId, String bookId) {
        // 대여 기록 추가
        try {
            Date today = new Date();
            SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
            // 아이디를 갖고 대여기록 추가. 1은 대여를, 0은 반납 완료를 의미.
            return databaseManager.executeUpdate("insert into rents VALUES('" + date.format(today) + "', '" +
                    bookId + "', '" + userId + "', 1)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int ReturnUpdate(String userId, String bookId) {
        // 반납
        try {
            SqlCommand sqlCommand = new UpdateSql("rents");
            sqlCommand = new EqualIntSql(sqlCommand, "Rent", 0);
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "UserId", userId);
            sqlCommand = new ConjunctionSql(sqlCommand, "and");
            sqlCommand = new EqualStrSql(sqlCommand, "BookId", bookId);
            sqlCommand = new ConjunctionSql(sqlCommand, "and");
            sqlCommand = new EqualIntSql(sqlCommand, "Rent", 1);
            return databaseManager.executeUpdate(sqlCommand.command());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
