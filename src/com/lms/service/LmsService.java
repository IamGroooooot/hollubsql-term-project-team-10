package com.lms.service;

import com.lms.repository.DatabaseManager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LmsService {
    private String label1;
    private String label2;
    private String result = "";
    private ConnectDatabase connectDatabase;
    private String[] columnNames;
    private Object[][] dataList;

    public LmsService(ConnectDatabase connectDatabase) {
        SetLabelValues(0);
        this.connectDatabase = connectDatabase;
    }

    public String GetLabel1() {
        return label1;
    }
    public String GetLabel2() {
        return label2;
    }
    public String GetResult() {
        return result;
    }

    public void SetLabelValues(int selectedNum) {
        switch (selectedNum) {
            case 1:
                label1 = "ID";
                label2 = "이름";
                break;
            case 0, 2:
                label1 = "도서 ID";
                label2 = "유저 ID";
                break;
            case 3:
                label1 = "도서 ID";
                label2 = "도서 이름";
                break;
            case 4:
                label1 = null;
                label2 = "도서 이름";
                break;
            case 5, 6:
                label1 = null;
                label2 = "이름";
                break;
        }
    }

    public void BorrowBook(String userId, String bookId) {
        int resultNum = connectDatabase.AddData(userId, bookId);
        SetResultWhenRent(resultNum);
    }

    public void ReturnBook(String userId, String bookId) {
        int resultNum = connectDatabase.ReturnUpdate(userId, bookId);
        SetResultWhenRent(resultNum);
    }

    public void AddUser(String userId, String userName) {
        int resultNum = connectDatabase.AddData("users", userId, userName);
        SetResultWhenAdd(resultNum);
    }

    public void AddBook(String bookId, String bookName) {
        int resultNum = connectDatabase.AddData("books", bookId, bookName);
        SetResultWhenAdd(resultNum);
    }

    private void SetResultWhenAdd(int resultNum) {
        assert resultNum == 1 || resultNum == -7026;
        if (resultNum == -7026) result = "Id Already Exists";
        else result = "Inserted";
    }

    private void SetResultWhenRent(int resultNum) {
        assert resultNum == 1 || resultNum == -7026;
        if (resultNum == -7026) result = "Id Does Not Exist";
        else result = "Completed";
    }

    public void SearchBook(String bookId, String bookName) {
        result = String.valueOf(connectDatabase.SearchData(bookId));
    }

    /** DB 쿼리 예시 코드
    public void dbTestCode() {
        DatabaseManager databaseManager = new DatabaseManager();
        try {
            databaseManager.openDatabase("temp");
            databaseManager.executeUpdate(
                    "create table test (" +
                            "  Entry      INTEGER      NOT NULL" +
                            ", Customer   VARCHAR (20) NOT NULL" +
                            ", DOW        VARCHAR (3)  NOT NULL" +
                            ", Cups       INTEGER      NOT NULL" +
                            ", Type       VARCHAR (10) NOT NULL" +
                            ", PRIMARY KEY( Entry )" +
                            ")"
            );

            databaseManager.executeUpdate(
                    "insert into test VALUES (1,  'John',   'Mon', 1, 'JustJoe')");

            ResultSet result = databaseManager.executeQuery("select * from test");

            while (result.next()) {
                System.out.println
                        (result.getInt("Entry") + ", "
                                + result.getString("Customer") + ", "
                                + result.getString("DOW") + ", "
                                + result.getInt("Cups") + ", "
                                + result.getString("Type")
                        );
            }
            databaseManager.closeDatabase();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
     */


}
