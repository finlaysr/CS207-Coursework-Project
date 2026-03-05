/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

/*Imports Required */
import java.util.Scanner;

/** This is the game class which acts as the controller/engine for the overall system */
public class Game {

  // instance variables- still to decdie the data types of the variables
  private Cryptogram playerGameMapping; // the current cryptogram that the player is playing
  private Player currentPlayer; // the current player playing the game

  // constructor for Game, empty for now
  // initialse to empty just now
  public Game() {
    this.playerGameMapping = null;
    this.currentPlayer = null;
  }

  /**
   * User story 1: As a player I want to be able to generate a cryptogram When the game starts, a
   * crcyptogram
   */
  public void generateCryptogram(boolean isLetterCrypto) {
    // allow the user to choice if they would want letter to letter or number to letter
    // check what cryptogram to generate
    if (isLetterCrypto) {
      playerGameMapping = new LetterCryptogram();
      System.out.println("Letter cryptogram created!");
    } else {
      playerGameMapping = new NumberCryptogram();
      System.out.println("Number cryptogram created!");
    }
  }

  /**
   * User story 2: As a player I want to be able to enter a letter so I can solve the cryptogram.
   */
  public void enterLetter() {
    boolean validGuess = false;
    while (!validGuess) {
      Scanner input = new Scanner(System.in);
      System.out.println("Enter your guess: ");

      String userGuess = input.nextLine();
      if (userGuess.length() > 1 || userGuess.length() < 1) {
        System.out.println("Guess must be a single letter");
      } else {
        validGuess = true;
      }
    }
  }

  /** User story 3: As a player I want to be able to undo a letter so I can play the cryptogram */
  public void undoLetter() {}
}
