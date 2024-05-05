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

    private void onLogIn(ActionEvent e) {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
    
        // First check if the username field is empty
        if (username.length() < 1) {
            JOptionPane.showMessageDialog(VaporApp.APP_SINGLETON, "Username required", "Log In Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
        // Then check if the password field is empty
        else if (password.length < 1) {
            JOptionPane.showMessageDialog(VaporApp.APP_SINGLETON, "Password required", "Log In Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
        // Validate login credentials; if valid, proceed to log the user in
        else if (validateLogIn(username, password)) {
            // Assuming a method exists to fetch the user's ID based on the username
            int userId = getUserID(username);
            if (userId > 0) {
                VaporApp.APP_SINGLETON.loggedIn(userId);
            } else {
                JOptionPane.showMessageDialog(VaporApp.APP_SINGLETON, "Invalid username and/or password", "Log In Failed", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        }
        // If credentials are not valid, show an error message
        else {
            JOptionPane.showMessageDialog(VaporApp.APP_SINGLETON, "Invalid username and/or password", "Log In Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    
        // Clear password array for security after processing
        Arrays.fill(password, (char) 0);
    }
    
    // This method would need to be implemented to check the database for the user ID based on the username
    private int getUserID(String username) {
        // Code to retrieve user ID from the database
        String sql = "SELECT UserID FROM Users WHERE Username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("UserID");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1; // Return -1 or another appropriate value to indicate not found
    }

    public void loggedIn(int userId) {
        this.userId = userId;
        getContentPane().removeAll();
        mainUI = new MainUI();
        getContentPane().add(mainUI,PercentConstraints.FULL);
        revalidate();
        repaint(); // Makes sure that UI updates are immediately shown/visible
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
