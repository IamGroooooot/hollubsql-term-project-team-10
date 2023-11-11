package com.lms;

import com.lms.controller.LmsController;
import com.lms.service.LmsService;
import com.lms.ui.GUIApp;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("GUIApp");
                GUIApp view = new GUIApp();
                frame.setContentPane(view.getjPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setSize(500, 300); // UI 기본 크기
                frame.setVisible(true);
                LmsService model = new LmsService();
                LmsController controller = new LmsController(model, view);
            }
        });
    }
}
