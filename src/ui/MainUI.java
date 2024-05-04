package ui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.event.KeyEvent;

public class MainUI extends JTabbedPane {
    private int tabIndex = 0;

    private final StoreUI storeUI;
    private final WishlistUI wishlistUI;
    private final CartUI cartUI;
    private final LibraryUI libraryUI;
    private final ProfileUI profileUI;
    private final FriendsUI friendsUI;
    private final AchievementsUI achievementsUI;

    public MainUI() {
        addTab("Store", StoreUI.STORE_ICON, storeUI = new StoreUI(), "Store (Ctrl + S)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_S);
        addTab("Wishlist", WishlistUI.WISHLIST_ICON, wishlistUI = new WishlistUI(), "Wishlist (Ctrl + W)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_W);
        addTab("Cart", CartUI.CART_ICON, cartUI = new CartUI(), "Cart (Ctrl + C)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_C);
        addTab("Library", LibraryUI.LIBRARY_ICON, libraryUI = new LibraryUI(), "Library (Ctrl + L)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_L);
        addTab("Profile", ProfileUI.PROFILE_ICON, profileUI = new ProfileUI(), "Profile (Ctrl + P)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_P);
        addTab("Friends", FriendsUI.FRIENDS_ICON, friendsUI = new FriendsUI(), "Friends (Ctrl + F)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_F);
        addTab("Achievements", AchievementsUI.ACHIEVEMENTS_ICON, achievementsUI = new AchievementsUI(), "Achievements (Ctrl + A)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_A);
    }

    private void addTab(String title, ImageIcon icon, JPanel ui, String tooltip, int mnemonic) {
        int i = tabIndex++;
        addTab(title, icon, ui);
        setToolTipTextAt(i, tooltip);
        setMnemonicAt(i, mnemonic);
    }

    public void refreshWishlist() {
        wishlistUI.refresh();
    }

    public void refreshCart() {
        cartUI.refresh();
    }

    public void closeGame(int id) {
        libraryUI.closeGame(id);
    }

    public void launchGame(int id) {
        libraryUI.launchGame(id);
    }

    public void openMessages(int friendshipId) {
        friendsUI.openMessages(friendshipId);
    }

    public void refreshLibrary() {
        libraryUI.refresh();
    }
}
