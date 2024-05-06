package com.vaporapp.ui.label;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;

import com.vaporapp.ui.main.VaporApp;
import com.loginapp.database.UserShoppingCartDAO;

public class CartGameLabel extends BaseGameLabel {
    private final JButton purchaseButton;

    public CartGameLabel(int id, String title, String developer, String publisher,
                         String genre, String tags, String releaseDate, String description,
                         float price, String platforms, float avgRating) {
        super(id, title, developer, publisher, genre, tags, releaseDate, description, price, platforms, avgRating);

        add(purchaseButton = new JButton("Remove from cart") {{
            setBackground(Color.RED);
            addActionListener(e -> {
                UserShoppingCartDAO.deleteCartItem(id);  // Adjusted to use static method correctly
                VaporApp.APP_SINGLETON.refreshCart(); // Refresh cart
            });
        }});
    }

    @Override
    protected void updateButtons(Graphics g) {
        int userId = VaporApp.APP_SINGLETON.getUserId();
        boolean isInCart = UserShoppingCartDAO.isItemInCart(getGameId());

        if (!isInCart) {
            setVisible(false);  // Hide the label if not in cart anymore
        }

        int height = (getHeight() - 40);
        int width = height * 2;
        int x = getWidth() - (width) - 20;

        purchaseButton.setBounds(x, 20, width, height);
    }
}