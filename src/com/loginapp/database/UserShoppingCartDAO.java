package com.loginapp.database;

import com.loginapp.database.DatabaseConnection;
import label.AchievementLabel;
import label.CartGameLabel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserShoppingCartDAO {
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

    public static List<CartGameLabel> getCartItems() {
        List<CartGameLabel> cartItems = new ArrayList<>();
        String sql = "SELECT usc.CartID, g.Title AS GameTitle, d.DeveloperName AS DevName, p.PublisherName AS PubName," +
                "gen.GenreName, g.ReleaseDate AS Date, g.Description AS GameDescription, g.Price AS GamePrice," +
                "g.Platform AS GamePlatforms" +
                "FROM UserShoppingCart usc " +
                "JOIN Games g ON usc.GameID = g.GameID " +
                "JOIN GameDeveloper gd ON g.GameID = gd.GameID" +
                "JOIN Developers d ON d.DeveloperID = gd.DeveloperID" +
                "JOIN GamePublisher gp ON g.GameID = gp.GameID" +
                "JOIN Publishers p ON p.DeveloperID = gp.DeveloperID" +
                "JOIN GameGenre gg ON gg.GameID = g.GameID" +
                "JOIN Genres gen ON gen.GenreID = gg.GenreID";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("AchievementID");
                    String gameTitle = rs.getString("GameTitle");
                    String devName = rs.getString("DevName");
                    String pubName = rs.getString("PubName");
                    String genreName = rs.getString("GenreName");
                    String date = rs.getDate("Date").toString();
                    String description = rs.getString("GameDescription");
                    String platforms = rs.getString("GamePlatforms");
                    float price = rs.getFloat("GamePrice");
                    float avgRating = rs.getFloat("AvgRating");

                    // Assuming Achievement class constructor takes these parameters:
                    CartGameLabel cartItem = new CartGameLabel(id, gameTitle, devName, pubName, genreName, "None", date,
                            description, price, platforms, avgRating);
                    cartItems.add(cartItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
}
