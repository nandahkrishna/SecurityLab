import java.util.*;
import java.lang.Math;

public class RowColumnCipher {
  private Integer[] key;

  public RowColumnCipher() {}

  public RowColumnCipher(Integer[] key) {
    this.key = key;
  }

  public void setKey(Integer[] key) {
    this.key = key;
  }

  private Integer[] getDecryptionKey() {
    Integer[] decryptionKey = new Integer[this.key.length];
    for(int i = 0; i < this.key.length; i++) {
      decryptionKey[i] = Arrays.asList(this.key).indexOf(i + 1);
    }
    return decryptionKey;
  }

  public String encryptMessage(String message) {
    int rows = (int) Math.ceil((double) message.length() / this.key.length);
    String[] matrix = new String[rows];
    for(int i = 0, j = 0; i < message.length(); i += this.key.length, j++) {
      int end = i + this.key.length;
      if(end > message.length()) end = message.length();
      matrix[j] = message.substring(i, end);
    }
    int rem = this.key.length - matrix[rows - 1].length();
    if(rem > 0) {
      for(int i = 0; i < rem; i++) matrix[rows - 1] += "X";
    }
    String encrypted = "";
    for(int i = 0; i < this.key.length; i++) {
      int col = Arrays.asList(this.key).indexOf(i + 1);
      for(int j = 0; j < rows; j++) {
        encrypted += matrix[j].charAt(col);
      }
    }
    return encrypted;
  }

  public String decryptMessage(String message) {
    Integer[] key = getDecryptionKey();
    String[] matrix = new String[key.length];
    int cols = message.length() / key.length;
    for(int i = 0, j = 0; i < key.length; i++, j += cols) {
      int end = j + cols;
      matrix[i] = message.substring(j, end);
    }
    String decrypted = "";
    for(int i = 0; i < cols; i++) {
      for(int j = 0; j < key.length; j++) {
        int row = Arrays.asList(key).indexOf(j);
        decrypted += matrix[row].charAt(i);
      }
    }
    return decrypted;
  }

  public static void main(String[] args) {
    int choice;
    Integer[] key;
    Scanner scanner = new Scanner(System.in);
    String message = "";
    RowColumnCipher rowColumnCipher = new RowColumnCipher();
    System.out.print("1. Encrypt Message\n2. Decrypt Message\nEnter your choice: ");
    choice = scanner.nextInt();
    if (choice == 1) {
      scanner.nextLine();
      System.out.print("Enter the message to encrypt: ");
      message = scanner.nextLine();
      System.out.print("Enter key: ");
      String[] temp = scanner.nextLine().split(" ");
      key = new Integer[temp.length];
      for(int i = 0; i < temp.length; i++) key[i] = Integer.parseInt(temp[i]);
      rowColumnCipher.setKey(key);
      System.out.println("The encrypted message is: ");
      System.out.println(rowColumnCipher.encryptMessage(message));
    }
    else {
      scanner.nextLine();
      System.out.print("Enter the message to decrypt: ");
      message = scanner.nextLine();
      System.out.print("Enter key: ");
      String[] temp = scanner.nextLine().split(" ");
      key = new Integer[temp.length];
      for(int i = 0; i < temp.length; i++) key[i] = Integer.parseInt(temp[i]);
      rowColumnCipher.setKey(key);
      System.out.println("The decrypted message is: ");
      System.out.println(rowColumnCipher.decryptMessage(message));
    }
    scanner.close();
  }
}
