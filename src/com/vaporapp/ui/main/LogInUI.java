package com.vaporapp.ui.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.loginapp.database.DatabaseConnection;

public class LogInUI extends JPanel {
    private static final PercentConstraints ICON_BOUNDS =  new PercentConstraints(.3f, .225f, .1f, .1f);
    private static final PercentConstraints LOG_IN_LABEL_BOUNDS =  new PercentConstraints(.3f, .225f, .4f, .1f);
    private static final PercentConstraints USERNAME_FIELD_TITLE_BOUNDS =  new PercentConstraints(.3f,.355f, .4f, .02f);
    private static final PercentConstraints USERNAME_FIELD_BOUNDS =  new PercentConstraints(.3f,.375f, .4f, .1f);
    private static final PercentConstraints PASSWORD_FIELD_TITLE_BOUNDS =  new PercentConstraints(.3f,.505f, .4f, .02f);
    private static final PercentConstraints PASSWORD_FIELD_BOUNDS =  new PercentConstraints(.3f,.525f, .4f, .1f);
    private static final PercentConstraints LOG_IN_BUTTON_BOUNDS =  new PercentConstraints(.3f, .675f, .4f, .1f);

    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LogInUI() {
        setLayout(new PercentLayout());

        Font font = getFont().deriveFont(35f);

        add(new IconLabel(true) {{ setIcon(VaporApp.ICON.getImage()); }}, ICON_BOUNDS);
        add(new JLabel("Log In", SwingConstants.CENTER) {{
            setFont(font);
        }}, LOG_IN_LABEL_BOUNDS);
        
        add(new JLabel("Username", SwingConstants.LEFT), USERNAME_FIELD_TITLE_BOUNDS);
        add(usernameField = new JTextField() {{
            setFont(font);
        }}, USERNAME_FIELD_BOUNDS);
        
        add(new JLabel("Password", SwingConstants.LEFT), PASSWORD_FIELD_TITLE_BOUNDS);
        add(passwordField = new JPasswordField() {{
            setFont(font);
        }}, PASSWORD_FIELD_BOUNDS);
        
        add(new JButton("Log In"){{
            addActionListener(LogInUI.this::onLogIn);
            setBackground(Color.BLACK);
            setForeground(Color.WHITE);
            setFont(font);
        }}, LOG_IN_BUTTON_BOUNDS);
    }

    // Called when the login button is clicked, gets the username and
    // password and validates them. If they are valid, the user is logged in.
    // If not valid, then the appropriate message is shown, and the password
    // field is cleared.
    private void onLogIn(ActionEvent e) {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        LoginResult result;
        if (username.length() < 1) {
            JOptionPane.showMessageDialog(VaporApp.APP_SINGLETON, "Username required", "Log In Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        } else if (password.length < 1) {
            JOptionPane.showMessageDialog(VaporApp.APP_SINGLETON, "Password required", "Log In Failed", JOptionPane.ERROR_MESSAGE);
        } else if ((result = validateLogIn(username, new String(password))).isValid) {
            VaporApp.APP_SINGLETON.loggedIn(result.userId);
        } else {
            JOptionPane.showMessageDialog(VaporApp.APP_SINGLETON,"Invalid username and/or password", "Log In Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
        
        // Clear password arr for security
        Arrays.fill(password, (char)0);
    }

    private LoginResult validateLogIn(String username, String password) {
        return new LoginResult(true, 1); // This would have been implemented, but we did not have time due to the scope of the project
//        String hashedPassword = getHashedPassword(username);
//        if (hashedPassword == null) {
//            return new LoginResult(false, -1);
//        }
//        // We intended on implementing password hashing, but were not able to in the time frame
//        boolean valid = BCrypt.checkpw(new String(password), hashedPassword);
//        int userId = valid ? getUserId(username) : -1;
//        return new LoginResult(valid, userId);
    }  


        // Gets the hashed password for this username
    // returns null if the username is invalid
    private String getHashedPassword(String username) {
        String hashedPassword = null;
        String sql = "SELECT PasswordHash FROM Users WHERE Username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    hashedPassword = rs.getString("PasswordHash");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }   

    private int getUserId(String username) {
        String sql = "SELECT UserID FROM Users WHERE Username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("UserID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // User ID not found or error
    }
    
    private static class LoginResult {
        boolean isValid;
        int userId;

        LoginResult(boolean isValid, int userId) {
            this.isValid = isValid;
            this.userId = userId;
        }
    }
}