/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

/*Imports Required */
import java.util.LinkedHashMap;
import java.util.Map;

/** This is the game class which acts as the controller/engine for the overall system */
public class Game {

  // instance variables - still to decide the data types of the variables
  private Cryptogram playerGameMapping; // the current cryptogram that the player is playing
  private Player currentPlayer; // the current player playing the game

  // Key: Encrypted, Value: Guess
  private LinkedHashMap<Character, Character> guesses = new LinkedHashMap<>();

  // constructor for Game, empty for now
  // initialise to empty just now
  public Game() {
    this.playerGameMapping = null;
    this.currentPlayer = null;
    this.guesses = new LinkedHashMap<>();
  }

  /**
   * User story 1: As a player I want to be able to generate a cryptogram When the game starts, a
   * crcyptogram
   */
  public void generateCryptogram(boolean isLetterCrypto) {
    // allow the user to choice if they would want letter to letter or number to letter
    // check what cryptogram to generate
    if (isLetterCrypto) {
      LetterCryptogram playerGameMapping = new LetterCryptogram();
      guesses.clear();

      System.out.println("Letter cryptogram created!");
      playerGameMapping.Cryptogram(); // Generates the cryptogram and prints the statement
      playerGameMapping.Show(); // Shows the hashmap connections
    } else {
      playerGameMapping = new NumberCryptogram();
      guesses.clear();
      NumberCryptogram playerGameMapping = new NumberCryptogram();
      System.out.println("Number cryptogram created!");
      playerGameMapping.Cryptogram(); // Generates the cryptogram and prints the statement
      playerGameMapping.Show(); // shows hashmap connections
    }
  }

  /**
   * User story 2: As a player I want to be able to enter a letter so I can solve the cryptogram.
   */

  // enter a letter and ensure its valid
  public boolean enterLetter(Character guess, Character encrypted) {
    if (!Character.isLetter(guess)) {
      System.out.println("Guess must be a letter");
      return false;
    }

    if (guesses.containsValue(guess)) {
      System.out.println("You already guessed that letter");
      return false;
    }

    guesses.put(encrypted, guess);
    return true;
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

  public Map<Character, Character> getGuessStack() {
    return guesses;
  }

  public void viewFrequencies() {}

  public void saveGame() {}

  public void loadGame() {}

  public void showSolution() {}
}
