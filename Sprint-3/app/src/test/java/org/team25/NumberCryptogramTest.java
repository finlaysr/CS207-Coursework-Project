/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NumberCryptogramTest {

  // Check phrase is fully encrypted
  @Test
  void encryptionHappensToGivenPhrase() {
    NumberCryptogram numberCryptogram = new NumberCryptogram();
    System.out.println("Phrase : " + numberCryptogram.getPhrase());
    System.out.println("Encrypted : " + numberCryptogram.getEncryptedPhrase());
    System.out.println("Task 1 - Checking if encryption happens to the provided phrase fully");
    assertEquals(
        numberCryptogram.getPhrase().length(), numberCryptogram.getEncryptedPhrase().size());
  }

  // Check phrase is correctly encrypted to base 13
  @Test
  void encryptionHappensCorrectly() {
    NumberCryptogram numberCryptogram = new NumberCryptogram();
    System.out.println("Phrase : " + numberCryptogram.getPhrase());
    System.out.println("Encrypted : " + numberCryptogram.getEncryptedPhrase());
    System.out.println("Task 2 - Checking if the encryption happens to every phrase to base 13");
    for (int i = 0; i < numberCryptogram.getPhrase().length(); i++) {
      assertEquals(
          numberCryptogram.getPhrase().charAt(i),
          numberCryptogram.getPlainLetter(numberCryptogram.getEncryptedPhrase().get(i)));
    }
  }

  // Check all letters have been encrypted to the correct offset for all possible offsets
  @Test
  void checkAllOffsets() {
    for (int i = 1; i < 27; i++) {
      NumberCryptogram n = new NumberCryptogram(i);
      int offset = i;
      assertTrue(
          n.cryptogramAlphabet.keySet().stream()
              .allMatch(
                  enc -> {
                    int encChar = Integer.parseInt(enc);
                    char origChar = n.cryptogramAlphabet.get(enc);
                    int diff = Math.abs(origChar - encChar - 'a');
                    return diff == offset || diff == 26 - offset;
                  }));
    }
  }
}
