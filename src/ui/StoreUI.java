package ui;

import com.loginapp.database.StoreDAO;
import label.GameLabelSearchField;
import label.StoreGameLabel;
import main.PercentConstraints;
import main.PercentLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class StoreUI extends JPanel {
    public static final ImageIcon STORE_ICON = new ImageIcon("res/store.png");

    // SAMPLE DATA

    private final JPanel gamesPanel;

    public StoreUI() {
        setLayout(new PercentLayout());

        gamesPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Store Games"));
            for (StoreGameLabel game : StoreDAO.getStoreGames()) {
                add(game);
            }
        }};

        add(new GameLabelSearchField(gamesPanel),
                new PercentConstraints(0.05f, 0.05f, .9f, .08f));

        add(new JScrollPane() {{
            setViewportView(gamesPanel);
        }}, new PercentConstraints(.05f, .15f, .9f, .8f));
    }
}
