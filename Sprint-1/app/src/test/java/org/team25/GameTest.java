/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GameTest {
  @Test
  void testGuessStackEmpty() {
    Game game = new Game();
    assertEquals(0, game.getGuessStack().size());
  }

  @Test
  void testEnterLetter() {
    Game game = new Game();
    game.enterLetter('a', "b");
    assertEquals(1, game.getGuessStack().size());
  }

  @Test
  void testGetLastGuess() {
    Game game = new Game();
    game.enterLetter('a', "g");
    game.enterLetter('b', "h");
    game.enterLetter('c', "i");
    assertEquals(3, game.getGuessStack().size());
    assertEquals('c', game.getGuessStack().remove("i"));
    assertEquals('b', game.getGuessStack().remove("h"));
    assertEquals('a', game.getGuessStack().remove("g"));
    assertTrue(game.getGuessStack().isEmpty());
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
  }

  @Test
  void testUndoGuessStackEmpty() {
    Game game = new Game();
    game.undoLetter();
    assertEquals(0, game.getGuessStack().size());
  }

  @Test
  void testGenLetterCryptogram() {
    Game game = new Game();
    game.generateCryptogram(true);
    System.out.println(game.getGuessStack());
    assertTrue(game.isLetterCrypto());
  }

  @Test
  void testGenNumberCryptogram() {
    Game game = new Game();
    game.generateCryptogram(false);
    assertFalse(game.isLetterCrypto());
  }
}
