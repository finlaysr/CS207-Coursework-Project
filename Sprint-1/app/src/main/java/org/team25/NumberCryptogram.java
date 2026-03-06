/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

public class NumberCryptogram extends Cryptogram {
  public NumberCryptogram() { // Scanner the file
    // loadPhrase is called in Cryptogram
    encryptPhrase();
  }

  // Store the string inside crpytogramAlphabet as a linked hashmap(string, string)
  // Convert each element
  // Phrase contains the string
  // Encrpyted phrase will contain the encrypted chars of each element
  private void encryptPhrase() {
    // System.out.println("Length : " + Math.pow(2, phrase.length()));
    String thinnedPhrase = phrase.replaceAll("\\s", "");
    int[] encryptedIntArray = new int[thinnedPhrase.length()];
    for (int i = 0; i < thinnedPhrase.length(); i++) {

      char currentChar = phrase.charAt(i);

      if (Character.isLetter(currentChar)) {
        // Move 13 places forward
        char base = Character.isLowerCase(currentChar) ? 'a' : 'A';
        // System.out.println(base);
        encryptedIntArray[i] = (currentChar - base + 13) % 26;
        // System.out.print(encryptedCharArray[i]);

      } else {
        // System.out.println(1);
        if (Character.isWhitespace(currentChar)) {
          // System.out.println("True");
          encryptedIntArray[i] = ' ';
        } else {
          encryptedIntArray[i] = currentChar;
        }
        // System.out.println(2);
      }
      // System.out.print();
    }

    System.out.print("Encrypted : ");
    int k = 0; // Pointer for the encryptedIntArray

    for (int j = 0; j < phrase.length(); j++) {
      if (Character.isWhitespace(phrase.charAt(j))) {
        System.out.print("  "); // Print your spacing
      } else {
        // Only access the array and increment k if it's NOT a space
        System.out.print(encryptedIntArray[k] + " ");
        k++;
      }
    }

    System.out.println();

    // Store inside hashmap
    for (int i = 0; i < thinnedPhrase.length(); i++) {
      cryptogramAlphabet.put(Integer.toString(encryptedIntArray[i]), thinnedPhrase.charAt(i));
    }
  }
}
