/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Cryptogram {

  protected String phrase;
  // Annoyingly has to be a String, not a Char, since in NumberCryptogram 2-digit numbers will be
  // more than one character long
  protected HashMap<String, Character> cryptogramAlphabet = new HashMap<>();
  protected ArrayList<String> encryptedPhrase = new ArrayList<>();

  // Scanner the file
  public String loadPhrase() {
    String phrase = "";

    File database = new File("src/resources/sentences.txt");

    try (Scanner myReader = new Scanner(database)) {
      Random rand = new Random();
      int num = rand.nextInt(51); // random number from 1 to 50
      do {
        phrase = myReader.nextLine();
        num--;
      } while (num > 0);

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }

    phrase = phrase.toLowerCase();

    return phrase;
  }

  // Phrase contains the string
  public String getPhrase() {
    return phrase;
  }

  public HashMap<String, Character> getCryptoAlphabet() {
    return cryptogramAlphabet;
  }

  public void show() {
    System.out.println("phrase: " + phrase);
    System.out.println("Cryptogram Alphabet: " + cryptogramAlphabet);
    System.out.println("encrypted: " + getEncryptedPhrase());
    /*
    for (char i : cryptogramAlphabet.keySet()) {
        System.out.print(i);
    }*/
  }

  // encrypts the phrase using the cryptogramAlphabet
  // very complicated since the hashmap is in the opposite direction from what we need here
  // leaves special characters alone, only encrypts characters
  public ArrayList<String> getEncryptedPhrase() {
    return encryptedPhrase;
  }

  void getFrequency() {}
}
