import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WishlistedGamesDao {
    public void addWishlistedGame(int userId, int gameId) throws SQLException {
        String sql = "INSERT INTO WishlistedGames (UserID, GameID, WishlistDateAdded) VALUES (?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        }
    }

    public void deleteWishlistedGame(int wishlistId) throws SQLException {
        String sql = "DELETE FROM WishlistedGames WHERE WishlistID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, wishlistId);
            stmt.executeUpdate();
        }
    }
}
