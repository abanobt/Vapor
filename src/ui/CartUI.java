package ui;

import label.CartGameLabel;
import label.GameLabelSearchField;
import main.PercentConstraints;
import main.PercentLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;

public class CartUI extends JPanel {
    public static final ImageIcon CART_ICON = new ImageIcon("res/store.png");

    private final JPanel gamesPanel;

    public CartUI() {
        setLayout(new PercentLayout());

        gamesPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Cart Items"));
        }};

        add(new GameLabelSearchField(gamesPanel),
                new PercentConstraints(0.05f, 0.05f, .75f, .08f));

        add(new JButton("Checkout") {{
            setBackground(Color.GREEN);
            addActionListener(e -> {
                // SQL : add game to library, remove it from it cart, and remove it from wishlist if it's there
                // refresh();
                // refresh library as well
            });
        }}, new PercentConstraints(0.85f, 0.05f, .1f, .08f));

        add(new JScrollPane() {{
            setViewportView(gamesPanel);
        }}, new PercentConstraints(.05f, .15f, .9f, .8f));

        refresh();
    }

    public void refresh() {
        gamesPanel.removeAll();
        for (int i = 0; i < 4; i ++) {
            // SQL: retrieve games in cart
            gamesPanel.add(new CartGameLabel(i, SampleData.TITLES[i],
                    SampleData.DEVELOPERS[i], SampleData.PUBLISHERS[i],
                    SampleData.GENRE[i], SampleData.TAGS[i],
                    SampleData.RELEASE_DATE[i],SampleData. DESCRIPTION[i], SampleData.PRICE[i],
                    SampleData.PLATFORMS[i], SampleData.AVG_RATING[i]));
        }
    }
}
