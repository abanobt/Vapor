import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserSettingsDao {
    public void addUserSettings(int userId, String languagePreference, String notificationSettings, String displayPreference) throws SQLException {
        String sql = "INSERT INTO UserSettings (UserID, LanguagePreference, NotificationSettings, DisplayPreference) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, languagePreference);
            stmt.setString(3, notificationSettings);
            stmt.setString(4, displayPreference);
            stmt.executeUpdate();
        }
    }

    public void updateUserSettings(int userId, String languagePreference, String notificationSettings, String displayPreference) throws SQLException {
        String sql = "UPDATE UserSettings SET LanguagePreference = ?, NotificationSettings = ?, DisplayPreference = ? WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, languagePreference);
            stmt.setString(2, notificationSettings);
            stmt.setString(3, displayPreference);
            stmt.setInt(4, userId);
            stmt.executeUpdate();
        }
    }

    public void deleteUserSettings(int userId) throws SQLException {
        String sql = "DELETE FROM UserSettings WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
}
