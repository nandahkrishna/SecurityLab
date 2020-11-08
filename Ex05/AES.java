import java.util.*;
import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class AES {
  private static SecretKeySpec secretKey;
  private static byte[] key;

  public static void setKey(String myKey) {
    MessageDigest sha = null;
    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      secretKey = new SecretKeySpec(key, "AES");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String encrypt(String strToEncrypt, String secret) {
    try {
      setKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    } catch (Exception e) {
      System.out.println("Error while encrypting: " + e.toString());
    }
    return null;
  }

  public static String decrypt(String strToDecrypt, String secret) {
    try {
      setKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      System.out.println("Error while decrypting: " + e.toString());
    }
    return null;
  }

  public static void main(String[] args) {
    String secretKey, originalString, encryptedString, decryptedString;
    System.out.println("Enter key string:");
    secretKey = System.console().readLine();
    int ch;
    do {
      System.out.println("AES: 1. Encrypt 2. Decrypt 3. Exit");
      System.out.println("Enter choice:");
      String c = System.console().readLine();
      ch = Integer.parseInt(c);
      if (ch == 1) {
        System.out.println("Enter plain text:");
        originalString = System.console().readLine();
        encryptedString = AES.encrypt(originalString, secretKey);
        System.out.println(encryptedString);
      } else if (ch == 2) {
        System.out.println("Enter cipher text:");
        encryptedString = System.console().readLine();
        decryptedString = AES.decrypt(encryptedString, secretKey);
        System.out.println(decryptedString);
      }
    } while (ch != 3);
  }
}
