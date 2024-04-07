package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Dimension;

public class VaporApp extends JFrame {
    public static VaporApp APP_SINGLETON;
    public static final ImageIcon ICON = new ImageIcon("res/icon.png");
    private static final int INIT_WIDTH = 1280;
    private static final int INIT_HEIGHT = 720;

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

    public void start() {
        getContentPane().add(new LogIn(), PercentConstraints.FULL);
    }

    public void loggedIn() {
        getContentPane().removeAll();
        getContentPane().add(new MainScene(),PercentConstraints.FULL);
        revalidate();
    }
}
