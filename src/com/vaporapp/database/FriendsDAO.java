package com.vaporapp.database;

import com.vaporapp.ui.label.FriendLabel;
import com.vaporapp.ui.label.MessageLabel;
import com.vaporapp.ui.main.VaporApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendsDAO {
    public static void addFriend(int userId, int friendId, String status) throws SQLException {
        String sql = "INSERT INTO Friends (UserID, FriendID, FriendStatus, FriendStartDate) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            stmt.setString(3, status);
            stmt.executeUpdate();
        }
    }

    public static void updateFriendStatus(int friendshipId, String status) throws SQLException {
        String sql = "UPDATE Friends SET FriendStatus = ? WHERE FriendshipID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, friendshipId);
            stmt.executeUpdate();
        }
    }

    public static void deleteFriend(int friendshipId) throws SQLException {
        String sql = "DELETE FROM Friends WHERE FriendshipID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, friendshipId);
            stmt.executeUpdate();
        }
    }

    public static void sendMessage(int friendshipId, String message) {
        String sql = "INSERT INTO Messages (friendshipId, messageDetails, messageDate) VALUES (?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, friendshipId);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static List<FriendLabel> fetchFriends() {
        List<FriendLabel> friends = new ArrayList<>();
        String sql = "SELECT friendshipId, username, status, lastUpdated FROM Friends WHERE userId = ?"; // Adjust based on your schema
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId()); // Assuming you have a way to get the current user's ID
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int friendshipId = rs.getInt("friendshipId");
                String username = rs.getString("username");
                String status = rs.getString("status");
                String lastUpdated = rs.getString("lastUpdated");
                friends.add(new FriendLabel(friendshipId, username, status, lastUpdated));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return friends;
    }

    public static List<MessageLabel> fetchMessages(int friendshipId) {
        List<MessageLabel> messages = new ArrayList<>();
        String sql = "SELECT messageId, senderId, messageDetails, messageDate FROM Messages WHERE friendshipId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, friendshipId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int messageId = rs.getInt("messageId");
                boolean isSender = rs.getInt("senderId") == VaporApp.APP_SINGLETON.getUserId(); // Adjust depending on how you store sender
                String message = rs.getString("messageDetails");
                String date = rs.getString("messageDate");
                messages.add(new MessageLabel(messageId, "Friend Name", isSender, message, date));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return messages;
    }
}
