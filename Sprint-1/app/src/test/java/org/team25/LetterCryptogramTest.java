package org.team25;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class LetterCryptogramTest {

  LetterCryptogram letterCryptogram = new LetterCryptogram();

  @Test
  void encryptionHappensToGivenPhrase() {
    System.out.println("Phrase : " + letterCryptogram.phrase);
    System.out.println("Encrypted : " + letterCryptogram.encryptedPhrase);
    System.out.println("Task 1 - Checking if encryption happens to the provided phrase fully");
    assertEquals(letterCryptogram.phrase.length(), letterCryptogram.encryptedPhrase.size());
  }

  @Test
  void encrpytionHappensToBase13(){
    System.out.println("Phrase : " + letterCryptogram.phrase);
    System.out.println("Encrypted : " + letterCryptogram.encryptedPhrase);
    System.out.println("Task 2 - Checking if the encryption happens to every phrase to base 13");

    for(int i = 0; i < letterCryptogram.phrase.length(); i++){
      String currentEncrypted = letterCryptogram.encryptedPhrase.get(i);
      assertEquals(letterCryptogram.phrase.charAt(i), letterCryptogram.getPlainLetter(currentEncrypted.charAt(0)));
    }
  }

}
