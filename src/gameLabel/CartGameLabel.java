package gameLabel;

import main.VaporApp;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;

public class CartGameLabel extends BaseGameLabel {
    private final JButton purchaseButton;
    public CartGameLabel(int id, String title, String developer, String publisher,
                         String genre, String tags, String releaseDate, String description,
                         float price, String platforms, float avgRating) {
        super(id, title, developer, publisher, genre, tags, releaseDate, description, price, platforms, avgRating);
        add(purchaseButton = new JButton() {{
            setText("Remove from cart");
            setBackground(Color.RED);
            addActionListener(e -> {
                // SQL: remove item from cart
            });
        }});
    }

    protected void updateButtons(Graphics g) {
        if (false) { // SQL: check if game is owned or game is not in cart
            setVisible(false);
            // SQL: remove game from cart if it is owned
            VaporApp.APP_SINGLETON.refreshCart(); // Refresh cart
            return;
        }

        int height = (getHeight() - 40);
        int width = height * 2;
        int x = getWidth() - (width) - 20;

        purchaseButton.setBounds(x, 20, width, height);
    }
}
