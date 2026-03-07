/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

public class LetterCryptogram extends Cryptogram {
  public LetterCryptogram() {
    // loadPhrase is called in Cryptogram
    super.phrase = loadPhrase();
    encryptPhrase(13);
  }

  // Store the string inside crpytogramAlphabet as a linked hashmap(string, string)
  // Phrase contains the string
  // Convert each element
  // Encrpyted phrase will contain the encrypted chars of each element
  private void encryptPhrase(int offset) {
    // System.out.println("Length : " + Math.pow(2, phrase.length()));

    for (int i = 0; i < phrase.length(); i++) {
      char currentChar = phrase.charAt(i);

      if (Character.isLetter(currentChar)) {
        // Move 13 places forward
        char base = Character.isLowerCase(currentChar) ? 'a' : 'A';
        // System.out.println(base);
        char encrypted = (char) ((currentChar - base + offset) % 26 + base);
        super.encryptedPhrase.add(Character.toString(encrypted));
        // System.out.print(encryptedCharArray[i]);

      } else {
        // if it's a space or punctuation just add it as it is
        super.encryptedPhrase.add(Character.toString(currentChar));
      }
    }
    System.out.print("Encrypted : ");

    for (int i = 0; i < phrase.length(); i++) {
      // System.out.println("i : " + i);

      // System.out.println("Adding this encrypted: " + thinnedEncrypt.charAt(i) + " -> " +
      // thinnedPhrase.charAt(i));
      cryptogramAlphabet.put(super.encryptedPhrase.get(i), phrase.charAt(i));
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

  void getPlainLetter(char cryptoLetter) {}
}
