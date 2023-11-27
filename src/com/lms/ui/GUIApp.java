package com.lms.ui;

import com.lms.service.LmsService;
//import com.lms.service.impl.LmsServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUIApp {
    private JPanel jPanel;
    private JRadioButton bookBorrowRadioButton;
    private JRadioButton userAddRadioButton;
    private JRadioButton bookReturnRadioButton;
    private JRadioButton bookAddRadioButton;
    private JRadioButton bookSearchRadioButton;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;
    private JLabel label2;
    private JLabel label1;
    private JTextArea textArea1;
    private JTable table1;
    private JRadioButton userSearchRadioButton;
    private JRadioButton bookBorrowHistoryRadioButton;
    private JPanel label1Panel;
    private JPanel label2Panel;
    private Integer select;
    private LmsService lmsService;

    public JPanel getjPanel() {
        return jPanel;
    }

    public void SetLabels(String label1Str, String label2Str) {
        if (label1Str == null) {
            label1Panel.setVisible(false);
        } else {
            label1.setText(label1Str);
            label1Panel.setVisible(true);
        }
        if (label2Str == null) {
            label2Panel.setVisible(false);
        } else {
            label2.setText(label2Str);
            label2Panel.setVisible(true);
        }
    }

    public void setTable(String[] columnNames, Object[][] data) {
        table1.setModel(new DefaultTableModel(data, columnNames));
        table1.repaint();
    }

    public void RemovePreviousButtonListener() {
        for (ActionListener al : button1.getActionListeners()) {
            button1.removeActionListener(al);
        }
    }

    public void SetButtonListener(ActionListener action) {
        button1.addActionListener(action);
    }

    public void SetResultView(String resultText) {
        textArea1.setText(resultText);
    }

    public String GetTextfield1() {
        return textField1.getText();
    }

    public String GetTextfield2() {
        return textField2.getText();
    }

    public void ClearInsert() {
        this.textField1.setText(null);
        this.textField2.setText(null);
    }

    public void ClearResult() {
        this.textArea1.setText(null);
    }

    public void SetRadioButtons(ActionListener action1, ActionListener action2, ActionListener action3,
                                ActionListener action4, ActionListener action5, ActionListener action6,
                                ActionListener action7) {
        bookBorrowRadioButton.addActionListener(action1);
        userAddRadioButton.addActionListener(action2);
        bookReturnRadioButton.addActionListener(action3);
        bookAddRadioButton.addActionListener(action4);
        bookSearchRadioButton.addActionListener(action5);
        userSearchRadioButton.addActionListener(action6);
        bookBorrowHistoryRadioButton.addActionListener(action7);
    }

    private void initUI() {
        jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bookBorrowRadioButton = new JRadioButton();
        bookBorrowRadioButton.setText("도서 대출");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(bookBorrowRadioButton, gbc);
        userAddRadioButton = new JRadioButton();
        userAddRadioButton.setText("사용자 추가");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(userAddRadioButton, gbc);
        bookReturnRadioButton = new JRadioButton();
        bookReturnRadioButton.setText("도서 반납");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(bookReturnRadioButton, gbc);
        bookAddRadioButton = new JRadioButton();
        bookAddRadioButton.setText("도서 추가");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(bookAddRadioButton, gbc);
        bookSearchRadioButton = new JRadioButton();
        bookSearchRadioButton.setText("도서 검색");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(bookSearchRadioButton, gbc);
        userSearchRadioButton = new JRadioButton();
        userSearchRadioButton.setText("사용자 검색");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(userSearchRadioButton, gbc);
        bookBorrowHistoryRadioButton = new JRadioButton();
        bookBorrowHistoryRadioButton.setText("대출 기록 검색");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(bookBorrowHistoryRadioButton, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        jPanel.add(panel1, gbc);

        textArea1 = new JTextArea();
        textArea1.setLineWrap(false);
        textArea1.setRows(2);
        textArea1.setText("");

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel.add(textArea1, gbc);

        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,2));

        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());

        label1Panel = new JPanel();
        label1Panel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(label1Panel, gbc);
        label1 = new JLabel();
        label1.setText("도서 ID");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        label1Panel.add(label1, gbc);
        textField1 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        label1Panel.add(textField1, gbc);
        label2Panel = new JPanel();
        label2Panel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(label2Panel, gbc);
        label2 = new JLabel();
        label2.setText("유저 ID");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        label2Panel.add(label2, gbc);
        textField2 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        label2Panel.add(textField2, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(panel4, gbc);
        button1 = new JButton();
        button1.setText("입력");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(button1, gbc);

        panel2.add(panel3);

        final JScrollPane scrollPane1 = new JScrollPane();

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel.add(panel2, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel.add(scrollPane1, gbc);
        table1 = new JTable();

        scrollPane1.setViewportView(table1);
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(bookBorrowRadioButton);
        buttonGroup.add(userAddRadioButton);
        buttonGroup.add(bookReturnRadioButton);
        buttonGroup.add(bookAddRadioButton);
        buttonGroup.add(bookSearchRadioButton);
        buttonGroup.add(userSearchRadioButton);
        buttonGroup.add(bookBorrowHistoryRadioButton);

        bookBorrowRadioButton.setSelected(true);
        select = 0;
    }

    public GUIApp() {
//        lmsService = new LmsServiceImpl();

        initUI();
//        initActionListener();
    }

}