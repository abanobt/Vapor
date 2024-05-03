package com.loginapp.util;

import org.mindrot.jbcrypt.BCrypt;

public class HashingUtils {

    // Method to hash a password
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Method to check a password against a hashed password
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
