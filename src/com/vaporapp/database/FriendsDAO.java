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
        String sql = "UPDATE Friends SET FriendStatus = ? WHERE FriendshipId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, friendshipId);
            stmt.executeUpdate();
        }
    }

    public static void deleteFriend(int friendshipId) throws SQLException {
        String sql = "DELETE FROM Friends WHERE FriendshipId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, friendshipId);
            stmt.executeUpdate();
        }
    }

    public static void sendMessage(int friendshipId, String message) {
        String sql = "INSERT INTO UserMessages (FriendshipId, MessageDetails, MessageDate) VALUES (?, ?, NOW())";
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
        String sql = "SELECT f.FriendshipId, u.Username, f.FriendStatus, f.FriendStartDate " +
                "FROM Friends f " +
                "JOIN Users u ON f.FriendID = u.UserID " +
                "WHERE u.UserID = ? "; // Adjust based on your schema
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId()); // Assuming you have a way to get the current user's ID
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int friendshipId = rs.getInt("FriendshipId");
                String username = rs.getString("Username");
                String status = rs.getString("FriendStatus");
                String friendStartDate = rs.getDate("FriendStartDate").toString();
                friends.add(new FriendLabel(friendshipId, username, status, friendStartDate));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return friends;
    }

    public static List<MessageLabel> fetchMessages(int friendshipId) {
        List<MessageLabel> messages = new ArrayList<>();
        String sql = "SELECT um.MessageId, um.SenderID, um.MessageDetails, um.MessageDate " +
                "FROM UserMessages um " +
                "JOIN Friends f ON f.UserID = um.SenderID OR f.UserID = um.ReceiverID " +
                "WHERE f.UserID = ? AND (um.SenderID = ? OR um.ReceiverID = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            stmt.setInt(2, friendshipId);
            stmt.setInt(3, friendshipId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int messageId = rs.getInt("MessageId");
                boolean isSender = rs.getInt("SenderId") == VaporApp.APP_SINGLETON.getUserId(); // Adjust depending on how you store sender
                String message = rs.getString("MessageDetails");
                String date = rs.getString("MessageDate");
                messages.add(new MessageLabel(messageId, "Friend Name", isSender, message, date));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return messages;
    }
}
