import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserShoppingCartDao {
    public void addItemToCart(int userId, int gameId, int quantity) throws SQLException {
        String sql = "INSERT INTO UserShoppingCart (UserID, GameID, ItemQuantity, GameAddedDate) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        }
    }

    public void updateCartItem(int cartId, int quantity) throws SQLException {
        String sql = "UPDATE UserShoppingCart SET ItemQuantity = ? WHERE CartID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartId);
            stmt.executeUpdate();
        }
    }

    public void deleteCartItem(int cartId) throws SQLException {
        String sql = "DELETE FROM UserShoppingCart WHERE CartID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        }
    }
}
