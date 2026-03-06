/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

public class LetterCryptogram extends Cryptogram {
  public LetterCryptogram() {
    // loadPhrase is called in Cryptogram
    encryptPhrase();
  }

  // Store the string inside crpytogramAlphabet as a linked hashmap(string, string)
  // Phrase contains the string
  // Convert each element
  // Encrpyted phrase will contain the encrypted chars of each element
  private void encryptPhrase() {
    // System.out.println("Length : " + Math.pow(2, phrase.length()));
    char[] encryptedCharArray = new char[phrase.length()];
    for (int i = 0; i < phrase.length(); i++) {

      char currentChar = phrase.charAt(i);

      if (Character.isLetter(currentChar)) {
        // Move 13 places forward
        char base = Character.isLowerCase(currentChar) ? 'a' : 'A';
        // System.out.println(base);
        encryptedCharArray[i] = (char) ((currentChar - base + 13) % 26 + base);
        // System.out.print(encryptedCharArray[i]);

      } else {
        // System.out.println(1);
        if (Character.isWhitespace(currentChar)) {
          // System.out.println("True");
          encryptedCharArray[i] = ' ';
        } else {
          encryptedCharArray[i] = currentChar;
        }
        // System.out.println(2);
      }
    }
    System.out.print("Encrypted : ");
    for (char j : encryptedCharArray) {
      System.out.print(j);
    }
    System.out.println();

    String thinnedPhrase = phrase.replaceAll("\\s", "");
    String thinnedEncrypt = new String(encryptedCharArray).replaceAll("\\s", "");

    for (int i = 0; i < thinnedPhrase.length(); i++) {
      // System.out.println("i : " + i);

      // System.out.println("Adding this encrypted: " + thinnedEncrypt.charAt(i) + " -> " +
      // thinnedPhrase.charAt(i));
      cryptogramAlphabet.put(Character.toString(thinnedEncrypt.charAt(i)), thinnedPhrase.charAt(i));
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
