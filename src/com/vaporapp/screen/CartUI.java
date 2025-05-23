package com.vaporapp.screen;

import com.vaporapp.database.UserShoppingCartDAO;
import com.vaporapp.ui.label.CartGameLabel;
import com.vaporapp.ui.main.PercentConstraints;
import com.vaporapp.ui.main.PercentLayout;
import com.vaporapp.ui.main.VaporApp;
import com.vaporapp.ui.label.GameLabelSearchField;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;

public class CartUI extends JPanel {
    public static final ImageIcon CART_ICON = new ImageIcon("res/cart.png");

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
                UserShoppingCartDAO.checkoutCart();
                refresh();
                VaporApp.APP_SINGLETON.refreshLibrary();
            });
        }}, new PercentConstraints(0.85f, 0.05f, .1f, .08f));

        add(new JScrollPane() {{
            setViewportView(gamesPanel);
        }}, new PercentConstraints(.05f, .15f, .9f, .8f));

        refresh();
    }

    public void refresh() {
        gamesPanel.removeAll();
        for (CartGameLabel item : UserShoppingCartDAO.getCartItems()) {
            gamesPanel.add(item);
        }
    }
}
