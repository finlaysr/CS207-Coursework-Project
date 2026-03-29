/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25;

/*Imports Required */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;

/** This is the game class which acts as the controller/engine for the overall system */
public class Game {
  private Player currentPlayer; // the current player playing the game
  private Players players;

  // Stores user guesses. Key: Encrypted, Value: Guess

  /** Constructor for the game class, just initialises everything */
  public Game() {
    players = new Players();
    currentPlayer = null;
  }

  public void shutdown() {
    players.saveAllData();
  }

  // need to modify for setting current game mapping
  // Returns error if exists, else null on success
  public String signUp(String username) {
    if (username.isEmpty()) {
      return "Username cannot be empty!";
    }
    if (!username.chars().allMatch(Character::isLetterOrDigit)) {
      return "Username must contain only letters and numbers!";
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
    if (isLetterCrypto) {
      // Generates the cryptogram and prints the statement
      currentPlayer.setCurrentCryptogram(new LetterCryptogram());
      System.out.println("Letter cryptogram created!");
    } else {
      // Generates the cryptogram and prints the statement
      currentPlayer.setCurrentCryptogram(new NumberCryptogram());
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

    if (currentPlayer.getCurrentCryptogram().getGuesses().containsValue(guess)) {
      if (currentPlayer.getCurrentCryptogram().getGuesses().containsKey(encrypted)
          && currentPlayer.getCurrentCryptogram().getGuesses().get(encrypted).equals(guess)) {
        return null; // letter entered = current letter so it's fine
      }
      return "Letter already in use!";
    }

    // If guess valid enter it and return null
    currentPlayer.getCurrentCryptogram().getGuesses().put(encrypted, guess);
    currentPlayer.incrementTotalGuesses();

    if (currentPlayer.getCurrentCryptogram().getCryptoAlphabet().containsKey(encrypted)
        && currentPlayer
            .getCurrentCryptogram()
            .getCryptoAlphabet()
            .get(encrypted)
            .equals(guess)) { // If the guess is correct, increment total correct guesses
      currentPlayer.incrementTotalCorrectGuesses();
    }

    return null;
  }

  /** User story 3: As a player I want to be able to undo a letter so I can play the cryptogram */
  public void undoLetter() {
    if (currentPlayer.getCurrentCryptogram().getGuesses().isEmpty()) {
      System.out.println("There are no guesses to be undone");
    } else {
      currentPlayer
          .getCurrentCryptogram()
          .getGuesses()
          .remove(currentPlayer.getCurrentCryptogram().getGuesses().lastEntry().getKey());
    }
    System.out.println("Guesses after: " + currentPlayer.getCurrentCryptogram().getGuesses());
  }

  public LinkedHashMap<String, Character> getGuessStack() {
    return currentPlayer.getCurrentCryptogram().getGuesses();
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

  public HashMap<String, Integer> viewFrequencies() {
    return currentPlayer.getCurrentCryptogram().getFrequency();
  }

  public String showSolution() {
    String phrase = currentPlayer.getCurrentCryptogram().getPhrase();
    currentPlayer.setCurrentCryptogram(null);

    return phrase;
  }

  public boolean checkWin() {
    if (currentPlayer.getCurrentCryptogram().getGuesses().containsValue(null)) {
      return false;
    }

    for (String encrypted : currentPlayer.getCurrentCryptogram().getCryptoAlphabet().keySet()) {
      // check input is equal to correct value
      if (!currentPlayer.getCurrentCryptogram().getGuesses().containsKey(encrypted)) {
        return false; // If guess not entered for that letter
      }
      if (!currentPlayer
          .getCurrentCryptogram()
          .getGuesses()
          .get(encrypted)
          .equals(currentPlayer.getCurrentCryptogram().getCryptoAlphabet().get(encrypted))) {
        return false; // If any guess is incorrect or missing, the player has not won
      }
    }
    currentPlayer.incrementCryptogramsCompleted();
    currentPlayer.setCurrentCryptogram(null);
    return true; // All guesses are correct, the player has won
  }

  /**
   * Checks that the player has a current cryptogram, this will then be loaded if they do If not,
   * then they can generate a letter or number cryptogram
   *
   * @return boolean flag to tell if they have a game or not
   */
  public boolean loadGame() {
    return currentPlayer.getCurrentCryptogram() != null;
  }

  public LinkedHashMap<String, String> getPlayerStats() {
    LinkedHashMap<String, String> stats = new LinkedHashMap<>();
    stats.put("Cryptograms Completed", String.valueOf(currentPlayer.getNumCryptogramsCompleted()));
    stats.put("Cryptograms Played", String.valueOf(currentPlayer.getNumCryptogramsPlayed()));
    stats.put("Correct Guesses", String.valueOf(currentPlayer.getTotalCorrectGuesses()));
    stats.put("Total Guesses", String.valueOf(currentPlayer.getTotalGuesses()));
    if (currentPlayer.getTotalGuesses() == 0) {
      stats.put("Guess Accuracy", "NA");
    } else {
      stats.put(
          "Guess Accuracy",
          currentPlayer.getTotalCorrectGuesses() * 100 / currentPlayer.getTotalGuesses() + "%");
    }
    return stats;
  }

  /**
   * Get a sorted leaderboard of top 10 players in the game
   *
   * @return 2d array of players and scores in form [[player, score], [player, score],...]
   */
  public String[][] getLeaderboard() {
    return players.getAllPlayers().stream()
        .sorted(Comparator.comparing(Player::getNumCryptogramsCompleted).reversed())
        .limit(10)
        .map(
            player ->
                new String[] {
                  player.getUsername(), String.valueOf(player.getNumCryptogramsCompleted())
                })
        .toArray(String[][]::new);
  }

  /** Returns null if no hint has been inserted, else returns an error string */
  public String getHint() {
    String result = currentPlayer.getCurrentCryptogram().getHint();
    if (result == null) {
      return "No more hints available!";
    } else {
      // if hint will conflict with preexisting guess, then remove that guess
      String enterResult = enterLetter(result, getCryptoAlph().get(result));
      if (enterResult != null && enterResult.equals("Letter already in use!")) {
        // remove guess that's causing the problem
        getGuessStack().values().remove(getCryptoAlph().get(result));
        // enter correct guess received from hint
        enterLetter(result, getCryptoAlph().get(result));
      }

      return null;
    }
  }
}
