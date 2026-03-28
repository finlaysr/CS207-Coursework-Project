/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class GameTest {
  // random test username
  private final String testUsername = "41D17BC763FF47A3F99270b2b70da070b04e87fa";

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
    assertEquals(
        "Player not found! Please sign up first.",
        game2.logIn("notAUser")); // check invalid user login not allowed
  }

  // Test that signing up with an invalid user name won't work
  @Test
  void testInvalidUserName() {
    Game game = new Game();
    assertEquals("Username cannot be empty!", game.signUp(""));
    assertEquals(
        "Username must contain only letters and numbers!", game.signUp(testUsername + ";"));
    assertEquals(
        "Username must contain only letters and numbers!", game.signUp(testUsername + " "));
    assertEquals(
        "Username must contain only letters and numbers!", game.signUp(testUsername + "_"));
    assertEquals(
        "Username must contain only letters and numbers!", game.signUp(testUsername + "!£$"));
    assertEquals(
        "Username must contain only letters and numbers!", game.signUp(testUsername + "."));

    assertNull(game.signUp(testUsername));
    game.shutdown();

    Game game2 = new Game();
    assertEquals(
        "Player already exists! Please log in or select a different username.",
        game2.signUp(testUsername));
  }

  // User Story 6
  // Check that asking to view the solution removes the current cryptogram and changes the player
  // stats correctly
  @Test
  void testShowSolution() {
    Game game = new Game();
    assertNull(game.signUp(testUsername));

    game.generateCryptogram(true);
    game.showSolution();

    // Check cryptogram is no longer being played
    assertNull(game.getCurrentPlayer().getCurrentCryptogram());
    // Check stats have been counted correctly
    assertEquals(0, game.getCurrentPlayer().getNumCryptogramsCompleted());
    assertEquals(1, game.getCurrentPlayer().getNumCryptogramsPlayed());
  }

  // User Story 13
  // Checks that users get added to the leaderboard correctly after completing a cryptogram
  @Test
  void testLeaderBoard() {
    Game game = new Game();
    final int count = 5;
    String[][] expected = new String[count][];
    System.out.println(Arrays.deepToString(expected));

    // add a bunch of users
    for (int i = 0; i < count; i++) {
      assertNull(game.signUp(testUsername + i));

      // the first user will have completed 1 game, the second 2 games etc.
      for (int j = 0; j < i; j++) {
        game.generateCryptogram(true);

        // complete the cryptogram
        game.getCryptoAlph()
            .keySet()
            .forEach(enc -> game.enterLetter(enc, game.getCryptoAlph().get(enc)));
        assertTrue(game.checkWin());
      }
      expected[i] = new String[] {testUsername + i, String.valueOf(i)};
    }

    // Cant just compare expected and game.getLeaderboard() since the leaderboard might have some
    // users in it already so have to check every user is in the leaderboard and ordered correctly
    // manually :/

    // check every user has been entered into the leaderboard
    int pos = 999999; // used to check the users are in descending order
    for (int i = 0; i < count; i++) {
      boolean found = false;
      for (int j = 0; j < game.getLeaderboard().length; j++) {
        String[] leader = game.getLeaderboard()[j];
        if (leader[0].equals(expected[i][0]) && leader[1].equals(expected[i][1])) {
          // check the users in the leaderboard are in descending order of cryptograms completed
          assertTrue(j < pos);
          pos = j;

          found = true;
          break;
        }
      }
      assertTrue(found);
    }
  }
}
