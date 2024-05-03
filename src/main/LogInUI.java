package main;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class LogInUI extends JPanel {
    private static final PercentConstraints ICON_BOUNDS =  new PercentConstraints(.3f, .225f, .1f, .1f);
    private static final PercentConstraints LOG_IN_LABEL_BOUNDS =  new PercentConstraints(.3f, .225f, .4f, .1f);
    private static final PercentConstraints USERNAME_FIELD_TITLE_BOUNDS =  new PercentConstraints(.3f,.355f, .4f, .02f);
    private static final PercentConstraints USERNAME_FIELD_BOUNDS =  new PercentConstraints(.3f,.375f, .4f, .1f);
    private static final PercentConstraints PASSWORD_FIELD_TITLE_BOUNDS =  new PercentConstraints(.3f,.505f, .4f, .02f);
    private static final PercentConstraints PASSWORD_FIELD_BOUNDS =  new PercentConstraints(.3f,.525f, .4f, .1f);
    private static final PercentConstraints LOG_IN_BUTTON_BOUNDS =  new PercentConstraints(.3f, .675f, .4f, .1f);

    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LogInUI() {
        setLayout(new PercentLayout());

        Font font = getFont().deriveFont(35f);

        add(new IconLabel(true) {{ setIcon(VaporApp.ICON.getImage()); }}, ICON_BOUNDS);
        add(new JLabel("Log In", SwingConstants.CENTER) {{
            setFont(font);
        }}, LOG_IN_LABEL_BOUNDS);
        
        add(new JLabel("Username", SwingConstants.LEFT), USERNAME_FIELD_TITLE_BOUNDS);
        add(usernameField = new JTextField() {{
            setFont(font);
        }}, USERNAME_FIELD_BOUNDS);
        
        add(new JLabel("Password", SwingConstants.LEFT), PASSWORD_FIELD_TITLE_BOUNDS);
        add(passwordField = new JPasswordField() {{
            setFont(font);
        }}, PASSWORD_FIELD_BOUNDS);
        
        add(new JButton("Log In"){{
            addActionListener(LogInUI.this::onLogIn);
            setBackground(Color.BLACK);
            setForeground(Color.WHITE);
            setFont(font);
        }}, LOG_IN_BUTTON_BOUNDS);
    }

    // Called when the login button is clicked, gets the username and
    // password and validates them. If they are valid, the user is logged in.
    // If not valid, then the appropriate message is shown, and the password
    // field is cleared.
    private void onLogIn(ActionEvent e) {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        if (username.length() < 1) {
            JOptionPane.showMessageDialog(VaporApp.APP_SINGLETON, "Username required", "Log In Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        } else if (password.length < 1) {
            JOptionPane.showMessageDialog(VaporApp.APP_SINGLETON, "Password required", "Log In Failed", JOptionPane.ERROR_MESSAGE);
        } else if (validateLogIn(username, password)) {
            VaporApp.APP_SINGLETON.loggedIn();
        } else {
            JOptionPane.showMessageDialog(VaporApp.APP_SINGLETON,"Invalid username and/or password", "Log In Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
        
        // Clear password arr for security
        Arrays.fill(password, (char)0);
    }

    // Validates the login credentials, returns true if valid, false otherwise
    private boolean validateLogIn(String username, char[] password) {
        return true;
//        String hashedPassword = getHashedPassword(username);
//        if (hashedPassword == null) {
//            return false;
//        }
//        return BCrypt.checkpw(new String(password), hashedPassword);
    }

    // Gets the hashed password for this username
    // returns null if the username is invalid
    private String getHashedPassword(String username) {
        // SQL: get hashed password
        return null;
    }
}
