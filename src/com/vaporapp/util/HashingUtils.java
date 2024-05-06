package com.vaporapp.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using BCrypt.
 */
public class HashingUtils {

    /**
     * Hashes a plain text password using BCrypt with an automatically generated salt.
     * 
     * @param password The plain text password to hash.
     * @return A string containing the hashed password.
     */
    public static String hashPassword(String password) {
        // BCrypt.hashpw hashes the password using the bcrypt algorithm and a newly generated salt.
        // BCrypt.gensalt() generates a salt that is used in the hashing process to ensure uniqueness of the hashes.
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Checks if a plain text password matches a given hashed password.
     * 
     * @param password The plain text password to check.
     * @param hashedPassword The hashed password against which the plain text password is checked.
     * @return true if the password matches the hashed password, false otherwise.
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        // BCrypt.checkpw compares the plain text password with a hashed password.
        // It returns true if the password, when hashed with the same salt as the hashedPassword, matches the hashedPassword.
        return BCrypt.checkpw(password, hashedPassword);
    }
}
