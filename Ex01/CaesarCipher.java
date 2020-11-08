import java.util.*;

public class CaesarCipher {
  private int key;

  public CaesarCipher() {
    this.key = 3;
  }

  public CaesarCipher(int key) {
    this.key = key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public String encryptText(String plainText) {
    String cipherText = "";
    for(int i = 0; i < plainText.length(); i++) {
      cipherText += String.valueOf((char)((plainText.charAt(i) - 65 + this.key) % 26 + 65));
    }
    return cipherText;
  }

  public String decryptText(String cipherText) {
    String plainText = "";
    int current;
    for(int i = 0; i < cipherText.length(); i++) {
      current = (cipherText.charAt(i) - this.key);
      if(current < 65) current += 26;
      plainText += String.valueOf((char)(current));
    }
    return plainText;
  }

  public static void main(String[] args) {
    String text;
    CaesarCipher cipher = new CaesarCipher();
    Scanner in = new Scanner(System.in);
    System.out.println("Caesar Cipher\n-------------\n");
    System.out.print("Key: ");
    cipher.setKey(in.nextInt());
    System.out.print("Plain Text: ");
    text = in.next().toUpperCase();
    System.out.println("Encrypted! Cipher Text is " + cipher.encryptText(text) + ".");
    System.out.print("\nCipher Text: ");
    text = in.next().toUpperCase();
    System.out.print("Known key? [y/n]: ");
    if(in.next().equals("y")) {
      System.out.print("Key: ");
      cipher.setKey(in.nextInt());
      System.out.println("Decrypted! Plain Text is " + cipher.decryptText(text) + ".");
    } else {
      System.out.println("Possible Plain Texts:");
      for(int i = 0; i < 26; i++) {
        cipher.setKey(i);
        System.out.println(cipher.decryptText(text) + ", Key: " + String.valueOf(i));
      }
    }
    in.close();
  }
}
