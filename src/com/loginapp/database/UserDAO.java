package com.loginapp.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Method to get the hashed password for a username
    public static String getHashedPassword(String username) {
        String hashedPassword = null;
        String sql = "SELECT password FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                hashedPassword = rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return hashedPassword;
    }

    // Method to verify if the username and password combination is correct
    public static boolean validateUser(String username, String password) {
        String hashedPassword = getHashedPassword(username);
        return hashedPassword != null && HashingUtils.checkPassword(password, hashedPassword);
    }
}
