package ui;

import label.GameLabelSearchField;
import label.LibraryGameLabel;
import main.GameInstance;
import main.PercentConstraints;
import main.PercentLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.loginapp.database.DatabaseConnection;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.HashMap;
import java.util.Map;

public class LibraryUI extends JPanel {
    public static final ImageIcon LIBRARY_ICON = new ImageIcon("res/library.png");

    private final Map<Integer, GameInstance> activeGameInstances;
    private final JPanel gamesPanel;

    public LibraryUI() {
        activeGameInstances = new HashMap<>();

        setLayout(new PercentLayout());

        gamesPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Library of Games"));
        }};

        add(new GameLabelSearchField(gamesPanel),
                new PercentConstraints(0.05f, 0.05f, .9f, .08f));

        add(new JScrollPane() {{
            setViewportView(gamesPanel);
        }}, new PercentConstraints(.05f, .15f, .9f, .8f));

        refresh();
    }

    public void launchGame(int gameId) {
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
                activeGameInstances.put(gameId, new GameInstance(gameId, gameTitle, id -> {
                    try (PreparedStatement endSessionStmt = conn.prepareStatement("UPDATE GameSessions SET SessionEndTime = NOW() WHERE SessionID = ?")) {
                        endSessionStmt.setInt(1, sessionId);
                        endSessionStmt.executeUpdate();
                        activeGameInstances.remove(id);
                    }
                }));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void closeGame(int gameId) {
        GameInstance game = activeGameInstances.get(gameId);
        if (game == null) { return; }
        game.setVisible(false);
        game.dispose();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE GameSessions SET SessionEndTime = NOW() WHERE GameID = ? AND SessionEndTime IS NULL")) {
            stmt.setInt(1, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        activeGameInstances.remove(gameId);
    }
    
    public void refresh() {
        gamesPanel.removeAll();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT GameID, Title FROM Games")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int gameId = rs.getInt("GameID");
                String title = rs.getString("Title");
                gamesPanel.add(new LibraryGameLabel(gameId, title));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        revalidate();
        repaint();
    }
}