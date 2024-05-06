package com.vaporapp.ui.label;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;

import com.vaporapp.ui.main.VaporApp;
import com.loginapp.database.UserShoppingCartDAO;

public class WishlistGameLabel extends BaseGameLabel {
    private final JButton purchaseButton;
    private final JButton wishlistButton;

    public WishlistGameLabel(int id, String title, String developer, String publisher,
                             String genre, String tags, String releaseDate, String description,
                             float price, String platforms, float avgRating) {
        super(id, title, developer, publisher, genre, tags, releaseDate, description, price, platforms, avgRating);

        add(purchaseButton = new JButton("Add to Cart") {{
            setBackground(Color.GREEN);
            addActionListener(e -> {
                UserShoppingCartDAO.addItemToCart(id, 1);  // Assumes quantity is 1
                VaporApp.APP_SINGLETON.refreshCart(); // Refresh the cart UI after adding item
            });
        }});

        add(wishlistButton = new JButton("Remove from Wishlist") {{
            setBackground(Color.RED);
            addActionListener(e -> {
                UserShoppingCartDAO.deleteItemFromWishlist(id); // Remove item from wishlist
                VaporApp.APP_SINGLETON.refreshWishlist(); // Refresh the wishlist UI
            });
        }});
    }

    @Override
    protected void updateButtons(Graphics g) {
        int userId = VaporApp.APP_SINGLETON.getUserId();
        boolean isOwned = UserShoppingCartDAO.isGameInWishlist(getGameId()); // Need to implement this method in DAO
        boolean isInCart = UserShoppingCartDAO.isItemInCart(getGameId());

        if (isOwned) {
            setVisible(false);
            UserShoppingCartDAO.deleteItemFromWishlist(getGameId()); // If owned, remove from wishlist
            VaporApp.APP_SINGLETON.refreshWishlist();
            return;
        }

        int height = (getHeight() - 60) / 2;
        int width = height * 4;
        int x = getWidth() - width - 20;

        purchaseButton.setBounds(x, 20, width, height);
        wishlistButton.setBounds(x, 20 + height + 20, width, height);

        if (isInCart) {
            purchaseButton.setText("Remove from Cart");
            purchaseButton.setBackground(Color.RED);
        } else {
            purchaseButton.setText("Add to Cart");
            purchaseButton.setBackground(Color.GREEN);
        }
    }
}