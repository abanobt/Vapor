package ui;

import gameLabel.GameLabelSearchField;
import gameLabel.LibraryGameLabel;
import main.PercentConstraints;
import main.PercentLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// Settings
// Transaction history
public class ProfileUI extends JPanel {
    public static final ImageIcon LIBRARY_ICON = new ImageIcon("res/store.png");

    private final JPanel gamesPanel;

    public ProfileUI() {
        setLayout(new PercentLayout());

        gamesPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        }};

        add(new GameLabelSearchField(gamesPanel),
                new PercentConstraints(0.05f, 0.05f, .9f, .08f));

        add(new JScrollPane() {{
            setViewportView(gamesPanel);
        }}, new PercentConstraints(.05f, .15f, .9f, .8f));

        refresh();
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
