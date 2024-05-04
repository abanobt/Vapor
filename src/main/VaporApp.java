package main;

import ui.MainUI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.util.Random;

public class VaporApp extends JFrame {
    public static VaporApp APP_SINGLETON;

    public static final ImageIcon ICON = new ImageIcon("res/icon.png");

    private static final int INIT_WIDTH = 1280;
    private static final int INIT_HEIGHT = 720;

    private MainUI mainUI;

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

    public void loggedIn() {
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
}
