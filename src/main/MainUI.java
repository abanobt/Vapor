package main;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.event.KeyEvent;

public class MainUI extends JTabbedPane {
    private int tabIndex = 0;

    private final StoreUI storeUI;
    private final WishlistUI wishlistUI;

    public MainUI() {
        addTab("Store", StoreUI.STORE_ICON, storeUI = new StoreUI(), "Store (Ctrl + S)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_S);
        addTab("Wishlist", StoreUI.STORE_ICON, wishlistUI = new WishlistUI(), "Wishlist (Ctrl + W)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_W);
//        addTab("Library", StoreUI.STORE_ICON, new LibraryUI(), "Library (Ctrl + L)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_L);
//        addTab("Friends", StoreUI.STORE_ICON, new FriendsUI(), "Friends (Ctrl + F)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_F);
//        addTab("Profile", StoreUI.STORE_ICON, new ProfileUI(), "Profile (Ctrl + P)", KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_P);
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
}
