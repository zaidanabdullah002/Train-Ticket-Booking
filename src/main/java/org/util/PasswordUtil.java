package org.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash password during signup
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verify password during login
    public static boolean verify(String rawPassword, String storedHash) {
        return BCrypt.checkpw(rawPassword, storedHash);
    }
}
