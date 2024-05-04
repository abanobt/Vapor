import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserLibraryDao {
    public void addGameToLibrary(int userId, int gameId) throws SQLException {
        String sql = "INSERT INTO UserLibrary (UserID, GameID, PurchaseDate, HoursPlayed) VALUES (?, ?, NOW(), 0)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        }
    }

    public void updateHoursPlayed(int userId, int gameId, int hoursPlayed) throws SQLException {
        String sql = "UPDATE UserLibrary SET HoursPlayed = HoursPlayed + ? WHERE UserID = ? AND GameID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hoursPlayed);
            stmt.setInt(2, userId);
            stmt.setInt(3, gameId);
            stmt.executeUpdate();
        }
    }
}
