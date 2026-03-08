/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

/*Imports Required */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/** This is the game class which acts as the controller/engine for the overall system */
public class Game {

  // instance variables - still to decide the data types of the variables
  private Cryptogram playerGameMapping; // the current cryptogram that the player is playing
  private Player currentPlayer; // the current player playing the game

  // Key: Encrypted, Value: Guess
  private LinkedHashMap<String, Character> guesses = new LinkedHashMap<>();

  // constructor for Game, empty for now
  // initialise to empty just now
  public Game() {
    this.playerGameMapping = null;
    this.currentPlayer = null;
  }

  /**
   * User story 1: As a player I want to be able to generate a cryptogram When the game starts, a
   * crcyptogram
   */
  public void generateCryptogram(boolean isLetterCrypto) {
    // allow the user to choice if they would want letter to letter or number to letter
    // check what cryptogram to generate
    if (isLetterCrypto) {
      // Generates the cryptogram and prints the statement
      playerGameMapping = new LetterCryptogram();
      guesses.clear();
      System.out.println("Letter cryptogram created!");
      playerGameMapping.show(); // Shows the hashmap connections

    } else {
      // Generates the cryptogram and prints the statement
      playerGameMapping = new NumberCryptogram();
      guesses.clear();
      System.out.println("Number cryptogram created!");
      playerGameMapping.show(); // shows hashmap connections
    }
  }

  /**
   * User story 2: As a player I want to be able to enter a letter so I can solve the cryptogram.
   */

  // enter a letter and ensure its valid
  public String enterLetter(Character guess, String encrypted) {
    if (!Character.isLetter(guess)) {
      return "Guess must be a letter from a-z!";
    }

    if (guesses.containsValue(guess)) {
      return "Letter already in use!";
    }

    guesses.put(encrypted, guess);
    return null;
  }

  /** User story 3: As a player I want to be able to undo a letter so I can play the cryptogram */
  public void undoLetter() {
    System.out.println("Guesses beofre: " + guesses);
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
   * @return- true: this is a letter-letter cipher false- this is a letter-number cipher
   */
  public boolean isLetterCrypto() {
    return playerGameMapping instanceof LetterCryptogram;
  }

  public HashMap<String, Character> getCryptoAlph() {
    return playerGameMapping.getCryptoAlphabet();
  }

  public ArrayList<String> getEncryptedPhrase() {
    return playerGameMapping.getEncryptedPhrase();
  }

  public void viewFrequencies() {}

  public void saveGame() {}

  public void loadGame() {}

  public void showSolution() {}
}
