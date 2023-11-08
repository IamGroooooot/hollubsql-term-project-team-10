package com.lms.service;

import javax.swing.table.DefaultTableModel;

public class LmsService {
    private String label1;
    private String label2;
    private String result = "";

    public LmsService() {
        
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
                label1 = "도서명";
                label2 = "저자명";
                break;
        }
    }

}
