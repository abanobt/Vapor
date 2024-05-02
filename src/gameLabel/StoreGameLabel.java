package gameLabel;

import main.VaporApp;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class StoreGameLabel extends BaseGameLabel {
    private final JButton purchaseButton;
    private final JButton wishlistButton;
    public StoreGameLabel(int id, String title, String developer, String publisher,
                          String genre, String tags, String releaseDate, String description,
                          float price, String platforms, float avgRating) {
        super(id, title, developer, publisher, genre, tags, releaseDate, developer, price, platforms, avgRating);
        add(purchaseButton = new JButton() {{
            addActionListener(e -> {
                //SQL: add/remove item to/from cart
            });
        }});
        add(wishlistButton = new JButton() {{
            addActionListener(e -> {
                //SQL: add/remove item to/from wishlist
            });
        }});
    }

    protected void updateButtons(Graphics g) {
        if (VaporApp.REMOVE_ME.nextBoolean()) { // SQL: check if game is owned
            purchaseButton.setVisible(false);
            wishlistButton.setVisible(false);
            return;
        }

        int height = (getHeight() - 60) / 2;
        int width = height * 4;
        int x = getWidth() - (width) - 20;

        purchaseButton.setBounds(x, 20, width, height);
        wishlistButton.setBounds(x, 20 + height + 20, width, height);

        if (VaporApp.REMOVE_ME.nextBoolean()) { // SQL: check if game is in cart
            purchaseButton.setText("Remove from Cart");
            purchaseButton.setBackground(Color.RED);
        } else {
            purchaseButton.setText("Add from Cart");
            purchaseButton.setBackground(Color.GREEN);
        }

        if (VaporApp.REMOVE_ME.nextBoolean()) { // SQL: check if game is in wishlist
            wishlistButton.setText("Remove from Wishlist");
            wishlistButton.setBackground(Color.RED);
        } else {
            wishlistButton.setText("Add from Wishlist");
            wishlistButton.setBackground(Color.GREEN);
        }
    }
}
