import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLibraryDAO {
    // Method to add a game to the user's library
    public void addGameToLibrary(int userId, int gameId) throws SQLException {
        String sql = "INSERT INTO UserLibrary (UserID, GameID, PurchaseDate, HoursPlayed) VALUES (?, ?, NOW(), 0)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding game to library: " + e.getMessage());
            throw e;  // Re-throw to allow higher-level handlers to manage the exception
        }
    }

    // Method to update hours played for a game in the user's library
    public void updateHoursPlayed(int userId, int gameId, int hoursPlayed) throws SQLException {
        String sql = "UPDATE UserLibrary SET HoursPlayed = HoursPlayed + ? WHERE UserID = ? AND GameID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hoursPlayed);
            stmt.setInt(2, userId);
            stmt.setInt(3, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating hours played: " + e.getMessage());
            throw e;  // Re-throw to allow higher-level handlers to manage the exception
        }
    }
}
