/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25;

import java.util.Random;

public class NumberCryptogram extends Cryptogram {
  public NumberCryptogram() { // Scanner the file
    // can't have numbers in a number cryptogram, so re-roll if it happens
    do {
      loadPhrase();
    } while (super.phrase.matches("^.*\\d.*"));
    Random rand = new Random();
    encryptPhrase(rand.nextInt(0, 27));
  }

  public NumberCryptogram(int offset) { // Scanner the file
    // can't have numbers in a number cryptogram, so re-roll if it happens
    do {
      loadPhrase();
    } while (super.phrase.matches("^.*\\d.*"));
    encryptPhrase(offset);
  }

  // Store the string inside cryptogramAlphabet as a linked hashmap(string, string)
  // Convert each element
  // Phrase contains the string
  // Encrypted phrase will contain the encrypted chars of each element
  private void encryptPhrase(int offset) {
    for (int i = 0; i < phrase.length(); i++) {
      char currentChar = phrase.charAt(i);

      if (Character.isLetter(currentChar)) {
        // Move n places forward
        char base = Character.isLowerCase(currentChar) ? 'a' : 'A';
        int encrypted = (currentChar - base + offset) % 26;
        super.encryptedPhrase.add(Integer.toString(encrypted));
        super.cryptogramAlphabet.put(String.valueOf(encrypted), currentChar);

      } else {
        // if it's a space or punctuation just add it as it is
        super.encryptedPhrase.add(String.valueOf(currentChar));
      }
    }
  }

  public Character getPlainLetter(String cryptoNum) {
    Character revertedLetter; // This is to store the reverted phrase

    if (isNumeric(cryptoNum)) {
      revertedLetter = cryptogramAlphabet.get(cryptoNum);
    } else {
      revertedLetter = cryptoNum.charAt(0);
    }
    return revertedLetter;
  }
}
