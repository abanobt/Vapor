import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FriendsDAO {
    public void addFriend(int userId, int friendId, String status) throws SQLException {
        String sql = "INSERT INTO Friends (UserID, FriendID, FriendStatus, FriendStartDate) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, friendId);
            stmt.setString(3, status);
            stmt.executeUpdate();
        }
    }

    public void updateFriendStatus(int friendshipId, String status) throws SQLException {
        String sql = "UPDATE Friends SET FriendStatus = ? WHERE FriendshipID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, friendshipId);
            stmt.executeUpdate();
        }
    }

    public void deleteFriend(int friendshipId) throws SQLException {
        String sql = "DELETE FROM Friends WHERE FriendshipID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, friendshipId);
            stmt.executeUpdate();
        }
    }
}
