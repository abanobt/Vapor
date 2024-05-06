package com.vaporapp.database;

import com.loginapp.util.Pair;
import com.vaporapp.ui.label.LibraryGameLabel;
import com.vaporapp.ui.main.VaporApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserLibraryDAO {
    // Method to add a game to the user's library
    public static void addGameToLibrary(int userId, int gameId) throws SQLException {
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
    public static void updateHoursPlayed(int userId, int gameId, int hoursPlayed) throws SQLException {
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

    public static int getTotalPlaytime(int gameId) {
        String sql = "SELECT HoursPlaytime FROM UserLibrary WHERE UserID = ? AND GameID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static float getSessionTime(int gameId) {
        String sql = "SELECT DurationMinutes FROM GameSessions WHERE UserID = ? AND GameID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, VaporApp.APP_SINGLETON.getUserId());
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) / 60f;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Pair<Integer, String> launchGame(int gameId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT Title FROM Games WHERE GameID = ?")) {
            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            String gameTitle = rs.next() ? rs.getString("Title") : "Unknown Game";

            try (PreparedStatement startSessionStmt = conn.prepareStatement("INSERT INTO GameSessions (GameID, SessionStartTime) VALUES (?, NOW())", PreparedStatement.RETURN_GENERATED_KEYS)) {
                startSessionStmt.setInt(1, gameId);
                startSessionStmt.executeUpdate();
                ResultSet sessionRs = startSessionStmt.getGeneratedKeys();
                int sessionId = sessionRs.next() ? sessionRs.getInt(1) : -1;
                return new Pair<Integer, String>(sessionId, gameTitle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isSessionActive(int gameId) {
        String sql = "SELECT COUNT(*) FROM GameSessions WHERE UserID = ? AND GameID = ?";
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

    public static void sessionEnded(int sessionId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement endSessionStmt = conn.prepareStatement("UPDATE GameSessions SET SessionEndTime = NOW() WHERE SessionID = ?")) {
            endSessionStmt.setInt(1, sessionId);
            endSessionStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeGame(int gameId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE GameSessions SET SessionEndTime = NOW() WHERE GameID = ? AND SessionEndTime IS NULL")) {
            stmt.setInt(1, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<LibraryGameLabel> getLibraryGames() {
        List<LibraryGameLabel> library = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT GameID, Title FROM Games")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int gameId = rs.getInt("GameID");
                String title = rs.getString("Title");
                library.add(new LibraryGameLabel(gameId, title));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return library;
    }
}
