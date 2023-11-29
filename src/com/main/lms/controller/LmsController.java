package com.main.lms.controller;

import com.main.lms.service.LmsService;
import com.main.lms.ui.GUIApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LmsController {
    private final LmsService model;
    private final GUIApp view;

    public LmsController(LmsService model, GUIApp view) {
        this.model = model;
        this.view = view;
        InitController();
    }

    private void InitController() {
        view.SetRadioButtons(new RadioButtonAction(0), new RadioButtonAction(1),
                new RadioButtonAction(2), new RadioButtonAction(3), new RadioButtonAction(4)
                , new RadioButtonAction(5), new RadioButtonAction(6));
        SetAction(0);
    }

    public void SetAction(int num) {
        // 선택하는 라디오박스? 에 따라서 입력 버튼에 기존에 할당되어있던 listener 을 삭제하고 신규 listener 을 추가해줌
        view.RemovePreviousButtonListener();
        switch (num) {
            case 0:
                view.SetButtonListener(new ExecuteBorrow());
                break;
            case 1:
                view.SetButtonListener(new ExecuteAddUser());
                break;
            case 2:
                view.SetButtonListener(new ExecuteReturn());
                break;
            case 3:
                view.SetButtonListener(new ExecuteAddBook());
                break;
            case 4:
                view.SetButtonListener(new ExecuteBookSearch());
                break;
            case 5:
                view.SetButtonListener(new ExecuteUserSearch());
                break;
            case 6:
                view.SetButtonListener(new ExecuteRentSearch());
                break;
        }
        view.ClearInsert();
        view.ClearResult();
    }

    class RadioButtonAction implements ActionListener {
        final private int selectedNum;
        RadioButtonAction(int selectedNum) {
            this.selectedNum = selectedNum;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            model.SetLabelValues(this.selectedNum);
            view.SetLabels(model.GetLabel1(), model.GetLabel2());
            SetAction(this.selectedNum);
        }
    }

    class ExecuteBorrow implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.BorrowBook(view.GetTextfield1(), view.GetTextfield2());
            view.SetResultView(model.GetResult());
            view.ClearInsert();
        }
    }

    class ExecuteAddUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.AddUser(view.GetTextfield1(), view.GetTextfield2());
            view.SetResultView(model.GetResult());
            view.ClearInsert();
        }
    }

    class ExecuteReturn implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.ReturnBook(view.GetTextfield1(), view.GetTextfield2());
            view.SetResultView(model.GetResult());
            view.ClearInsert();
        }
    }

    class ExecuteAddBook implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.AddBook(view.GetTextfield1(), view.GetTextfield2());
            view.SetResultView(model.GetResult());
            view.ClearInsert();
        }
    }

    class ExecuteBookSearch implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.Search("books", view.GetTextfield2());
            view.setTable(model.ReturnColumnNames(), model.ReturnDataList());
            view.ClearInsert();
        }
    }
    class ExecuteUserSearch implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.Search("users", view.GetTextfield2());
            view.setTable(model.ReturnColumnNames(), model.ReturnDataList());
            view.ClearInsert();
        }
    }

    class ExecuteRentSearch implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.SearchRent(view.GetTextfield2());
            view.setTable(model.ReturnColumnNames(), model.ReturnDataList());
            view.ClearInsert();
        }
    }

}
