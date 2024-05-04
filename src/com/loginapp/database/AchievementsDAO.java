import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AchievementsDao {
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
}
