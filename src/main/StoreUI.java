package main;

import gameLabel.GameLabelSearchField;
import gameLabel.StoreGameLabel;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class StoreUI extends JPanel {
    public static final ImageIcon STORE_ICON = new ImageIcon("res/store.png");

    // SAMPLE DATA
    private static final String[] TITLES = {"Minecraft", "Valorant", "Counter Strike", "Hearts of Iron"};
    private static final String[] DEVELOPERS = {"Mojang", "Riot", "Valve", "Paradox"};
    private static final String[] PUBLISHERS = {"Microsoft", "Riot", "Valve", "Paradox"};
    private static final String[] GENRE = {"Sandbox", "FPS", "FPS", "Grand Strategy"};
    private static final String[] TAGS = {"Sandbox", "FPS", "FPS", "Grand Strategy"};
    private static final String[] RELEASE_DATE = {"01/02/2009", "03/04/2021", "05/06/2005", "07/08/2011"};
    private static final String[] DESCRIPTION = {"01/02/2009", "03/04/2021", "05/06/2005", "07/08/2011"};
    private static final float[] PRICE = {29.99f, 0.00f, 59.99f, 39.99f};
    private static final String[] PLATFORMS = {"Windows, Mac", "Windows", "Windows", "Windows, Linux"};
    private static final float[] AVG_RATING = {9.2f, 6.3f, 4.2f, 9.0f};

    private final JPanel gamesPanel;

    public StoreUI() {
        setLayout(new PercentLayout());

        gamesPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            for (int i = 0; i < 4; i ++) {
                add(new StoreGameLabel(i, TITLES[i], DEVELOPERS[i], PUBLISHERS[i], GENRE[i], TAGS[i],
                        RELEASE_DATE[i], DESCRIPTION[i], PRICE[i], PLATFORMS[i], AVG_RATING[i]));
            }
        }};

        add(new GameLabelSearchField(gamesPanel),
                new PercentConstraints(0.05f, 0.05f, .9f, .08f));

        add(new JScrollPane() {{
            setViewportView(gamesPanel);
        }}, new PercentConstraints(.05f, .15f, .9f, .8f));
    }
}
