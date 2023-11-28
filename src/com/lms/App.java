package com.lms;

import com.lms.controller.LmsController;
import com.lms.service.ConnectDatabase;
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
                    ConnectDatabase connectDatabase = new ConnectDatabase("library");
                    model = new LmsService(connectDatabase);
                    LmsController controller = new LmsController(model, view);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
