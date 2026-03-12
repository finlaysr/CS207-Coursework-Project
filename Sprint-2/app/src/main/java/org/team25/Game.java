/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

/*Imports Required */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/** This is the game class which acts as the controller/engine for the overall system */
public class Game {
  private Cryptogram playerGameMapping; // the current cryptogram that the player is playing
  private Player currentPlayer; // the current player playing the game

  // Stores user guesses. Key: Encrypted, Value: Guess
  private LinkedHashMap<String, Character> guesses = new LinkedHashMap<>();

  /**
   * User story 1: As a player I want to be able to generate a cryptogram When the game starts, a
   * crcyptogram
   */
  public void generateCryptogram(boolean isLetterCrypto) {
    // allow the user to choice if they would want letter to letter or number to letter
    // check what cryptogram to generate
    guesses.clear();
    if (isLetterCrypto) {
      // Generates the cryptogram and prints the statement
      playerGameMapping = new LetterCryptogram();
      System.out.println("Letter cryptogram created!");
    } else {
      // Generates the cryptogram and prints the statement
      playerGameMapping = new NumberCryptogram();
      System.out.println("Number cryptogram created!");
    }
    playerGameMapping.show(); // shows hashmap connections
  }

  /**
   * User story 2: As a player I want to be able to enter a letter so I can solve the cryptogram.
   */
  // enter a letter and ensure its valid
  public String enterLetter(String encrypted, Character guess) {
    if (!Character.isLetter(guess)) {
      return "Guess must be a letter from a-z!";
    }

    if (guesses.containsValue(guess)) {
      return "Letter already in use!";
    }

    // If guess valid enter it and return null
    guesses.put(encrypted, guess);
    currentPlayer.IncrementTotalGuesses();

    if (playerGameMapping.getCryptoAlphabet().get(encrypted) == guess) {
      currentPlayer.IncrementTotalCorrectGuesses();
    }
    
    return null;
  }

  /** User story 3: As a player I want to be able to undo a letter so I can play the cryptogram */
  public void undoLetter() {
    System.out.println("Guesses before: " + guesses);
    if (guesses.isEmpty()) {
      System.out.println("There are no guesses to be undone");
    } else {
      guesses.remove(guesses.lastEntry().getKey());
    }
    System.out.println("Guesses after: " + guesses);
  }

  public LinkedHashMap<String, Character> getGuessStack() {
    return guesses;
  }

  /**
   * An accesor method that will tell the GUI what type of cyptogram is in the game
   *
   * @return true: this is a letter-letter cipher false: this is a letter-number cipher
   */
  public boolean isLetterCrypto() {
    return playerGameMapping instanceof LetterCryptogram;
  }

  /** Get mappings from encrypted to unencrypted */
  public HashMap<String, Character> getCryptoAlph() {
    return playerGameMapping.getCryptoAlphabet();
  }

  /** Return the entire encrypted phrase */
  public ArrayList<String> getEncryptedPhrase() {
    return playerGameMapping.getEncryptedPhrase();
  }

  public void viewFrequencies() {}

  public void saveGame() {}

  public void loadGame() {}

  public void showSolution() {}
}
