/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PlayerTest {

  /** After all delete the test file as a player as it impacts other tests from running */
  @AfterEach
  void teardown() {
    File test = new File("src/data/players/test.ser");
    test.delete();
  }

  /** Story 4 Scenario: Player saves cryptogram */
  @Test
  void saveCryptogram() {
    // create new game and generate cryptogram
    Game game1 = new Game();
    game1.signUp("test");
    assertNull(game1.getCurrentPlayer().getCurrentCryptogram());
    game1.generateCryptogram(true);
    game1.shutdown();

    // create new game and save if game has been saved
    Game game2 = new Game();
    game2.logIn("test");
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
    game1.signUp("test");
    assertNull(game1.getCurrentPlayer().getCurrentCryptogram());
    game1.generateCryptogram(true);
    game1.shutdown();

    // restart game and check if player can play
    Game game2 = new Game();
    game2.logIn("test");
    assertNotNull(game2.getCurrentPlayer().getCurrentCryptogram());

    // player can enter a guess here...just to show functionality
    // test will fail if the encrypted value is not in the message
    // used V and i as assumed they are in most messages since a vowel
    assertNull(game2.enterLetter("v", 'i'));
  }

  /** User Story 5- loading cryptogram Scenario- Player has no previous game stored */
  @Test
  void loadEmptyCryptogram() {
    // create new game and tried to load with no cryptogram
    Game game1 = new Game();
    game1.signUp("test");
    assertNull(game1.getCurrentPlayer().getCurrentCryptogram());

    // try to load the game...GUI will display error message if no existing game
    assertFalse(game1.loadGame());
  }

  /** User Story 5- loading crypogram Scenario- corupt file */
  @Test
  void corruptGram() {
    Game game1 = new Game();
    game1.signUp("luke");
    game1.generateCryptogram(true);
    game1.shutdown();

    // delete file to simulate corruption
    File test = new File("src/data/players/luke.ser");
    test.delete();

    // then try and login...wont be allowed to as their data is corrupt and therefore lost
    Game game2 = new Game();
    assertEquals("Player not found! Please sign up first.", game2.logIn("luke"));
  }

  /**
   * Story 8: Store my details so I can track my game play statistics Senario: Given a game is
   * shutdown, details are stored in a file, player could log in again in new game
   */
  @Test
  void testStorePlayerName() {
    Game game = new Game();
    game.signUp("test");
    game.shutdown();

    // create new game to save if the player was saved to the file
    // then should be able to login in a new run of the game
    Game game1 = new Game();
    assertNull(game1.logIn("test"));
  }

  /**
   * Story 9: // As a player I want the software to track the number of cryptograms I have
   * successfully
   */
  @Test
  void testCryptogramsCompletedIncrement() {
    Game game = new Game();
    game.signUp("test");

    // initially, they will have 0 cryptograms completed
    // The point of this test shows the stat is there... will only increment when game complete
    assertEquals(0, game.getCurrentPlayer().getNumCryptogramsCompleted());
  }

  /**
   * Story 10: As a player I want the software to track the number of cryptograms I have played
   * Scenario: new cryptogram played
   */
  @Test
  void testCryptogramsPlayedIncrement() {
    Game game = new Game();
    game.signUp("test");

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
    game.signUp("test");

    // generate new game and check current number played
    game.generateCryptogram(true);
    assertEquals(1, game.getCurrentPlayer().getNumCryptogramsPlayed());
    game.shutdown();

    // load game and login, check number of games doesn't increase
    Game game1 = new Game();
    game.logIn("test");
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
    game.signUp("test");
    game.generateCryptogram(true);

    // using knowledge of a correct encryption
    game.enterLetter("v", 'i');

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
    game.signUp("test");
    game.generateCryptogram(true);

    // using knowledge of a correct encryption
    game.enterLetter("v", 'r');

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
    game.signUp("test");
    game.generateCryptogram(true);
    game.shutdown();

    Game game1 = new Game();
    game1.logIn("test");

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
    game.signUp("luke");
    game.shutdown();

    // delete file to simulate corruption
    File test = new File("src/data/players/luke.ser");
    test.delete();

    // try and login...player has been deleted and stats lost.. GUI will say no player exists
    Game game1 = new Game();
    assertEquals("Player not found! Please sign up first.", game1.logIn("luke"));
  }

  /**
   * Story 12: As a player I want to load my details so I can track my game play statistics
   * Scenario: error loading player details as they don't exist
   */
  @Test
  void dontExist() {
    Game game = new Game();
    assertEquals("Player not found! Please sign up first.", game.logIn("test"));
  }
}
