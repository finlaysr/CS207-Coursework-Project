/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class GameTest {
  private final String testUsername = "testUsername";

  @AfterEach
  void teardown() {
    File test = new File("src/data/players/" + testUsername + ".ser");
    test.delete();
  }

  @Test
  void testGuessStackEmpty() {
    Game game = new Game();
    assertNull(game.signUp(testUsername));
    game.generateCryptogram(true);
    assertTrue(game.getCurrentPlayer().getCurrentCryptogram().getGuesses().isEmpty());
  }

  // Acceptance Criteria 2.1
  @Test
  void testEnterLetter() {
    Game game = new Game();
    assertNull(game.signUp(testUsername));
    game.generateCryptogram(true);
    // Will return null if no error in entering a letter like invalid or duplicate character
    assertNull(game.enterLetter("b", 'a'));
    assertEquals(1, game.getGuessStack().size());
  }

  // Acceptance Criteria 2.2
  @Test
  void testRepeatedInput() {
    Game game = new Game();
    assertNull(game.signUp(testUsername));
    game.generateCryptogram(true);
    assertNull(game.enterLetter("g", 'a'));
    assertNull(game.enterLetter("h", 'b'));
    assertEquals("Letter already in use!", game.enterLetter("i", 'a'));
  }

  // Check that undoing removes the last entered letter
  @Test
  void testUndoGuess() {
    Game game = new Game();
    assertNull(game.signUp(testUsername));
    game.generateCryptogram(true);
    game.enterLetter("g", 'a');
    game.enterLetter("h", 'b');
    game.enterLetter("i", 'c');
    game.undoLetter();
    assertEquals(2, game.getGuessStack().size());
    assertFalse(game.getGuessStack().containsKey("i"));
    assertTrue(game.getGuessStack().containsKey("h"));
    assertTrue(game.getGuessStack().containsKey("g"));
  }

  // Check nothing happens if undo on empty stack
  @Test
  void testUndoGuessStackEmpty() {
    Game game = new Game();
    assertNull(game.signUp(testUsername));
    game.generateCryptogram(true);
    game.undoLetter();
    assertEquals(0, game.getGuessStack().size());
  }

  // Test removing guesses
  @Test
  void testRemoveGuess() {
    Game game = new Game();
    assertNull(game.signUp(testUsername));
    game.generateCryptogram(true);
    // Will return null if no error in entering a letter like invalid or duplicate character
    assertNull(game.enterLetter("g", 'a'));
    assertNull(game.enterLetter("h", 'b'));
    assertNull(game.enterLetter("i", 'c'));
    assertEquals(3, game.getGuessStack().size());
    assertEquals('c', game.getGuessStack().remove("i"));
    assertEquals('b', game.getGuessStack().remove("h"));
    assertEquals('a', game.getGuessStack().remove("g"));
    assertTrue(game.getGuessStack().isEmpty());
  }

  // Check invalid inputs are blocked
  @Test
  void testInvalidInput() {
    Game game = new Game();
    assertNull(game.signUp(testUsername));
    game.generateCryptogram(true);
    assertEquals("Guess must be a letter from a-z!", game.enterLetter("g", '1'));
    assertEquals("Guess must be a letter from a-z!", game.enterLetter("g", '0'));
    assertEquals("Guess must be a letter from a-z!", game.enterLetter("g", '!'));
    assertEquals("Guess must be a letter from a-z!", game.enterLetter("g", ' '));
    assertEquals("Guess must be a letter from a-z!", game.enterLetter("g", '@'));
    assertTrue(game.getGuessStack().isEmpty());
  }

  // Acceptance Criteria 1.1
  @Test
  void testGenLetterCryptogram() {
    Game game = new Game();
    assertNull(game.signUp(testUsername));
    game.generateCryptogram(true);
    game.generateCryptogram(true);
    System.out.println(game.getGuessStack());
    assertTrue(game.isLetterCrypto());
  }

  // Acceptance Criteria 1.2
  @Test
  void testGenNumberCryptogram() {
    Game game = new Game();
    assertNull(game.signUp(testUsername));
    game.generateCryptogram(true);
    game.generateCryptogram(false);
    assertFalse(game.isLetterCrypto());
  }

  // check that user login is saved across games
  @Test
  void testLogin() {
    Game game1 = new Game();
    assertNull(game1.signUp(testUsername));
    game1.shutdown(); // saves the test user

    Game game2 = new Game();
    assertNull(game2.logIn(testUsername)); // check user was loaded
    assertNotNull(game2.logIn("notAUser")); // check invalid user login not allowed
  }
}
