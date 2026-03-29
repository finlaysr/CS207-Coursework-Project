/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LetterCryptogramTest {

  // Check phrase is fully encrypted
  @Test
  void encryptionHappensToGivenPhrase() {
    LetterCryptogram letterCryptogram = new LetterCryptogram();
    System.out.println("Phrase : " + letterCryptogram.getPhrase());
    System.out.println("Encrypted : " + letterCryptogram.getEncryptedPhrase());
    System.out.println("Task 1 - Checking if encryption happens to the provided phrase fully");
    assertEquals(
        letterCryptogram.getPhrase().length(), letterCryptogram.getEncryptedPhrase().size());
  }

  // Check phrase is correctly encrypted to base 13
  @Test
  void encryptionHappensCorrectly() {
    LetterCryptogram letterCryptogram = new LetterCryptogram();
    System.out.println("Phrase : " + letterCryptogram.getPhrase());
    System.out.println("Encrypted : " + letterCryptogram.getEncryptedPhrase());
    System.out.println("Task 2 - Checking if the encryption happens to every phrase to base 13");

    for (int i = 0; i < letterCryptogram.getPhrase().length(); i++) {
      String currentEncrypted = letterCryptogram.getEncryptedPhrase().get(i);
      assertEquals(
          letterCryptogram.getPhrase().charAt(i),
          letterCryptogram.getPlainLetter(currentEncrypted));
    }
  }

  // Check all letters have been encrypted to the correct offset for all possible offsets
  @Test
  void checkAllOffsets() {
    for (int i = 1; i < 27; i++) {
      LetterCryptogram l = new LetterCryptogram(i);
      int offset = i;

      assertTrue(
          l.cryptogramAlphabet.keySet().stream()
              .allMatch(
                  enc -> {
                    char encChar = enc.charAt(0);
                    char origChar = l.cryptogramAlphabet.get(enc);
                    int diff = Math.abs(origChar - encChar);
                    return diff == offset || diff == 26 - offset;
                  }));
    }
  }
}
