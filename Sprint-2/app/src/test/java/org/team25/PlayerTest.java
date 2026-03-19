/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PlayerTest {

  // Story 8:
  // As a player I want to store my player name so the software can track my game play statistics
  @Test
  void testStorePlayerName() {
    Player player = new Player("Luke");
    assertEquals("Luke", player.getUsername());
  }

  // As a player I want to store my details so I can track my game play statistics
  @Test
  void testNewPlayerStartsWithZeroStats() {
    Player player = new Player("Luke");

    assertEquals(0, player.getNumCryptogramsPlayed());
    assertEquals(0, player.getNumCryptogramsCompleted());
    assertEquals(0, player.getTotalGuesses());
    assertEquals(0, player.getTotalCorrectGuesses());
  }

  // Story 9:
  // As a player I want the software to track the number of cryptograms I have successfully completed
  @Test
  void testCryptogramsCompletedIncrement(){
    Player player = new Player("Luke");
    player.incrementCryptogramsCompleted();
    assertEquals(1, player.getNumCryptogramsCompleted());
  }

  // Story 10:
  // As a player I want the software to track the number of cryptograms I have played
  @Test
  void testCryptogramsPlayedIncrement() {
    Player player = new Player("Luke");
    player.incrementCryptogramsPlayed();
    assertEquals(1, player.getNumCryptogramsPlayed());
  }

  // Story 11:
  // As a player I want the software to track the number of correct guesses I have made
  // so I can see how accurate I am as a percentage of my total number of guesses
  @Test
  void testCorrectGuessUpdate() {
    Player player = new Player("Luke");

    player.incrementTotalGuesses();
    player.incrementTotalCorrectGuesses();
    assertEquals(1, player.getTotalGuesses());
    assertEquals(1, player.getTotalCorrectGuesses());
  }
  @Test
  void testIncorrectGuessUpdates() {
    Player player = new Player("Luke");

    player.incrementTotalGuesses();
    assertEquals(1, player.getTotalGuesses());
    assertEquals(0, player.getTotalCorrectGuesses());
  }
  /*
  @Test
  void testAccuracyPercentageCalculation() {
    Player player = new Player("Luke");

    player.setGuesses(10);
    player.setCorrectGuesses(7);

    assertEquals(70.0, player.getAccuracy());
  }

   */
  @Test
  void testAccuracyWithZeroGuesses() {
    Player player = new Player("Luke");
    assertEquals(0.0, player.getAccuracy());
  }


}
