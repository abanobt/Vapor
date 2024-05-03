package ui;

import gameLabel.GameLabelSearchField;
import gameLabel.StoreGameLabel;
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
            // SQL: retrieve all games
            for (int i = 0; i < 4; i ++) {
                add(new StoreGameLabel(i, SampleData.TITLES[i],
                        SampleData.DEVELOPERS[i], SampleData.PUBLISHERS[i],
                        SampleData.GENRE[i], SampleData.TAGS[i],
                        SampleData.RELEASE_DATE[i],SampleData. DESCRIPTION[i], SampleData.PRICE[i],
                        SampleData.PLATFORMS[i], SampleData.AVG_RATING[i]));
            }
        }};

        add(new GameLabelSearchField(gamesPanel),
                new PercentConstraints(0.05f, 0.05f, .9f, .08f));

        add(new JScrollPane() {{
            setViewportView(gamesPanel);
        }}, new PercentConstraints(.05f, .15f, .9f, .8f));
    }
}
