package com.lms.service;

import javax.swing.table.DefaultTableModel;

public class LmsService {
    private String label1;
    private String label2;
    private String result = "";

    public LmsService() {
        SetLabelValues(0);
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
            case 3, 4:
                label1 = "도서 ID";
                label2 = "도서 이름";
                break;
        }
    }

    public void BorrowBook(String userId, String bookId) {
        result = userId;
    }

    public void ReturnBook(String userId, String bookId) {
        result = "return";
    }

    public void AddUser(String userId, String userName) {
        result = "adduser";
    }

    public void AddBook(String bookId, String bookName) {
        result = "addbook";
    }

    public void SearchBook(String bookId, String bookName) {
        result = "search";
    }

}
