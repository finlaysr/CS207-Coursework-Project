/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25; /* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class LetterCryptogram extends Cryptogram {
  LinkedHashMap<Character, Character> cryptogramAlphabet = new LinkedHashMap<>();

  /*** Install plugin***/

  void Cryptogram() {
    // Scanner the file
    // Store the string inside crpytogramAlphabet as a linked hashmap(string, string)
    // Convert each element
    String phrase = "";

    File database = new File("app/src/main/java/org/team25/test.txt");

    try (Scanner myReader = new Scanner(database)) {
      while (myReader.hasNextLine()) {
        phrase = myReader.nextLine();
        // System.out.println(phrase);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
    // Phrase contains the string
    // Encrpyted phrase will contain the encrypted chars of each element
    for (int j = 0; j < phrase.length(); j++) {
      char[] encryptedCharArray = new char[phrase.length() * 10];
      for (char i : phrase.toCharArray()) {
        if (Character.isLetter(i)) {
          // Move 13 places forward
          char base = Character.isLowerCase(i) ? 'a' : 'A';
          // System.out.println(base);
          encryptedCharArray[i] = (char) ((i - base + 13) % 26 + base);
          System.out.print(encryptedCharArray[i]);
          // cryptogramAlphabet.put(encryptedCharArray[i], phrase.charAt(i));
        } else {
          // System.out.println(1);
          encryptedCharArray[j] = i;
          // System.out.println(2);
        }
      }
    }
  }

  void Show() {
    for (char i : cryptogramAlphabet.keySet()) {
      System.out.print(i);
    }
  }

  void getPlainLetter(char cryptoLetter) {
    // Getting the actual letter from the encrpyted letter

  }
}
