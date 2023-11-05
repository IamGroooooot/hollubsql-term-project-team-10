package com.lms;

import com.lms.ui.GUIApp;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("GUIApp");
                frame.setContentPane(new GUIApp().getjPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setSize(500, 300); // UI 기본 크기
                frame.setVisible(true);
            }
        });
    }
}
