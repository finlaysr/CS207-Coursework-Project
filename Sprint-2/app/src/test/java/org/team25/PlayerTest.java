/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class PlayerTest {

  /** After all delete the test file as a player as it impacts other tests from running */
  @AfterAll
  public static void teardown() {
    File test = new File("src/data/players/test.ser");
    test.delete();
  }

  /** Story 4 Senario: Player saves crypogram */
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

  /** Story 5: As a player I want to be able to load a cryptogram and continue playing */
  void loadSavedCrypto() {}

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

  // As a player I want to store my details so I can track my game play statistics
  @Test
  void testNewPlayerStartsWithZeroStats() {
    Game game = new Game();
    game.signUp("Luke");

    assertEquals(0, game.getCurrentPlayer().getNumCryptogramsPlayed());
    assertEquals(0, game.getCurrentPlayer().getNumCryptogramsCompleted());
    assertEquals(0, game.getCurrentPlayer().getTotalGuesses());
    assertEquals(0, game.getCurrentPlayer().getTotalCorrectGuesses());
  }

  // Story 9:
  // As a player I want the software to track the number of cryptograms I have successfully
  // completed
  @Test
  void testCryptogramsCompletedIncrement() {
    Game game = new Game();
    game.signUp("Luke");

    game.getCurrentPlayer().incrementCryptogramsCompleted();
    assertEquals(1, game.getCurrentPlayer().getNumCryptogramsCompleted());
  }

  // Story 10:
  // As a player I want the software to track the number of cryptograms I have played
  @Test
  void testCryptogramsPlayedIncrement() {
    Game game = new Game();
    game.signUp("Luke");

    game.getCurrentPlayer().incrementCryptogramsCompleted();

    assertEquals(1, game.getCurrentPlayer().getNumCryptogramsCompleted());
  }

  // Story 11:
  // As a player I want the software to track the number of correct guesses I have made
  // so I can see how accurate I am as a percentage of my total number of guesses
  @Test
  void testCorrectGuessUpdate() {
    Game game = new Game();
    game.signUp("Luke");

    game.getCurrentPlayer().incrementTotalGuesses();
    game.getCurrentPlayer().incrementTotalCorrectGuesses();

    assertEquals(1, game.getCurrentPlayer().getTotalGuesses());
    assertEquals(1, game.getCurrentPlayer().getTotalCorrectGuesses());
  }

  @Test
  void testIncorrectGuessUpdates() {
    Game game = new Game();
    game.signUp("Luke");

    game.getCurrentPlayer().incrementTotalGuesses();
    assertEquals(1, game.getCurrentPlayer().getTotalGuesses());
    assertEquals(0, game.getCurrentPlayer().getTotalCorrectGuesses());
  }

  //  @Test
  //  void testAccuracyPercentageCalculation() {
  //    Game game = new Game();
  //    game.signUp("Luke");
  //
  //    game.getCurrentPlayer().setGuesses(10);
  //    game.getCurrentPlayer().setCorrectGuesses(7);
  //
  //    assertEquals(70.0, game.getCurrentPlayer().getAccuracy());
  //  }
  @Test
  void testAccuracyWithZeroGuesses() {
    Game game = new Game();
    game.signUp("Luke");

    assertEquals(0.0, game.getCurrentPlayer().getAccuracy());
  }

  // Story 12:
  // As a player I want to load my details so I can track my game play statistics
  /*
  @Test
  void testLoadPlayerDetails(){
    Game game = new Game();
    game.signUp("Luke");

    game.getCurrentPlayer().incrementCryptogramsPlayed();
    game.getCurrentPlayer().savePlayer("testPlayer.txt");

    Game newGame = new Game();
    newGame.loadPlayer("testPlayer.txt");

    assertEquals(1, newGame.getCurrentPlayer().getNumCryptogramsPlayed());
  }
  */
}
