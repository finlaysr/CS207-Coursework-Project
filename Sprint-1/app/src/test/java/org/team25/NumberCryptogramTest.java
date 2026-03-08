package org.team25;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class NumberCryptogramTest {

  NumberCryptogram numberCryptogram = new NumberCryptogram();

  @Test
  void encryptionHappensToGivenPhrase() {
    System.out.println("Phrase : " + numberCryptogram.phrase);
    System.out.println("Encrypted : " + numberCryptogram.encryptedPhrase);
    System.out.println("Task 1 - Checking if encryption happens to the provided phrase fully");
    assertEquals(numberCryptogram.phrase.length(), numberCryptogram.encryptedPhrase.size());
  }

  @Test
  void encrpytionHappensToBase13(){
    System.out.println("Phrase : " + numberCryptogram.phrase);
    System.out.println("Encrypted : " + numberCryptogram.encryptedPhrase);
    System.out.println("Task 2 - Checking if the encryption happens to every phrase to base 13");
    for(int i = 0; i < numberCryptogram.phrase.length(); i++){
      assertEquals(numberCryptogram.phrase.charAt(i), numberCryptogram.getPlainLetter(numberCryptogram.encryptedPhrase.get(i)));
    }
  }

}
