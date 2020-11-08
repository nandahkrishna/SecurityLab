import java.util.*;

public class VigenereCipher {
  private String key;

  public VigenereCipher() {}

  public VigenereCipher(String key) {
      this.key = key;
  }

  public void setKey(String key) {
      this.key = key;
  }

  private int getCharacterIndex(char c) {
      return ((int) c) - 65;
  }

  private char getCharacter(int n) {
      return ((char) (n + 65));
  }

  private char getEncryptedCharacter(char msg, char keyChar) {
      return getCharacter((getCharacterIndex(msg) + getCharacterIndex(keyChar)) % 26);
  }

  private char getDecryptedCharacter(char msg, char keyChar) {
      return getCharacter((getCharacterIndex(msg) - getCharacterIndex(keyChar) + 26) % 26);
  }

  public String encryptMessage(String message) {
      String encoded = "";
      message = message.replaceAll("\\s", "");
      message = message.toUpperCase();
      for(int i=0; i<message.length(); i++){
          encoded += String.valueOf(getEncryptedCharacter(message.charAt(i),
                  this.key.charAt(i % key.length())));
      }
      return encoded;
  }

  public String decryptMessage(String message) {
      String decoded = "";
      message = message.replaceAll("\\s", "");
      message = message.toUpperCase();
      for(int i = 0; i < message.length(); i++) {
          decoded += String.valueOf(getDecryptedCharacter(message.charAt(i), this.key.charAt(i % key.length())));
      }
      return decoded;
  }

  public static void main(String[] args) {
    int choice;
    String k;
    String message;
    VigenereCipher vigenereCipher = new VigenereCipher();
    Scanner scanner = new Scanner(System.in);
    System.out.print("1. Encrypt Message\n2. Decrypt Message\nEnter your choice: ");
    choice = scanner.nextInt();
    if (choice == 1) {
        scanner.nextLine();
        System.out.print("Enter the message to encrypt: ");
        message = scanner.nextLine();
        System.out.print("Enter key: ");
        k = scanner.next();
        k = k.toUpperCase();
        vigenereCipher.setKey(k);
        System.out.print("The encrypted message is: ");
        System.out.println(vigenereCipher.encryptMessage(message));
    }
    else {
        scanner.nextLine();
        System.out.print("Enter the message to decrypt: ");
        message = scanner.nextLine();
        System.out.print("Enter key: ");
        k = scanner.next();
        k = k.toUpperCase();
        vigenereCipher.setKey(k);
        System.out.print("The decrypted message is: ");
        System.out.println(vigenereCipher.decryptMessage(message));
    }
    scanner.close();
  }
}
