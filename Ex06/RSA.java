import java.math.BigInteger;
import java.util.Random;
import java.io.*;

public class RSA {
  int primeSize;
  BigInteger p, q, N, r, E, D;

  public RSA(int primeSize) {
    this.primeSize = primeSize;
    p = new BigInteger(primeSize, 10, new Random());
    do {
      q = new BigInteger(primeSize, 10, new Random());
    } while (q.compareTo(p) == 0);
    N = p.multiply(q);
    r = p.subtract(BigInteger.valueOf(1));
    r = r.multiply(q.subtract(BigInteger.valueOf(1)));
    do {
      E = new BigInteger(2 * primeSize, new Random());
    } while ((E.compareTo(r) != -1) || (E.gcd(r).compareTo(BigInteger.valueOf(1)) != 0));
    D = E.modInverse(r);
  }

  public BigInteger[] encrypt(String message) {
    int i;
    byte[] temp = new byte[1];
    byte[] digits = message.getBytes();
    BigInteger[] bigdigits = new BigInteger[digits.length];
    for (i = 0; i < bigdigits.length; i++) {
      temp[0] = digits[i];
      bigdigits[i] = new BigInteger(temp);
    }
    BigInteger[] encrypted = new BigInteger[bigdigits.length];
    for (i = 0; i < bigdigits.length; i++)
      encrypted[i] = bigdigits[i].modPow(E, N);
    return encrypted;
  }

  public String decrypt(BigInteger[] encrypted, BigInteger D, BigInteger N) {
    int i;
    BigInteger[] decrypted = new BigInteger[encrypted.length];
    for (i = 0; i < decrypted.length; i++)
      decrypted[i] = encrypted[i].modPow(D, N);
    char[] charArray = new char[decrypted.length];
    for (i = 0; i < charArray.length; i++)
      charArray[i] = (char) (decrypted[i].intValue());
    return (new String(charArray));
  }

  public static void main(String[] args) throws IOException {
    int primeSize = 8;
    RSA rsa = new RSA(primeSize);
    System.out.println("Key size: " + primeSize);
    System.out.println("p = " + rsa.p.toString(16).toUpperCase());
    System.out.println("q = " + rsa.q.toString(16).toUpperCase());
    System.out.println("N = " + rsa.N.toString(16).toUpperCase());
    System.out.println("r = " + rsa.r.toString(16).toUpperCase());
    System.out.println("E = " + rsa.E.toString(16).toUpperCase());
    System.out.println("D = " + rsa.D.toString(16).toUpperCase());
    int ch;
    BigInteger[] ciphertext;
    String plaintext;
    do {
      System.out.println("\nRSA: 1. Encrypt 2. Decrypt 3. Exit");
      System.out.println("Enter choice:");
      ch = Integer.parseInt(System.console().readLine());
      if (ch == 1) {
        System.out.println("Enter the plain text: ");
        plaintext = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        ciphertext = rsa.encrypt(plaintext);
        System.out.print("Cipher Text: ");
        for (int i = 0; i < ciphertext.length; i++) {
          System.out.print(ciphertext[i].toString(16).toUpperCase());
          if (i != ciphertext.length - 1)
            System.out.print(" ");
        }
      } else if (ch == 2) {
        System.out.println("Enter the space-separated cipher text: ");
        String[] encrypted = (new BufferedReader(new InputStreamReader(System.in))).readLine().split(" ");
        ciphertext = new BigInteger[encrypted.length];
        for (int i = 0; i < encrypted.length; i++) {
          ciphertext[i] = new BigInteger(encrypted[i], 16);
        }
        String recoveredPlaintext = rsa.decrypt(ciphertext, rsa.D, rsa.N);
        System.out.println("Decrypted Plain Text: " + recoveredPlaintext);
      }
    } while (ch != 3);
  }
}
