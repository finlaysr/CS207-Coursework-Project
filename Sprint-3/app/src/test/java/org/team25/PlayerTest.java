/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
  // random test username
  private final String testUsername = "sdAUIDefgbaAUsrgAhfeai";

  /** After all delete the test file as a player as it impacts other tests from running */
  @AfterEach
  void teardown() {
    File test =
        new File(
            "src"
                + File.separator
                + "data"
                + File.separator
                + "players"
                + File.separator
                + testUsername
                + ".ser");
    test.delete();
  }

  /** Story 4 Scenario: Player saves cryptogram */
  @Test
  void saveCryptogram() {
    // create new game and generate cryptogram
    Game game1 = new Game();
    game1.signUp(testUsername);
    assertNull(game1.getCurrentPlayer().getCurrentCryptogram());
    game1.generateCryptogram(true);
    game1.shutdown();

    // create new game and save if game has been saved
    Game game2 = new Game();
    game2.logIn(testUsername);
    assertNotNull(game2.getCurrentPlayer().getCurrentCryptogram());
  }

  /**
   * Story 5: As a player I want to be able to load a cryptogram and continue playing Scenario:
   * player loads their saved cryptogram game, can enter input
   */
  @Test
  void loadSavedCrypto() {
    // create new game and generate cryptogram
    Game game1 = new Game();
    game1.signUp(testUsername);
    assertNull(game1.getCurrentPlayer().getCurrentCryptogram());
    game1.generateCryptogram(true);
    Cryptogram oldCrypto = game1.getCurrentPlayer().getCurrentCryptogram();
    game1.shutdown();

    // restart game and check if player can play
    Game game2 = new Game();
    game2.logIn(testUsername);
    Cryptogram newCrypto = game2.getCurrentPlayer().getCurrentCryptogram();
    assertNotNull(newCrypto);

    assertEquals(oldCrypto.getPhrase(), newCrypto.getPhrase());

    // player can enter a guess here...just to show functionality
    // enters a random key from the hashmap into the cryptogram as a guess
    assertNull(game2.enterLetter(game2.getCryptoAlph().keySet().iterator().next(), 't'));
  }

  /** User Story 5- loading cryptogram Scenario- Player has no previous game stored */
  @Test
  void loadEmptyCryptogram() {
    // create new game and tried to load with no cryptogram
    Game game1 = new Game();
    game1.signUp(testUsername);
    assertNull(game1.getCurrentPlayer().getCurrentCryptogram());

    // try to load the game...GUI will display error message if no existing game
    assertFalse(game1.loadGame());
  }

  /** User Story 5- loading crypogram Scenario- corupt file */
  @Test
  void corruptGram() {
    Game game1 = new Game();
    game1.signUp(testUsername);
    game1.generateCryptogram(true);
    game1.shutdown();

    // delete file to simulate corruption
    teardown();

    // then try and login...wont be allowed to as their data is corrupt and therefore lost
    Game game2 = new Game();
    assertEquals("Player not found! Please sign up first.", game2.logIn(testUsername));
  }

  /**
   * Story 8: Store my details so I can track my game play statistics Senario: Given a game is
   * shutdown, details are stored in a file, player could log in again in new game
   */
  @Test
  void testStorePlayerName() {
    Game game = new Game();
    game.signUp(testUsername);
    game.shutdown();

    // create new game to save if the player was saved to the file
    // then should be able to login in a new run of the game
    Game game1 = new Game();
    assertNull(game1.logIn(testUsername));
  }

  /**
   * Story 9: // As a player I want the software to track the number of cryptograms I have
   * successfully Check if count increases after successful completion of a cryptogram
   */
  @Test
  void testCryptogramsCompletedSuccessfully() {
    Game game = new Game();
    game.signUp(testUsername);
    game.generateCryptogram(true);

    // initially, they will have 0 cryptograms completed
    // The point of this test shows the stat is there... will only increment when game complete
    assertEquals(0, game.getCurrentPlayer().getNumCryptogramsCompleted());

    game.getCryptoAlph()
        .keySet()
        .forEach(enc -> game.enterLetter(enc, game.getCryptoAlph().get(enc)));

    assertTrue(game.checkWin());
    assertEquals(1, game.getCurrentPlayer().getNumCryptogramsCompleted());
  }

  /**
   * Story 9: // As a player I want the software to track the number of cryptograms I have
   * successfully Check if count does not increase if not all letters filled in
   */
  @Test
  void testCryptogramsCompletedEmptyLetter() {
    Game game = new Game();
    game.signUp(testUsername);
    game.generateCryptogram(true);

    // initially, they will have 0 cryptograms completed
    // The point of this test shows the stat is there... will only increment when game complete
    assertEquals(0, game.getCurrentPlayer().getNumCryptogramsCompleted());

    game.getCryptoAlph().keySet().stream()
        .skip(1)
        .forEach(enc -> game.enterLetter(enc, game.getCryptoAlph().get(enc)));

    assertFalse(game.checkWin());
    assertEquals(0, game.getCurrentPlayer().getNumCryptogramsCompleted());
  }

  /**
   * Story 9: // As a player I want the software to track the number of cryptograms I have
   * successfully Check if count does not increase if all invalid letters entered
   */
  @Test
  void testCryptogramsCompletedInvalidLetter() {
    Game game = new Game();
    game.signUp(testUsername);
    game.generateCryptogram(true);

    // initially, they will have 0 cryptograms completed
    // The point of this test shows the stat is there... will only increment when game complete
    assertEquals(0, game.getCurrentPlayer().getNumCryptogramsCompleted());

    String[] encryptedValues = game.getCryptoAlph().keySet().toArray(String[]::new);
    for (int i = 0; i < encryptedValues.length; i++) {
      game.enterLetter(
          encryptedValues[i],
          game.getCryptoAlph().get(encryptedValues[(i + 1) % encryptedValues.length]));
    }

    assertFalse(game.checkWin());
    assertEquals(0, game.getCurrentPlayer().getNumCryptogramsCompleted());
  }

  /**
   * Story 10: As a player I want the software to track the number of cryptograms I have played
   * Scenario: new cryptogram played
   */
  @Test
  void testCryptogramsPlayedIncrement() {
    Game game = new Game();
    game.signUp(testUsername);

    // generate new cryptogram and check that cryptograms played is 1
    game.generateCryptogram(true);
    assertEquals(1, game.getCurrentPlayer().getNumCryptogramsPlayed());
  }

  /**
   * Story 10: As a player I want the software to track the number of cryptograms played Scenario:
   * loading in a cryptogram should not change the number of cryptograms played
   */
  @Test
  void loadCryptoTest() {
    Game game = new Game();
    game.signUp(testUsername);

    // generate new game and check current number played
    game.generateCryptogram(true);
    assertEquals(1, game.getCurrentPlayer().getNumCryptogramsPlayed());
    game.shutdown();

    // load game and login, check number of games doesn't increase
    Game game1 = new Game();
    game.logIn(testUsername);
    assertTrue(game.loadGame()); // used by GUI to load game
    assertEquals(1, game.getCurrentPlayer().getNumCryptogramsPlayed());
  }

  /**
   * Story 11: As a player I want the software to track the number of correct guesses I have made
   * Scenario: Correct guess has been made
   */
  @Test
  void testCorrectGuessUpdate() {
    Game game = new Game();
    game.signUp(testUsername);
    game.generateCryptogram(true);

    // enter a correct guess
    String encrypted = game.getCryptoAlph().keySet().iterator().next();
    game.enterLetter(encrypted, game.getCryptoAlph().get(encrypted));

    assertEquals(1, game.getCurrentPlayer().getTotalGuesses());
    assertEquals(1, game.getCurrentPlayer().getTotalCorrectGuesses());
  }

  /**
   * Story 11: As a player I want the software to track the number of correct guesses I have made
   * Scenario: Incorrect guess has been made
   */
  @Test
  void incorrectGuess() {
    Game game = new Game();
    game.signUp(testUsername);
    game.generateCryptogram(true);

    // using knowledge of a correct encryption
    String encrypted = game.getCryptoAlph().keySet().iterator().next();
    String next = game.getCryptoAlph().keySet().stream().skip(1).findFirst().get();
    game.enterLetter(encrypted, game.getCryptoAlph().get(next)); // enter a invalid guess

    assertEquals(1, game.getCurrentPlayer().getTotalGuesses());
    assertEquals(0, game.getCurrentPlayer().getTotalCorrectGuesses());
  }

  /**
   * Story 12: As a player I want to load my details so I can track my game play statistics
   * Scenario: player details loaded
   */
  @Test
  void testLoadPlayerDetails() {
    Game game = new Game();
    game.signUp(testUsername);
    game.generateCryptogram(true);
    game.shutdown();

    Game game1 = new Game();
    game1.logIn(testUsername);

    // check a stat to see if player has been loaded correctly
    assertEquals(1, game1.getCurrentPlayer().getNumCryptogramsPlayed());
  }

  /**
   * Story 12: As a player I want to load my details so I can track my game play statistics
   * Scenario: error loading player details as they are corrupt
   */
  @Test
  void fileError() {
    Game game = new Game();
    game.signUp(testUsername);
    game.shutdown();

    // delete file to simulate corruption
    teardown();

    // try and login...player has been deleted and stats lost.. GUI will say no player exists
    Game game1 = new Game();
    assertEquals("Player not found! Please sign up first.", game1.logIn(testUsername));
  }

  /**
   * Story 12: As a player I want to load my details so I can track my game play statistics
   * Scenario: error loading player details as they don't exist
   */
  @Test
  void dontExist() {
    Game game = new Game();
    assertEquals("Player not found! Please sign up first.", game.logIn(testUsername));
  }
}
