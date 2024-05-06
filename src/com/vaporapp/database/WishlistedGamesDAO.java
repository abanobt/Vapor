package com.vaporapp.database;

import com.vaporapp.ui.label.WishlistGameLabel;
import com.vaporapp.ui.main.VaporApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishlistedGamesDAO {
    public static void addWishlistedGame(int userId, int gameId) throws SQLException {
        String sql = "INSERT INTO WishlistedGames (UserID, GameID, WishlistDateAdded) VALUES (?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        }
    }

    public static void deleteWishlistedGame(int wishlistId) throws SQLException {
        String sql = "DELETE FROM WishlistedGames WHERE WishlistID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, wishlistId);
            stmt.executeUpdate();
        }
    }

    public static List<WishlistGameLabel> getWishlistItems() {
        List<WishlistGameLabel> wishlist = new ArrayList<>();
        String sql = "SELECT wg.WishlistID, g.Title AS GameTitle, d.DeveloperName AS DevName, p.PublisherName AS PubName, " +
                "gen.GenreName, g.ReleaseDate AS Date, g.Description AS GameDescription, g.Price AS GamePrice, " +
                "g.Platform AS GamePlatforms " +
                "FROM WishlistedGames wg " +
                "WHERE wg.UserID = ? " +
                "JOIN Games g ON wg.GameID = g.GameID " +
                "JOIN GameDeveloper gd ON g.GameID = gd.GameID " +
                "JOIN Developers d ON d.DeveloperID = gd.DeveloperID " +
                "JOIN GamePublisher gp ON g.GameID = gp.GameID " +
                "JOIN Publishers p ON p.DeveloperID = gp.DeveloperID " +
                "JOIN GameGenre gg ON gg.GameID = g.GameID " +
                "JOIN Genres gen ON gen.GenreID = gg.GenreID ";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("WishlistID");
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
                    WishlistGameLabel wishlistItem = new WishlistGameLabel(id, gameTitle, devName, pubName, genreName, "None", date,
                            description, price, platforms, avgRating);
                    wishlist.add(wishlistItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishlist;
    }
}
