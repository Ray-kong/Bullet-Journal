package cs3500.pa05.model.data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for hashing passwords using the SHA-256 algorithm.
 */
public class HashPassword {

  /**
   * Hashes a password using the SHA-256 algorithm.
   *
   * @param passwordToHash the password to hash
   * @return the hashed password as a hexadecimal string
   * @throws RuntimeException if an error occurs while hashing the password
   */
  public static String hash(String passwordToHash) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
      StringBuilder hexString = new StringBuilder();
      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error hashing password", e);
    }
  }
}
