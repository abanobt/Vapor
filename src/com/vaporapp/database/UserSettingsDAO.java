package com.vaporapp.database;

import com.vaporapp.util.Pair;
import com.vaporapp.ui.label.ActivityHistoryLabel;
import com.vaporapp.ui.label.TransactionHistoryLabel;
import com.vaporapp.ui.main.VaporApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSettingsDAO {
    public static void addUserSettings(String languagePreference, String notificationSettings, String displayPreference) throws SQLException {
        String sql = "INSERT INTO UserSettings (UserID, LanguagePreference, NotificationSettings, DisplayPreference) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            stmt.setString(2, languagePreference);
            stmt.setString(3, notificationSettings);
            stmt.setString(4, displayPreference);
            stmt.executeUpdate();
        }
    }

    public static void updateUserSettings(String languagePreference, String notificationSettings, String displayPreference) {
        String sql = "UPDATE UserSettings SET LanguagePreference = ?, NotificationSettings = ?, DisplayPreference = ? WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, languagePreference);
            stmt.setString(2, notificationSettings);
            stmt.setString(3, displayPreference);
            stmt.setInt(4, VaporApp.APP_SINGLETON.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserSettings() throws SQLException {
        String sql = "DELETE FROM UserSettings WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            stmt.executeUpdate();
        }
    }

    public static Pair<String, String> getUserInfo() {
        String sql = "SELECT u.Username, u.Email, u.RegistrationDate, r.RoleName AS UserRole, u.ProfilePicURL " +
                "FROM Users u " +
                "JOIN UserRoles ur ON ur.UserID = u.USERID " +
                "JOIN Roles r ON r.RoleID = ur.RoleID " +
                "WHERE u.UserID = ? ";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            try (ResultSet rs = stmt.executeQuery()) {
                String userName = rs.getString("Username");
                String email = rs.getString("Email");
                String date = rs.getDate("RegistrationDate").toString();
                String role = rs.getString("UserRole");
                String profilePic = rs.getString("ProfilePicURL");
                return new Pair<>("<html>"
                        + "Username: " + userName + "<br>"
                        + "Email: " + email + "<br>"
                        + "RegistrationDate: " + date + "<br>"
                        + "Role: " + role + "<br>"
                        +"</html>", profilePic);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Pair<>("", "");
    }

    public static List<TransactionHistoryLabel> getTransactionHistory() {
        List<TransactionHistoryLabel> transactions = new ArrayList<>();
        String sql = "SELECT t.TransactionID, g.Title AS GameTitle, t.TransactionDate, t.TransactionAmount, t.PaymentMethod " +
                "FROM Transactions t " +
                "JOIN Games g ON t.GameID = g.GameID ";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("TransactionID");
                    String gameTitle = rs.getString("GameTitle");
                    String date = rs.getDate("TransactionDate").toString();
                    String paymentMethod = rs.getString("PaymentMethod");
                    float price = rs.getFloat("TransactionAmount");

                    TransactionHistoryLabel transaction = new TransactionHistoryLabel(id, gameTitle, date, price, paymentMethod);
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static List<ActivityHistoryLabel> getActivityHistory() {
        List<ActivityHistoryLabel> activities = new ArrayList<>();
        String sql = "SELECT * FROM UserActivityLogs " +
                "WHERE ActivityID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ActivityID");
                    String type = rs.getString("ActivityType");
                    String date = rs.getDate("ActivityDate").toString();
                    String details = rs.getString("ActivityDetails");

                    ActivityHistoryLabel activity = new ActivityHistoryLabel(id, type, date, details);
                    activities.add(activity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }
}
