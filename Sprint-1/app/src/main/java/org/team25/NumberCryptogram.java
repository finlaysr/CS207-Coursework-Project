/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

public class NumberCryptogram extends Cryptogram {
  public NumberCryptogram() { // Scanner the file
    // loadPhrase is called in Cryptogram
    encryptPhrase(13);
  }

  // Store the string inside crpytogramAlphabet as a linked hashmap(string, string)
  // Convert each element
  // Phrase contains the string
  // Encrpyted phrase will contain the encrypted chars of each element
  private void encryptPhrase(int offset) {
    // System.out.println("Length : " + Math.pow(2, phrase.length()));
    for (int i = 0; i < phrase.length(); i++) {
      char currentChar = phrase.charAt(i);

      if (Character.isLetter(currentChar)) {
        // Move 13 places forward
        char base = Character.isLowerCase(currentChar) ? 'a' : 'A';
        int encrypted = (currentChar - base + offset) % 26;
        // System.out.println(base);
        super.encryptedPhrase.add(Integer.toString(encrypted));
        // System.out.print(encryptedCharArray[i]);

      } else {
        super.encryptedPhrase.add(String.valueOf(currentChar));
      }
    }

    System.out.println("Encrypted phrase " + super.encryptedPhrase);

    // Store inside hashmap
    for (int i = 0; i < phrase.length(); i++) {
      cryptogramAlphabet.put(super.encryptedPhrase.get(i), phrase.charAt(i));
    }
  }
}
