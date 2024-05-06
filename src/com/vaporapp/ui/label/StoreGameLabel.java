package com.vaporapp.ui.label;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;

import com.vaporapp.ui.main.VaporApp;
import com.vaporapp.database.UserShoppingCartDAO;

public class StoreGameLabel extends BaseGameLabel {
    private final JButton purchaseButton;
    private final JButton wishlistButton;

    public StoreGameLabel(int id, String title, String developer, String publisher,
                          String genre, String tags, String releaseDate, String description,
                          float price, String platforms, float avgRating) {
        super(id, title, developer, publisher, genre, tags, releaseDate, description, price, platforms, avgRating);

        add(purchaseButton = new JButton() {{
            addActionListener(e -> {
                int userId = VaporApp.APP_SINGLETON.getUserId();
                if (UserShoppingCartDAO.isItemInCart(id)) {
                    UserShoppingCartDAO.deleteCartItem(id);
                } else {
                    UserShoppingCartDAO.addItemToCart(id, 1);
                }
                VaporApp.APP_SINGLETON.refreshCart(); // Refresh cart view
            });
        }});

        add(wishlistButton = new JButton() {{
            addActionListener(e -> {
                int userId = VaporApp.APP_SINGLETON.getUserId();
                if (UserShoppingCartDAO.isGameInWishlist(id)) {
                    UserShoppingCartDAO.deleteItemFromWishlist(id);
                } else {
                    UserShoppingCartDAO.addItemToWishlist(id);
                }
                VaporApp.APP_SINGLETON.refreshWishlist(); // Refresh wishlist view
            });
        }});
    }

    @Override
    protected void updateButtons(Graphics g) {
        int userId = VaporApp.APP_SINGLETON.getUserId();

        if (UserShoppingCartDAO.isItemInCart(getGameId())) {
            purchaseButton.setText("Remove from Cart");
            purchaseButton.setBackground(Color.RED);
        } else {
            purchaseButton.setText("Add to Cart");
            purchaseButton.setBackground(Color.GREEN);
        }

        if (UserShoppingCartDAO.isGameInWishlist(getGameId())) {
            wishlistButton.setText("Remove from Wishlist");
            wishlistButton.setBackground(Color.RED);
        } else {
            wishlistButton.setText("Add to Wishlist");
            wishlistButton.setBackground(Color.GREEN);
        }
    }
}