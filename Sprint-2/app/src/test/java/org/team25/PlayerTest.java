/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PlayerTest {

  // Story 8:
  // As a player I want to store my player name so the software can track my game play statistics
  @Test
  void testStorePlayerName() {
    Game game = new Game();
    game.signUp("Luke");
    assertEquals("Luke", game.getCurrentPlayer().getUsername());
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
  // As a player I want the software to track the number of cryptograms I have successfully completed
  @Test
  void testCryptogramsCompletedIncrement(){
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
  @Test
  void testAccuracyPercentageCalculation() {
    Game game = new Game();
    game.signUp("Luke");

    game.getCurrentPlayer().setGuesses(10);
    game.getCurrentPlayer().setCorrectGuesses(7);

    assertEquals(70.0, game.getCurrentPlayer().getAccuracy());
  }
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
