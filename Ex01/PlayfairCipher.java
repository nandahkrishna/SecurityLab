import java.util.*;

class Index {
  public int i = 0, j = 0;

	public void set(int i, int j) {
		this.i = i;
		this.j = j;
	}
}

class PlayfairCipher {
  private String key;
  private char[][] table;

  public PlayfairCipher() {
    this.key = "HELLO";
    this.table = generateTable(this.key);
  }

  public PlayfairCipher(String key) {
    this.key = key.toUpperCase();
    this.table = generateTable(this.key);
  }

  public void setKey(String key) {
    this.key = key.toUpperCase();
    this.table = generateTable(this.key);
  }

  private char[][] generateTable(String key) {
		char[][] table = new char[5][5];
    String sequence = "";
    for(int i = 0; i < key.length(); i++) {
      char c = key.charAt(i);
      if(sequence.indexOf(c) == -1 && c != 'J') sequence += String.valueOf(c);
    }
    for(int i = 'A'; i <= 'Z'; i++) {
      if(sequence.indexOf(i) == -1 && i != 'J') sequence += String.valueOf((char) i);
    }
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				table[i][j] = sequence.charAt(i * 5 + j);
			}
		}
		return table;
  }

  public Index getTableIndex(char c) {
		Index result = new Index();
		if(c == 'J') {
			return getTableIndex('I');
		}
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(table[i][j] == c) {
					result.set(i, j);
					return result;
				}
			}
		}
		return result;
  }
  
  public void showTable() {
    System.out.println("Table:\n---------------------");
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				System.out.print("| "+ this.table[i][j] + " ");
			}
			System.out.println("|\n---------------------");
		}
	}

  private ArrayList<char[]> splitText(String input) {
    int len = input.length();
    Boolean flag = false;
    ArrayList<char[]> splitText = new ArrayList<char[]>();
    for(int i = 0; i < len - 1; i++) {
      if(input.charAt(i) == input.charAt(i + 1)) {
        input = input.substring(0, i + 1) + "X" + input.substring(i + 1);
        len++;
        flag = true;
      }
    }
    if(len % 2 == 1) {
      input += "X";
      len++;
      flag = true;
    }
    if(flag) System.out.println("Processed Input: " + input);
		for(int i = 0; i < len; i++) {
			if(i % 2 == 0) {
				char[] temp = {input.charAt(i), input.charAt(i + 1)};
				splitText.add(temp);
			}
		}
		return splitText;
  }

	public String encryptText(String plainText) {
    String cipherText = "";
    ArrayList<char[]> splits = splitText(plainText);
		for(int i = 0; i < splits.size(); i++) {
			Index i1 = getTableIndex(splits.get(i)[0]);
			Index i2 = getTableIndex(splits.get(i)[1]);
			if(i1.i == i2.i) {
				cipherText += table[i1.i][(i1.j + 1) % 5];
				cipherText += table[i2.i][(i2.j + 1) % 5];
			}
			else if(i1.j == i2.j) {
				cipherText += table[(i1.i + 1) % 5][i1.j];
				cipherText += table[(i2.i + 1) % 5][i2.j];
			}
			else {
				cipherText += table[i1.i][i2.j];
				cipherText += table[i2.i][i1.j];
			}
		}
		return cipherText;
	}

  public String decryptText(String cipherText) {
    String plainText = "";
    ArrayList<char[]> splits = splitText(cipherText);
		for(int i = 0; i < splits.size(); i++) {
			Index i1 = getTableIndex(splits.get(i)[0]);
			Index i2 = getTableIndex(splits.get(i)[1]);
			if(i1.i == i2.i) {
				plainText += table[i1.i][(i1.j + 4) % 5];
				plainText += table[i2.i][(i2.j + 4) % 5];
			}
			else if(i1.j == i2.j) {
				plainText += table[(i1.i + 4) % 5][i1.j];
				plainText += table[(i2.i + 4) % 5][i2.j];
			}
			else {
				plainText += table[i1.i][i2.j];
				plainText += table[i2.i][i1.j];
			}
		}
		return plainText;
	}

  public static void main(String[] args) {
    String input, key;
    Scanner scan = new Scanner(System.in);
    PlayfairCipher cipher = new PlayfairCipher();
    System.out.println("Playfair Cipher\n---------------");
		System.out.print("\nInput Text: ");
		input = scan.nextLine().toUpperCase();
		System.out.print("Key: ");
    key = scan.nextLine();
    cipher.setKey(key);
    cipher.showTable();
		System.out.print("Encrypt/Decrypt? [0/1]: ");
		int choice = scan.nextInt();
		String output;
		output = (choice == 0) ? cipher.encryptText(input) : cipher.decryptText(input);
    System.out.println(output);
    scan.close();
  }
}
