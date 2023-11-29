package com.main.lms.service;

import com.main.lms.SqlCommands.*;
import com.main.lms.repository.DatabaseManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SearchDatabase {
    private DatabaseManager databaseManager;
    public SearchDatabase(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public boolean CheckItemExist(String tableName, String value) {
        try {
            SqlCommand sqlCommand = new SelectSql(tableName, "Id");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "Id", value);
            // 동일 아이디를 가진 레코드가 기존에 있었는지 확인
            ResultSet resultSet = databaseManager.executeQuery(sqlCommand.command());
            // 있었다면 추가 거부
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String GetItemString(String tableName, String returnElement, String equalElement, String value) {
        try {
            SqlCommand sqlCommand = new SelectSql(tableName, returnElement);
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, equalElement, value);
            // 동일 아이디를 가진 레코드가 기존에 있었는지 확인
            ResultSet resultSet = databaseManager.executeQuery(sqlCommand.command());
            // 있었다면 추가 거부
            if (resultSet.next()) {
                return resultSet.getString(returnElement);
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean CheckRentExist(String userId, String bookId) {
        try {
            SqlCommand sqlCommand = new SelectSql("rents", "UserId");
            sqlCommand = new ConjunctionSql(sqlCommand, "where");
            sqlCommand = new EqualStrSql(sqlCommand, "UserId", userId);
            sqlCommand = new ConjunctionSql(sqlCommand, "and");
            sqlCommand = new EqualStrSql(sqlCommand, "BookId", bookId);
            sqlCommand = new ConjunctionSql(sqlCommand, "and");
            sqlCommand = new EqualIntSql(sqlCommand, "Rent", 1);
            ResultSet resultRt = databaseManager.executeQuery(sqlCommand.command());
            return resultRt.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    public String[][] SearchRentData(String userId) {
        // 유저명 기반 빌린 책 조회. 여기는 like 문으로 변경할 계획 없음.
        List<String[]> results = new ArrayList<String[]>();
        try {
            SqlCommand sqlCommand = new SelectSql("rents", "*");
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
