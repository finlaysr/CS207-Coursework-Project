/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25;

import java.util.Random;

public class LetterCryptogram extends Cryptogram {
  public LetterCryptogram() {
    loadPhrase();
    Random rand = new Random();
    encryptPhrase(rand.nextInt(1, 27));
  }

  public LetterCryptogram(int offset) {
    loadPhrase();
    encryptPhrase(offset);
  }

  // Store the string inside cryptogramAlphabet as a linked hashmap(string, string)
  // Phrase contains the string
  // Convert each element
  // Encrypted phrase will contain the encrypted chars of each element
  private void encryptPhrase(int offset) {
    for (int i = 0; i < phrase.length(); i++) {
      char currentChar = phrase.charAt(i);

      if (Character.isLetter(currentChar)) {
        // Move n places forward
        char base = Character.isLowerCase(currentChar) ? 'a' : 'A';
        char encrypted = (char) (((currentChar - base + offset) % 26) + base);
        super.encryptedPhrase.add(Character.toString(encrypted));
        super.cryptogramAlphabet.put(Character.toString(encrypted), currentChar);

      } else {
        // if it's a space or punctuation just add it as it is
        super.encryptedPhrase.add(Character.toString(currentChar));
      }
    }
  }

  public Character getPlainLetter(String cryptoLetter) {
    Character revertedLetter; // This is to store the reverted phrase

    if (cryptoLetter.matches("[a-zA-Z]+")) { // Check if the current character is a letter
      revertedLetter = getCryptoAlphabet().get(cryptoLetter);

    } else { // If it is a special character
      revertedLetter = cryptoLetter.charAt(0); // Just return it
    }

    return revertedLetter;
  }
}
