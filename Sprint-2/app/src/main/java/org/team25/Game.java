/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

/*Imports Required */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/** This is the game class which acts as the controller/engine for the overall system */
public class Game {
  private Player currentPlayer; // the current player playing the game
  private Players players;

  // Stores user guesses. Key: Encrypted, Value: Guess
  private LinkedHashMap<String, Character> guesses;

  /** Constructor for the game class, just initialises everything */
  public Game() {
    players = new Players();
    guesses = new LinkedHashMap<>();
    currentPlayer = null;
  }

  public void shutdown() {
    players.saveAllData();
  }

  //need to modify for setting current game mapping
  // Returns error if exists, else null on success
  public String signUp(String username) {
    if (username.isEmpty()) {
      return "Username cannot be empty!";
    }
    if (!username.chars().allMatch(Character::isLetter)) {
      return "Username must contain only letters!";
    }
    Player found = players.findPlayer(username);
    if (found != null) {
      return "Player already exists! Please log in or select a different username.";
    }

    Player newPlayer = new Player(username);
    players.addPlayer(newPlayer);
    currentPlayer = newPlayer;
    return null; // if successful return null (no error)
  }

  // Returns error if exists, else null on success
  public String logIn(String username) {
    if (username.isEmpty()) {
      return "Username cannot be empty!";
    }
    Player found = players.findPlayer(username);
    if (found == null) {
      return "Player not found! Please sign up first.";
    }

    currentPlayer = found;

    return null; // if successful return null (no error)
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

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
      currentPlayer.setCurrentCryptogram(new LetterCryptogram());
      System.out.println("Letter cryptogram created!");
    } else {
      // Generates the cryptogram and prints the statement
      currentPlayer.setCurrentCryptogram(new Cryptogram());
      System.out.println("Number cryptogram created!");
    }
    currentPlayer.getCurrentCryptogram().show(); // shows hashmap connections
    currentPlayer.incrementCryptogramsPlayed();
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
    currentPlayer.incrementTotalGuesses();

    if (currentPlayer.getCurrentCryptogram()
        .getCryptoAlphabet()
        .get(encrypted)
        .equals(guess)) { // If the guess is correct, increment total correct guesses
      currentPlayer.incrementTotalCorrectGuesses();
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
    return currentPlayer.getCurrentCryptogram() instanceof LetterCryptogram;
  }

  /** Get mappings from encrypted to unencrypted */
  public HashMap<String, Character> getCryptoAlph() {
    return currentPlayer.getCurrentCryptogram().getCryptoAlphabet();
  }

  /** Return the entire encrypted phrase */
  public ArrayList<String> getEncryptedPhrase() {
    return currentPlayer.getCurrentCryptogram().getEncryptedPhrase();
  }

  public void viewFrequencies() {}

  public void showSolution() {}

  public boolean checkWin() {
    if (guesses.containsValue(null)) {
      return false;
    }
    for (String encrypted : currentPlayer.getCurrentCryptogram().getCryptoAlphabet().keySet()) {
      if (!guesses.get(encrypted).equals(currentPlayer.getCurrentCryptogram().getCryptoAlphabet().get(encrypted))) {
        return false; // If any guess is incorrect or missing, the player has not won
      }
    }
    currentPlayer.incrementCryptogramsCompleted();
    return true; // All guesses are correct, the player has won
  }

  /**
   * Checks that the player has a current cryptogram, this will then be loaded if they do
   * if not then they can generate a letter or numver cryptogram
   * @return boolean flag to tell if they have a game or not
   */
  public boolean loadGame() {
    if (currentPlayer.getCurrentCryptogram() != null) {
      return true;
    } else return false;
  }

}

