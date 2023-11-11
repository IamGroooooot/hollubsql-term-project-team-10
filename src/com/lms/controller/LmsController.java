package com.lms.controller;

import com.lms.service.LmsService;
import com.lms.ui.GUIApp;

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
                new RadioButtonAction(2), new RadioButtonAction(3), new RadioButtonAction(4));
        SetAction(0);
    }

    private void SetAction(int num) {
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
                view.SetButtonListener(new ExecuteSearch());
                break;
        }
        view.ClearInsert();
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

    class ExecuteSearch implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.SearchBook(view.GetTextfield1(), view.GetTextfield2());
            view.SetResultView(model.GetResult());
            view.ClearInsert();
        }
    }

}
