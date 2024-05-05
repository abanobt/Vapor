package main;

import ui.MainUI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Dimension;

public class VaporApp extends JFrame {
    public static VaporApp APP_SINGLETON;

    public static final ImageIcon ICON = new ImageIcon("res/icon.png");

    private static final int INIT_WIDTH = 1280;
    private static final int INIT_HEIGHT = 720;

    private MainUI mainUI;
    private int userId;

    public static void main(String[] args) {
        APP_SINGLETON = new VaporApp();
        APP_SINGLETON.start();
        APP_SINGLETON.setVisible(true);
    }

    private VaporApp() {
        super("Vapor");

        setSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
        setIconImage(ICON.getImage());
        getContentPane().setLayout(new PercentLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void start() {
        getContentPane().add(new LogInUI(), PercentConstraints.FULL);
    }

    public int getUserId() {
        return userId;
    }

    public void loggedIn(int userId) {
        this.userId = userId;
        getContentPane().removeAll();
        mainUI = new MainUI();
        getContentPane().add(mainUI,PercentConstraints.FULL);
        revalidate();
    }

    public void refreshWishlist() {
        if (mainUI == null) { return; }

        mainUI.refreshWishlist();
    }

    public void refreshCart() {
        if (mainUI == null) { return; }

        mainUI.refreshCart();
    }

    public void closeGame(int id) {
        if (mainUI == null) { return; }

        mainUI.closeGame(id);
    }

    public void launchGame(int id) {
        if (mainUI == null) { return; }

        mainUI.launchGame(id);
    }

    public void openMessages(int friendshipId) {
        if (mainUI == null) { return; }

        mainUI.openMessages(friendshipId);
    }

    public void refreshLibrary() {
        if (mainUI == null) { return; }

        mainUI.refreshLibrary();
    }
}
