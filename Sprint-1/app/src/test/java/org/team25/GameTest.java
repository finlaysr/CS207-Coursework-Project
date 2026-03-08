/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GameTest {
  @Test
  void testGuessStackEmpty() {
    Game game = new Game();
    assertEquals(0, game.getGuessStack().size());
  }

  // Acceptance Criteria 2.1
  @Test
  void testEnterLetter() {
    Game game = new Game();
    // Will return null if no error in entering a letter like invalid or duplicate character
    assertNull(game.enterLetter('a', "b"));
    assertEquals(1, game.getGuessStack().size());
  }

  // Acceptance Criteria 2.2
  @Test
  void testRepeatedInput() {
    Game game = new Game();
    assertNull(game.enterLetter('a', "g"));
    assertNull(game.enterLetter('b', "h"));
    assertEquals("Letter already in use!", game.enterLetter('a', "g"));
  }

  @Test
  void testUndoGuess() {
    Game game = new Game();
    game.enterLetter('a', "g");
    game.enterLetter('b', "h");
    game.enterLetter('c', "i");
    game.undoLetter();
    assertEquals(2, game.getGuessStack().size());
    assertFalse(game.getGuessStack().containsKey("i"));
    assertTrue(game.getGuessStack().containsKey("h"));
    assertTrue(game.getGuessStack().containsKey("g"));
  }

  @Test
  void testUndoGuessStackEmpty() {
    Game game = new Game();
    game.undoLetter();
    assertEquals(0, game.getGuessStack().size());
  }

  @Test
  void testGetLastGuess() {
    Game game = new Game();
    // Will return null if no error in entering a letter like invalid or duplicate character
    assertNull(game.enterLetter('a', "g"));
    assertNull(game.enterLetter('b', "h"));
    assertNull(game.enterLetter('c', "i"));
    assertEquals(3, game.getGuessStack().size());
    assertEquals('c', game.getGuessStack().remove("i"));
    assertEquals('b', game.getGuessStack().remove("h"));
    assertEquals('a', game.getGuessStack().remove("g"));
    assertTrue(game.getGuessStack().isEmpty());
  }

  @Test
  void testInvalidInput() {
    Game game = new Game();
    assertEquals("Guess must be a letter from a-z!", game.enterLetter('1', "g"));
    assertEquals("Guess must be a letter from a-z!", game.enterLetter('0', "g"));
    assertEquals("Guess must be a letter from a-z!", game.enterLetter('!', "g"));
    assertEquals("Guess must be a letter from a-z!", game.enterLetter(' ', "g"));
    assertEquals("Guess must be a letter from a-z!", game.enterLetter('@', "g"));
  }

  // Acceptance Criteria 1.1
  @Test
  void testGenLetterCryptogram() {
    Game game = new Game();
    game.generateCryptogram(true);
    System.out.println(game.getGuessStack());
    assertTrue(game.isLetterCrypto());
  }

  // Acceptance Criteria 1.2
  @Test
  void testGenNumberCryptogram() {
    Game game = new Game();
    game.generateCryptogram(false);
    assertFalse(game.isLetterCrypto());
  }
}
