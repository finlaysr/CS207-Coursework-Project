/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

/*Imports Required */
import java.util.Scanner;

/** This is the game class which acts as the controller/engine for the overall system */
public class Game {

  // instance variables- still to decdie the data types of the variables
  // private payerGameMapping; //need to decide a data type
  private Player currentPlayer;

  // constructor for Game, empty for now
  public Game() {}

  /** User story 1: As a player I want to be able to generate a cryptogram */
  private void generateCryptogram() {}

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
