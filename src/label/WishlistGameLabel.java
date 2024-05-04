package label;

import main.VaporApp;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;

public class WishlistGameLabel extends BaseGameLabel {
    private final JButton purchaseButton;
    private final JButton wishlistButton;
    public WishlistGameLabel(int id, String title, String developer, String publisher,
                             String genre, String tags, String releaseDate, String description,
                             float price, String platforms, float avgRating) {
        super(id, title, developer, publisher, genre, tags, releaseDate, description, price, platforms, avgRating);
        add(purchaseButton = new JButton() {{
            addActionListener(e -> {
                // SQL: add/remove item to/from cart
                 VaporApp.APP_SINGLETON.refreshCart();
            });
        }});

        add(wishlistButton = new JButton() {{
            setText("Remove from Wishlist");
            setBackground(Color.RED);
            addActionListener(e -> {
                // SQL: remove item from wishlist
                 VaporApp.APP_SINGLETON.refreshWishlist();
            });
        }});
    }

    protected void updateButtons(Graphics g) {
        if (false) { // SQL: check if game is owned or game is not in wishlist
            setVisible(false);
            // SQL: remove game from wishlist if it is owned
            VaporApp.APP_SINGLETON.refreshWishlist(); // Refresh wishlist
            return;
        }

        int height = (getHeight() - 60) / 2;
        int width = height * 4;
        int x = getWidth() - (width) - 20;

        purchaseButton.setBounds(x, 20, width, height);
        wishlistButton.setBounds(x, 20 + height + 20, width, height);

        if (false) { // SQL: check if game is in cart
            purchaseButton.setText("Remove from Cart");
            purchaseButton.setBackground(Color.RED);
        } else {
            purchaseButton.setText("Add from Cart");
            purchaseButton.setBackground(Color.GREEN);
        }
    }
}
