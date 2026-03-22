/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

public class NumberCryptogram extends Cryptogram {
  public NumberCryptogram() { // Scanner the file
    // can't have numbers in a number cryptogram, so re-roll if it happens
    do {
      loadPhrase();
    } while (super.phrase.matches("^.*\\d.*"));
    encryptPhrase(13);
  }

  // Store the string inside crpytogramAlphabet as a linked hashmap(string, string)
  // Convert each element
  // Phrase contains the string
  // Encrpyted phrase will contain the encrypted chars of each element
  private void encryptPhrase(int offset) {
    for (int i = 0; i < phrase.length(); i++) {
      char currentChar = phrase.charAt(i);

      if (Character.isLetter(currentChar)) {
        // Move 13 places forward
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

  public char getPlainLetter(String cryptoNum) {
    System.out.println("CrytoNum : " + cryptoNum);
    char revertedLetter = ' '; // This is to store the reverted phrase

    if (isNumeric(cryptoNum)) {
      int temp = Integer.parseInt(cryptoNum);
      char cryptoLetter = (char) ((char) temp + 96);
      System.out.println("CrytoLetter: " + cryptoLetter);
      revertedLetter = (char) ((cryptoLetter - 'a' - 12 + 26) % 26 + 'a'); // Revert the phrase
    } else {
      revertedLetter = cryptoNum.charAt(0);
    }
    System.out.println("revertedLetter : " + revertedLetter);
    return revertedLetter;
  }
}
