/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Scanner;

public class Cryptogram implements Serializable {

  /** Stores unencrypted phrase */
  protected String phrase;

  // Annoyingly has to be a String, not a Char, since in NumberCryptogram 2-digit numbers will be
  // more than one character long
  /** Stores encrypted character mappings, key: encrypted, value: unencrypted */
  protected HashMap<String, Character> cryptogramAlphabet = new HashMap<>();

  /** Stores the current encrypted string as an arrayList * */
  protected ArrayList<String> encryptedPhrase = new ArrayList<>();

  /** This variable stores the number of hints claimed for each cryptogram * */
  private int noHint;

  /**
   * Stores guesses entered: key: encrypted, value: guess LinkedHashMap so that last input can be
   * removed using undo
   */
  private LinkedHashMap<String, Character> guesses = new LinkedHashMap<>();

  protected Integer cryptogramID;

  // Scanner the file
  public void loadPhrase() {
    File database = new File("src/resources/sentences.txt");

    try (Scanner myReader = new Scanner(database)) {
      Random rand = new Random();
      int num = rand.nextInt(39); // random number from 1 to 39
      do {
        phrase = myReader.nextLine();
        num--;
      } while (num > 0);

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
      System.exit(1); // exit if file not found
    }

    phrase = phrase.toLowerCase();
  }

  // Phrase contains the unencrypted string
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
  }

  // encrypts the phrase using the cryptogramAlphabet
  // very complicated since the hashmap is in the opposite direction from what we need here
  // leaves special characters alone, only encrypts characters
  public ArrayList<String> getEncryptedPhrase() {
    return encryptedPhrase;
  }

  /** Returns a hashmap containing the frequency of letters occurring in the encrypted phrase */
  HashMap<String, Integer> getFrequency() {
    HashMap<String, Integer> frequency = new HashMap<>();
    encryptedPhrase.forEach(
        enc -> {
          if (cryptogramAlphabet.containsKey(enc)) {
            if (frequency.containsKey(enc)) {
              frequency.put(enc, frequency.get(enc) + 1);
            } else {
              frequency.put(enc, 1);
            }
          }
        });
    return frequency;
  }

  public boolean isNumeric(String input) {
    if (input.matches(".*[^a-zA-Z0-9].*")) {
      return false;
    }
    try {
      int inNum = Integer.parseInt(input);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  public Integer getCryptogramID() {
    return cryptogramID;
  }

  public LinkedHashMap<String, Character> getGuesses() {
    return guesses;
  }

  /**
   * Used to supply the player with a random hint character that is valid If used all hints (max 5,
   * then just return)
   *
   * @return char of hint to be returned to the user
   */
  public Character getHint() {

    // variable declerations
    Random ranEncrypted = new Random();
    int encryptedAccessor =
        ranEncrypted.nextInt(cryptogramAlphabet.size()); // 1 to size of the encrpyted message
    boolean valid = false;
    String encryptedCharacter = null;

    while (!valid) {
      // check hint numbers and generate a random hint
      if (noHint >= 5) return null;

      // can now generate a random hint...get an encrypted value to then return its character pair
      encryptedCharacter = encryptedPhrase.get(encryptedAccessor - 1);

      // check if this has already been guessed, does encrpyted value already have a guess linked to
      // it
      if (guesses.containsKey(encryptedCharacter)) {
        encryptedAccessor = ranEncrypted.nextInt(cryptogramAlphabet.size());
      } else valid = true;
    }
    // once you get to here, have a valid encrpyted string value that does not already have a linked
    // guess
    noHint++;
    return cryptogramAlphabet.get(encryptedCharacter);
  }
}
