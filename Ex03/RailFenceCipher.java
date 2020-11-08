import java.util.*;

public class RailFenceCipher {
  private int key;

  public RailFenceCipher() {}

  public RailFenceCipher(int key) {
    this.key = key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public String encryptMessage(String message) {
    String[] encrypted = new String[this.key];
    int i = 0, d = 1, r = 0;
    for(int j = 0; j < encrypted.length; j++) {
      encrypted[j] = "";
    }
    while(i < message.length()) {
      encrypted[r] += message.charAt(i);
      if(r == this.key - 1) d = -1;
      else if(r == 0) d = 1;
      r += d; i++;
    }
    return String.join("", encrypted);
  }

  public String decryptMessage(String message) {
    char[] decrypted = new char[message.length()];
    int i = 0, g = 2 * this.key - 2;
    for(int j = 0; j < this.key; j++) {
      for(int k = j; k < message.length(); k += g) {
        decrypted[k] = message.charAt(i);
        i++;
        if(i == message.length()) {
          j = this.key;
          break;
        }
      }
      g -= 2;
      if(g == 0) g = 2 * this.key - 2;
    }
    return String.valueOf(decrypted);
  }

  public static void main(String[] args) {
    int choice, key;
    Scanner scanner = new Scanner(System.in);
    String message = "";
    RailFenceCipher railFenceCipher = new RailFenceCipher();
    System.out.print("1. Encrypt Message\n2. Decrypt Message\nEnter your choice: ");
    choice = scanner.nextInt();
    if (choice == 1) {
      scanner.nextLine();
      System.out.print("Enter the message to encrypt: ");
      message = scanner.nextLine();
      System.out.print("Enter key: ");
      key = scanner.nextInt();
      railFenceCipher.setKey(key);
      System.out.println("The encrypted message is: ");
      System.out.println(railFenceCipher.encryptMessage(message));
    }
    else {
      scanner.nextLine();
      System.out.print("Enter the message to decrypt: ");
      message = scanner.nextLine();
      System.out.print("Enter key: ");
      key = scanner.nextInt();
      railFenceCipher.setKey(key);
      System.out.println("The decrypted message is: ");
      System.out.println(railFenceCipher.decryptMessage(message));
    }
    scanner.close();
  }
}
