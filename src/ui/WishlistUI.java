package ui;

import com.loginapp.database.UserShoppingCartDAO;
import com.loginapp.database.WishlistedGamesDAO;
import label.CartGameLabel;
import label.GameLabelSearchField;
import label.WishlistGameLabel;
import main.PercentConstraints;
import main.PercentLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class WishlistUI extends JPanel {
    public static final ImageIcon WISHLIST_ICON = new ImageIcon("res/wishlist.png");

    private final JPanel gamesPanel;

    public WishlistUI() {
        setLayout(new PercentLayout());

        gamesPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Wishlisted Games"));
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
        for (WishlistGameLabel item : WishlistedGamesDAO.getWishlistItems()) {
            gamesPanel.add(item);
        }
    }
}
