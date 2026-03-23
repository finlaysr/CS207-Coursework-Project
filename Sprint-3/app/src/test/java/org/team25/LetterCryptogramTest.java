/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LetterCryptogramTest {

  LetterCryptogram letterCryptogram = new LetterCryptogram();

  // Check phrase is fully encrypted
  @Test
  void encryptionHappensToGivenPhrase() {
    System.out.println("Phrase : " + letterCryptogram.getPhrase());
    System.out.println("Encrypted : " + letterCryptogram.getEncryptedPhrase());
    System.out.println("Task 1 - Checking if encryption happens to the provided phrase fully");
    assertEquals(
        letterCryptogram.getPhrase().length(), letterCryptogram.getEncryptedPhrase().size());
  }

  // Check phrase is correctly encrypted to base 13
  @Test
  void encrpytionHappensToBase13() {
    System.out.println("Phrase : " + letterCryptogram.getPhrase());
    System.out.println("Encrypted : " + letterCryptogram.getEncryptedPhrase());
    System.out.println("Task 2 - Checking if the encryption happens to every phrase to base 13");

    for (int i = 0; i < letterCryptogram.getPhrase().length(); i++) {
      String currentEncrypted = letterCryptogram.getEncryptedPhrase().get(i);
      assertEquals(
          letterCryptogram.getPhrase().charAt(i),
          letterCryptogram.getPlainLetter(currentEncrypted.charAt(0)));
    }
  }
}
