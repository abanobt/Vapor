package com.loginapp.ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        createUI();
    }

    private void createUI() {
        setTitle("Main Application Window");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Welcome to the Application!", JLabel.CENTER);
        add(welcomeLabel);

        setVisible(true);
    }
}
