package com.lms;

import com.lms.controller.LmsController;
import com.lms.repository.DatabaseManager;
import com.lms.service.SearchDatabase;
import com.lms.service.UpdateDatabase;
import com.lms.service.LmsService;
import com.lms.ui.GUIApp;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Library Management System");
                GUIApp view = new GUIApp();
                frame.setContentPane(view.getjPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setSize(700, 300); // UI 기본 크기
                frame.setVisible(true);
                LmsService model = null;
                try {
                    DatabaseManager databaseManager = new DatabaseManager();
                    databaseManager.openDatabase("library");
                    UpdateDatabase updateDatabase = new UpdateDatabase(databaseManager);
                    SearchDatabase searchDatabase = new SearchDatabase(databaseManager);
                    model = new LmsService(updateDatabase, searchDatabase);
                    LmsController controller = new LmsController(model, view);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
