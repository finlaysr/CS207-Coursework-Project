/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

public class LetterCryptogram extends Cryptogram {
  public LetterCryptogram() {
    // loadPhrase is called in Cryptogram
    loadPhrase();
    encryptPhrase(13);
  }

  // Store the string inside crpytogramAlphabet as a linked hashmap(string, string)
  // Phrase contains the string
  // Convert each element
  // Encrpyted phrase will contain the encrypted chars of each element
  private void encryptPhrase(int offset) {
    for (int i = 0; i < phrase.length(); i++) {
      char currentChar = phrase.charAt(i);

      if (Character.isLetter(currentChar)) {
        // Move 13 places forward
        char base = Character.isLowerCase(currentChar) ? 'a' : 'A';
        char encrypted = (char) ((currentChar - base + offset) % 26 + base);
        super.encryptedPhrase.add(Character.toString(encrypted));
        super.cryptogramAlphabet.put(Character.toString(encrypted), currentChar);

      } else {
        // if it's a space or punctuation just add it as it is
        super.encryptedPhrase.add(Character.toString(currentChar));
      }
    }
  }

  void compareInput(char input) {
    for (String i : cryptogramAlphabet.keySet()) {
      if (cryptogramAlphabet.get(i) == input) {
        // Correct
        System.out.println("Found it!");
      } else {
        System.out.println("Not found!");
      }
    }
  }

  public char getPlainLetter(char cryptoLetter) {
    char revertedLetter = ' '; // This is to store the reverted phrase

    if (Character.isLetter(cryptoLetter)) { // Check if the current character is a letter

      char base = Character.isLowerCase(cryptoLetter) ? 'a' : 'A';

      revertedLetter = (char) ((cryptoLetter - base - 13 + 26) % 26 + base); // Revert the phrase

    } else { // If it is a special character

      revertedLetter = cryptoLetter; // Just return it
    }

    return revertedLetter;
  }
}
