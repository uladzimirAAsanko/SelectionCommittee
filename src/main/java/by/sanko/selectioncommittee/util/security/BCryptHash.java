package by.sanko.selectioncommittee.util.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptHash {
    private static final int WORKLOAD = 12;

    public static String hashPassword(String passwordPlaintext) {
        String salt = BCrypt.gensalt(WORKLOAD);
        String hashedPassword = BCrypt.hashpw(passwordPlaintext, salt);

        return (hashedPassword);
    }

    public static boolean checkPassword(String passwordPlaintext, String storedHash) {
        boolean passwordVerified = false;

        if (null == storedHash || !storedHash.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash provided for comparison");
        }
        passwordVerified = BCrypt.checkpw(passwordPlaintext, storedHash);

        return (passwordVerified);
    }
}
