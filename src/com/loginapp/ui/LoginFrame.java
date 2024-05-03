package com.loginapp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        createUI();
    }

    private void createUI() {
        setTitle("Login");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(3, 2));
        add(new JLabel("Username:"));
        userTextField = new JTextField();
        add(userTextField);
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        add(loginButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Handle login
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
