package com.loginapp.database;

import com.vaporapp.ui.label.StoreGameLabel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDAO {
    public static List<StoreGameLabel> getStoreGames() {
        List<StoreGameLabel> wishlist = new ArrayList<>();
        String sql = "SELECT g.GameID, g.Title AS GameTitle, d.DeveloperName AS DevName, p.PublisherName AS PubName," +
                "gen.GenreName, g.ReleaseDate AS Date, g.Description AS GameDescription, g.Price AS GamePrice," +
                "g.Platform AS GamePlatforms" +
                "FROM Games g " +
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

                    StoreGameLabel wishlistItem = new StoreGameLabel(id, gameTitle, devName, pubName, genreName, "None", date,
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
