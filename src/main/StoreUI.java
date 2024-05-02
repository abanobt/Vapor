package main;

import gameLabel.BaseGameLabel;
import gameLabel.StoreGameLabel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class StoreUI extends JPanel {
    public static final ImageIcon STORE_ICON = new ImageIcon("res/store.png");

    // SAMPLE DATA
    private static final String[] TITLES = {"Minecraft", "Valorant", "Counter Strike", "Hearts of Iron"};
    private static final String[] DEVELOPERS = {"Mojang", "Riot", "Valve", "Paradox"};
    private static final String[] PUBLISHERS = {"Microsoft", "Riot", "Valve", "Paradox"};
    private static final String[] GENRE = {null, "Sandbox", "FPS", "FPS", "Grand Strategy"};
    private static final String[] TAGS = {"Sandbox", "FPS", "FPS", "Grand Strategy"};
    private static final String[] RELEASE_DATE = {"01/02/2009", "03/04/2021", "05/06/2005", "07/08/2011"};
    private static final String[] DESCRIPTION = {"01/02/2009", "03/04/2021", "05/06/2005", "07/08/2011"};
    private static final float[] PRICE = {29.99f, 0.00f, 59.99f, 39.99f};
    private static final String[] PLATFORMS = {"Windows, Mac", "Windows", "Windows", "Windows, Linux"};
    private static final float[] AVG_RATING = {9.2f, 6.3f, 4.2f, 9.0f};

    private final JPanel gamesPanel;
    private final JTextField searchField;
    private final JComboBox<String> genreFilter;
    public StoreUI() {
        setLayout(new PercentLayout());

        add(searchField = new JTextField() {{
            addActionListener(StoreUI.this::onSearch);
            setBorder(BorderFactory.createTitledBorder("Search"));
        }}, new PercentConstraints(0.05f, 0.05f, .78f, .08f));

        // Null value represents no filter
        add(genreFilter = new JComboBox<String>(GENRE) {{
            setBorder(BorderFactory.createTitledBorder("Filter Genre"));
            addActionListener(StoreUI.this::onFilter);
        }}, new PercentConstraints(0.85f, 0.05f, 0.1f, 0.08f));

        gamesPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            for (int i = 0; i < 4; i ++) {
                add(new StoreGameLabel(i, TITLES[i], DEVELOPERS[i], PUBLISHERS[i], GENRE[i+1], TAGS[i],
                        RELEASE_DATE[i], DESCRIPTION[i], PRICE[i], PLATFORMS[i], AVG_RATING[i]));
            }
        }};

        add(new JScrollPane() {{
            setViewportView(gamesPanel);
        }}, new PercentConstraints(.05f, .15f, .9f, .8f));
    }

    // TODO: error when searching and filtering at the same time, they override each other
    // TODO: search by genre, tags, etc.
    private void onSearch(ActionEvent e) {
        String query = searchField.getText().toLowerCase();
        for (Component comp : gamesPanel.getComponents()) {
             if (comp instanceof StoreGameLabel) {
                 StoreGameLabel label = (StoreGameLabel) comp;
                 label.setVisible(query.isEmpty() || label.getTitle().toLowerCase().contains(query));
             }
        }
    }

    private void onFilter(ActionEvent e) {
        String filter = (String)genreFilter.getSelectedItem();
        boolean noFilter = filter == null;
        for (Component comp : gamesPanel.getComponents()) {
            if (comp instanceof StoreGameLabel) {
                StoreGameLabel label = (StoreGameLabel) comp;
                label.setVisible(noFilter || label.getGenre().equals(filter));
            }
        }
    }
}
