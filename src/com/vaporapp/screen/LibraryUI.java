package com.vaporapp.screen;

import com.loginapp.database.UserLibraryDAO;
import com.loginapp.util.Pair;
import com.vaporapp.ui.label.LibraryGameLabel;
import com.vaporapp.ui.main.GameInstance;
import com.vaporapp.ui.main.PercentConstraints;
import com.vaporapp.ui.main.PercentLayout;
import com.vaporapp.ui.label.GameLabelSearchField;

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
        Pair<Integer, String> gameLaunch = UserLibraryDAO.launchGame(gameId);
        if (gameLaunch == null) {return;}
        activeGameInstances.put(gameId, new GameInstance(gameId, gameLaunch.secondItem, id -> {
            UserLibraryDAO.sessionEnded(gameLaunch.firstItem);
            activeGameInstances.remove(id);
        }));
    }
    
    public void closeGame(int gameId) {
        GameInstance game = activeGameInstances.get(gameId);
        if (game == null) { return; }
        game.setVisible(false);
        game.dispose();

        UserLibraryDAO.closeGame(gameId);

        activeGameInstances.remove(gameId);
    }
    
    public void refresh() {
        gamesPanel.removeAll();
        for (LibraryGameLabel game : UserLibraryDAO.getLibraryGames()) {
            gamesPanel.add(game);
        }
        revalidate();
        repaint();
    }
}