import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AchievementsDAO {
    public void addAchievement(int gameId, String name, String description, int points, String condition) throws SQLException {
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
    
    public class AchievementsDAO {
        // Method to select achievements and their associated game titles
        public List<Achievement> getAchievementsWithGameTitles() throws SQLException {
            List<Achievement> achievements = new ArrayList<>();
            String sql = "SELECT g.Title AS GameTitle, a.AchievementName, a.AchievementDescription, a.AchievementPoints, a.UnlockCondition " +
                        "FROM Achievements a JOIN Games g ON a.GameID = g.GameID";
            try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String gameTitle = rs.getString("GameTitle");
                        String name = rs.getString("AchievementName");
                        String description = rs.getString("AchievementDescription");
                        int points = rs.getInt("AchievementPoints");
                        String condition = rs.getString("UnlockCondition");

                        // Assuming Achievement class constructor takes these parameters:
                        Achievement achievement = new Achievement(gameTitle, name, description, points, condition);
                        achievements.add(achievement);
                    }
                }
            }
        }
    }
}