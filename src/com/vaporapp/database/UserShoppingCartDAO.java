package com.vaporapp.database;

import com.vaporapp.ui.label.CartGameLabel;
import com.vaporapp.ui.main.VaporApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserShoppingCartDAO {
    public static void addItemToCart(int gameId, int quantity) {
        String sql = "INSERT INTO UserShoppingCart (UserID, GameID, ItemQuantity, GameAddedDate) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            stmt.setInt(2, gameId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCartItem(int cartId, int quantity) {
        String sql = "UPDATE UserShoppingCart SET ItemQuantity = ? WHERE CartID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCartItem(int cartId) {
        String sql = "DELETE FROM UserShoppingCart WHERE CartID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isItemInCart(int gameId) {
        String sql = "SELECT COUNT(*) FROM UserShoppingCart WHERE UserID = ? AND GameID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            stmt.setInt(2, gameId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // If count is more than 0, item is in the cart
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Return false if there's an error or no entry found
    }

    public static void checkoutCart() {
        String sql = "SELECT GameID FROM UserShoppingCart WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int gameId = rs.getInt(1);
                    // Add game to the library
                    try (Connection conn1 = DatabaseConnection.getConnection();
                         PreparedStatement addGameStmt = conn1.prepareStatement("INSERT INTO UserLibrary (UserID, GameID, PurchaseDate) VALUES (?, ?, NOW())")) {
                        addGameStmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
                        addGameStmt.setInt(2, gameId);
                        addGameStmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // Remove game from the cart
                    try (Connection conn1 = DatabaseConnection.getConnection();
                         PreparedStatement removeCartStmt = conn1.prepareStatement("DELETE FROM UserShoppingCart WHERE UserID = ? AND GameID = ?")) {
                        removeCartStmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
                        removeCartStmt.setInt(2, gameId);
                        removeCartStmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // Remove game from wishlist if it's there
                    try (Connection conn1 = DatabaseConnection.getConnection();
                         PreparedStatement removeWishlistStmt = conn1.prepareStatement("DELETE FROM WishlistedGames WHERE UserID = ? AND GameID = ?")) {
                        removeWishlistStmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
                        removeWishlistStmt.setInt(2, gameId);
                        removeWishlistStmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean isGameInWishlist(int gameId) {
        String sql = "SELECT COUNT(*) FROM WishlistedGames WHERE UserID = ? AND GameID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            stmt.setInt(2, gameId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void addItemToWishlist(int gameId) {
        String sql = "INSERT INTO WishlistedGames (UserID, GameID) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteItemFromWishlist(int gameId) {
        String sql = "DELETE FROM WishlistedGames WHERE UserID = ? AND GameID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<CartGameLabel> getCartItems() {
        List<CartGameLabel> cartItems = new ArrayList<>();
        String sql = "SELECT usc.CartID, g.Title AS GameTitle, d.DeveloperName AS DevName, p.PublisherName AS PubName, " +
                "gen.GenreName, g.ReleaseDate AS Date, g.Description AS GameDescription, g.Price AS GamePrice, " +
                "g.Platform AS GamePlatforms " +
                "FROM UserShoppingCart usc " +
                "WHERE usc.UserID = ? " +
                "JOIN Games g ON usc.GameID = g.GameID " +
                "JOIN GameDeveloper gd ON g.GameID = gd.GameID " +
                "JOIN Developers d ON d.DeveloperID = gd.DeveloperID " +
                "JOIN GamePublisher gp ON g.GameID = gp.GameID " +
                "JOIN Publishers p ON p.PublisherID = gp.PublisherID " +
                "JOIN GameGenre gg ON gg.GameID = g.GameID " +
                "JOIN Genres gen ON gen.GenreID = gg.GenreID ";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
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
