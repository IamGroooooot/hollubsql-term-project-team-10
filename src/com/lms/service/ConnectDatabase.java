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
        // 대여 기록 추가
        try {
            SqlCommand sqlCommand = new SelectSql(tableName, "Id");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "Id", idValue);
            // 동일 아이디를 가진 레코드가 기존에 있었는지 확인
            ResultSet resultSet = databaseManager.executeQuery(sqlCommand.command());
            // 있었다면 추가 거부
            if (resultSet.next()) return -7026;
            return databaseManager.executeUpdate("insert into " + tableName + " VALUES('" +
                    idValue + "', '" + nameValue + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int AddData(String userId, String bookId) {
        // 책 정보 / 유저 정보 추가
        try {
            SqlCommand sqlCommand = new SelectSql("books", "Id");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "Id", bookId);
            // 해당 책 아이디를 가진 사람이 데이터베이스에 존재하는지 확인
            ResultSet resultBk = databaseManager.executeQuery(sqlCommand.command());
            sqlCommand = new SelectSql("users", "Id");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "Id", userId);
            // 해당 유저 아이디를 가진 사람이 데이터베이스에 존재하는지 확인
            ResultSet resultUsr = databaseManager.executeQuery(sqlCommand.command());
            if (!resultBk.next() || !resultUsr.next()) return -7026;
            Date today = new Date();
            SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            // 아이디를 갖고 대여기록 추가. 1은 대여를, 0은 반납 완료를 의미.
            return databaseManager.executeUpdate("insert into rents VALUES('" + date.format(today) + "', '" +
                    userId + "', '" + bookId + "', 1)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int ReturnUpdate(String userId, String bookId) {
        // 대여
        try {
            SqlCommand sqlCommand = new SelectSql("rents", "UserId");
            // 유저 아이디, 책 아이디, 대출 기록 모두 일치하는지 확인하는 where문 sql에 추가
            sqlCommand = AllEqual(sqlCommand, userId, bookId);
            // 기존에 대출기록이 존재했는지 확인
            ResultSet resultRt = databaseManager.executeQuery(sqlCommand.command());
            if (!resultRt.next()) return -7026;
            sqlCommand = new UpdateSql("rents");
            sqlCommand = new EqualIntSql(sqlCommand, "Rent", 0);
            // 대출 상태 1에서 0으로 전환
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
            // 현재 where Name=data 형태로 되어있으나, 추후 기능 추가 시 여기를 변경할 예정
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
        // 리스트 형태로 변환 후, 다시 한번 배열 형태로 바꿔서 JTable에 넣기 좋은 형태로 제작
        return results.toArray(new String[results.size()][]);
    }
    public String[][] SearchRentData(String data) {
        // 유저명 기반 빌린 책 조회. 여기는 like 문으로 변경할 계획 없음.
        List<String[]> results = new ArrayList<String[]>();
        try {
            SqlCommand sqlCommand = new SelectSql("users", "Id");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "Name", data);
            // 유저명 기반으로 유저 아이디 획득
            ResultSet resultRt = databaseManager.executeQuery(sqlCommand.command());
            String userId;
            if (resultRt.next()) userId = resultRt.getString("Id");
            // 유저명을 가진 아이디가 존재하지 않을 경우, 빈 array 리턴
            else return results.toArray(new String[0][]);
            sqlCommand = new SelectSql("rents", "*");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "UserId", userId);
            // 유저 아이디 기반으로 대여 기록 열람
            ResultSet resultSet = databaseManager.executeQuery(sqlCommand.command());
            while (resultSet.next()) {
                String rentInfo;
                // 빌림 상태 integer 을 읽기 좋은 형태로 변환
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
