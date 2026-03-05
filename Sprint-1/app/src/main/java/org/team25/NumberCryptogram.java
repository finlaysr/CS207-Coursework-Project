/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25; /* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class NumberCryptogram extends Cryptogram {
  LinkedHashMap<Character, Character> cryptogramAlphabet = new LinkedHashMap<>();

  void Cryptogram() { // Scanner the file
    // Store the string inside crpytogramAlphabet as a linked hashmap(string, string)
    // Convert each element
    String phrase = "";

    File database = new File("src/resources/test.txt");

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
    // System.out.println("Length : " + Math.pow(2, phrase.length()));
    int[] encryptedIntArray = new int[phrase.length() * 10];
    for (int i = 0; i < phrase.length(); i++) {

      char currentChar = phrase.charAt(i);

      if (Character.isLetter(currentChar)) {
        // Move 13 places forward
        char base = Character.isLowerCase(currentChar) ? 'a' : 'A';
        // System.out.println(base);
        encryptedIntArray[i] = (currentChar - base + 13) % 26;
        // System.out.print(encryptedCharArray[i]);

      } else {
        // System.out.println(1);
        if (Character.isWhitespace(currentChar)) {
          // System.out.println("True");
          encryptedIntArray[i] = ' ';
        } else {
          encryptedIntArray[i] = currentChar;
        }
        // System.out.println(2);
      }

      System.out.print("Encrypted : ");
      for (int j : encryptedIntArray) {
        System.out.print(j + " ");
      }
      System.out.println();
    }
  }
}
