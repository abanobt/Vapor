import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AchievementsDAO {
    public static void addAchievement(int gameId, String name, String description, int points, String condition) throws SQLException {
        String sql = "INSERT INTO Achievements (GameID, AchievementName, AchievementDescription, AchievementPoints, UnlockCondition) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gameId);
            stmt.setString(2, name);
            stmt.setString(3, description);
            stmt.setInt(4, points);
            stmt.setString(5, condition);
            stmt.executeUpdate();
        }
    }
    
        // Method to select all achievements with game titles and a boolean unlocked status
        public staic void List<Achievement> getAchievementsWithGameTitlesAndUnlockStatus() throws SQLException {
            List<Achievement> achievements = new ArrayList<>();
            String sql = "SELECT g.Title AS GameTitle, a.AchievementName, a.AchievementDescription, a.AchievementPoints, a.UnlockCondition, " +
                         "au.AchievementID IS NOT NULL AS IsUnlocked " + // True if unlocked, false if not
                         "FROM Achievements a " +
                         "JOIN Games g ON a.GameID = g.GameID " +
                         "LEFT JOIN AchievementsUnlocked au ON a.AchievementID = au.AchievementID";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String gameTitle = rs.getString("GameTitle");
                        String name = rs.getString("AchievementName");
                        String description = rs.getString("AchievementDescription");
                        int points = rs.getInt("AchievementPoints");
                        String condition = rs.getString("UnlockCondition");
                        boolean isUnlocked = rs.getBoolean("IsUnlocked");
    
                        // Assuming Achievement class constructor takes these parameters:
                        Achievement achievement = new Achievement(gameTitle, name, description, points, condition, isUnlocked);
                        achievements.add(achievement);
                    }
                }
            }
        return achievements;
    }
}    