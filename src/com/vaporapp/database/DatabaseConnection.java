package com.vaporapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // URL, username and password should be configured according to your database settings
    private static final String URL = "jdbc:mysql://localhost:3306/VaporDB";
    private static String USER = "root";  // Update it with your MySQL username
    private static String PASSWORD = "password";  // Update it with your MySQL password

    // Static block to load the JDBC driver
    public static void init(String userName, String password) {
        USER = userName;
        PASSWORD = password;
        try {
            // Explicitly load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    // Method to establish a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}