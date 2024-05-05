package label;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import main.VaporApp;
import com.loginapp.database.UserShoppingCartDAO;

public class StoreGameLabel extends BaseGameLabel {
    private final JButton purchaseButton;
    private final JButton wishlistButton;
    private UserShoppingCartDAO shoppingCartDAO = new UserShoppingCartDAO();

    public StoreGameLabel(int id, String title, String developer, String publisher,
                          String genre, String tags, String releaseDate, String description,
                          float price, String platforms, float avgRating) {
        super(id, title, developer, publisher, genre, tags, releaseDate, description, price, platforms, avgRating);

        add(purchaseButton = new JButton() {{
            addActionListener(e -> {
                int userId = VaporApp.APP_SINGLETON.getUserId();
                if (shoppingCartDAO.isGameInCart(userId, id)) {
                    shoppingCartDAO.deleteCartItem(userId, id);
                } else {
                    shoppingCartDAO.addItemToCart(userId, id, 1);
                }
                VaporApp.APP_SINGLETON.refreshCart(); // Refresh cart view
            });
        }});

        add(wishlistButton = new JButton() {{
            addActionListener(e -> {
                int userId = VaporApp.APP_SINGLETON.getUserId();
                if (shoppingCartDAO.isGameInWishlist(userId, id)) {
                    shoppingCartDAO.deleteItemFromWishlist(userId, id);
                } else {
                    shoppingCartDAO.addItemToWishlist(userId, id);
                }
                VaporApp.APP_SINGLETON.refreshWishlist(); // Refresh wishlist view
            });
        }});
    }

    @Override
    protected void updateButtons(Graphics g) {
        int userId = VaporApp.APP_SINGLETON.getUserId();

        if (shoppingCartDAO.isGameInCart(userId, getId())) {
            purchaseButton.setText("Remove from Cart");
            purchaseButton.setBackground(Color.RED);
        } else {
            purchaseButton.setText("Add to Cart");
            purchaseButton.setBackground(Color.GREEN);
        }

        if (shoppingCartDAO.isGameInWishlist(userId, getId())) {
            wishlistButton.setText("Remove from Wishlist");
            wishlistButton.setBackground(Color.RED);
        } else {
            wishlistButton.setText("Add to Wishlist");
            wishlistButton.setBackground(Color.GREEN);
        }
    }
}