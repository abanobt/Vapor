package ui;

import gameLabel.CartGameLabel;
import gameLabel.GameLabelSearchField;
import gameLabel.LibraryGameLabel;
import main.GameInstance;
import main.PercentConstraints;
import main.PercentLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class LibraryUI extends JPanel {
    public static final ImageIcon LIBRARY_ICON = new ImageIcon("res/store.png");

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
        if (activeGameInstances.containsKey(gameId)) { return; };
        String gameTitle = "Game"; // SQL: get game title
        // SQL: game session started
        activeGameInstances.put(gameId, new GameInstance(gameId, gameTitle, id -> {
            // SQL: game session ended
            activeGameInstances.remove(id);
        }));
    }

    public void closeGame(int gameId) {
        GameInstance game = activeGameInstances.get(gameId);
        if (game == null) { return; }
        game.setVisible(false);
        game.dispose();
        // SQL: game session ended
        activeGameInstances.remove(gameId);
    }

    public void refresh() {
        gamesPanel.removeAll();
        for (int i = 0; i < 4; i ++) {
            // SQL: retrieve games in cart
            gamesPanel.add(new LibraryGameLabel(i, SampleData.TITLES[i],
                    SampleData.DEVELOPERS[i], SampleData.PUBLISHERS[i],
                    SampleData.GENRE[i], SampleData.TAGS[i],
                    SampleData.RELEASE_DATE[i],SampleData. DESCRIPTION[i], SampleData.PRICE[i],
                    SampleData.PLATFORMS[i], SampleData.AVG_RATING[i]));
        }
    }
}
