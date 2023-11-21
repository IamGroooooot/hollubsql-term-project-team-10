package com.lms.ui;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ResultTable extends JFrame {
    /**
     *         사용예시)
     *             String[] header = {"First Name", "Last Name"};
     *             Object[][] data = {{"Taekwan", "Nam"},{"Joohee", "Cho"}};
     *             new ResultTable(header, data);
    */

    public ResultTable(String[] columnNames, Object[][] data) {
        super();

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setLocation(0,0);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        this.add(scrollPane);
        this.pack();
        this.setSize(500, 800);
        this.setVisible(true);
    }
}
