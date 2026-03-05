/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Stack;

public class LetterCryptogram extends Cryptogram {
  // Key is encrypted, and Value is the original value
  LinkedHashMap<Character, Character> cryptogramAlphabet = new LinkedHashMap<>();
  Stack<Character> input = new Stack<>();

  void Cryptogram() {
    // Scanner the file
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
    char[] encryptedCharArray = new char[phrase.length()];
    for (int i = 0; i < phrase.length(); i++) {

      char currentChar = phrase.charAt(i);

      if (Character.isLetter(currentChar)) {
        // Move 13 places forward
        char base = Character.isLowerCase(currentChar) ? 'a' : 'A';
        // System.out.println(base);
        encryptedCharArray[i] = (char) ((currentChar - base + 13) % 26 + base);
        // System.out.print(encryptedCharArray[i]);

      } else {
        // System.out.println(1);
        if (Character.isWhitespace(currentChar)) {
          // System.out.println("True");
          encryptedCharArray[i] = ' ';
        } else {
          encryptedCharArray[i] = currentChar;
        }
        // System.out.println(2);
      }
    }
    System.out.print("Encrypted : ");
    for (char j : encryptedCharArray) {
      System.out.print(j);
    }
    System.out.println();

    String thinnedPhrase = phrase.replaceAll("\\s", "");
    String thinnedEncrypt = new String(encryptedCharArray).replaceAll("\\s", "");

    for (int i = 0; i < thinnedPhrase.length(); i++) {
      // System.out.println("i : " + i);

      // System.out.println("Adding this encrypted: " + thinnedEncrypt.charAt(i) + " -> " +
      // thinnedPhrase.charAt(i));
      cryptogramAlphabet.put(thinnedEncrypt.charAt(i), thinnedPhrase.charAt(i));
    }
  }

  void Show() {
    System.out.println(cryptogramAlphabet);
    System.out.println("keyset" + cryptogramAlphabet.keySet());
    /*
    for (char i : cryptogramAlphabet.keySet()) {
        System.out.print(i);
    }*/
  }

  void inputTaking() {

    Scanner userinput = new Scanner(System.in);
    String getInput = "";
    while (userinput.hasNextLine()) {
      getInput = userinput.nextLine();
    }
    input.push(getInput.charAt(0));
  }

  void compareInput() {
    int j = 0;
    for (char i : cryptogramAlphabet.keySet()) {
      if (cryptogramAlphabet.get(i) == input.get(j)) {
        // Correct
        System.out.println("Found it!");
      } else {
        System.out.println("Not found!");
      }
      j++;
    }
  }

  void getPlainLetter(char cryptoLetter) {}
}
