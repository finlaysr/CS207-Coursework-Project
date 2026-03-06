/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Cryptogram {

  protected final String phrase;
  // Annoyingly has to be a String, not a Char, since in NumberCryptogram 2-digit numbers will be
  // more than one character long
  protected HashMap<String, Character> cryptogramAlphabet = new HashMap<>();
  protected char[] encryptedCharArray;

  public Cryptogram() {
    phrase = loadPhrase();
  }

  // Scanner the file
  public String loadPhrase() {
    String phrase = "";

    File database = new File("src/resources/sentences.txt");

    try (Scanner myReader = new Scanner(database)) {
      Random rand = new Random();
      int num = rand.nextInt(51);
      do {
        phrase = myReader.nextLine();
        num--;
      } while (num >= 0);

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }

    System.out.println("phrase: " + phrase);
    return phrase;
  }

  // Phrase contains the string
  public String getPhrase() {
    return phrase;
  }

  public char[] getEncryptedCharArray() {
    return encryptedCharArray;
  }

  public HashMap<String, Character> getCryptoAlphabet() {
    return cryptogramAlphabet;
  }

  void show() {
    System.out.println(cryptogramAlphabet);
    System.out.println("keyset" + cryptogramAlphabet.keySet());
    /*
    for (char i : cryptogramAlphabet.keySet()) {
        System.out.print(i);
    }*/
  }

  void getFrequency() {}
}
