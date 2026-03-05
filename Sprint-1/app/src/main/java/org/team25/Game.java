/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

/*Imports Required */
import java.util.Scanner;
import java.util.Stack;

/** This is the game class which acts as the controller/engine for the overall system */
public class Game {


  // instance variables- still to decdie the data types of the variables
  private Cryptogram playerGameMapping; // the current cryptogram that the player is playing
  private Player currentPlayer; // the current player playing the game

  private Stack<Character> guessStack = new Stack<>();
  // constructor for Game, empty for now
  // initialse to empty just now
  public Game() {
    this.playerGameMapping = null;
    this.currentPlayer = null;
    this.guessStack = null;
  }

  /**
   * User story 1: As a player I want to be able to generate a cryptogram When the game starts, a
   * crcyptogram
   */
  public void generateCryptogram(boolean isLetterCrypto) {
    // allow the user to choice if they would want letter to letter or number to letter
    // check what cryptogram to generate
    if (isLetterCrypto) {
      LetterCryptogram playerGameMapping = new LetterCryptogram();
      guessStack = null;

      System.out.println("Letter cryptogram created!");
      playerGameMapping.Cryptogram(); // Generates the cryptogram and prints the statement
      playerGameMapping.Show(); // Shows the hashmap connections
    } else {
      playerGameMapping = new NumberCryptogram();
      guessStack = null;
      System.out.println("Number cryptogram created!");
    }
  }

  /**
   * User story 2: As a player I want to be able to enter a letter so I can solve the cryptogram.
   */
  //enter a letter and ensure its valid
  public boolean enterLetter(char guess) {

    if (!Character.isLetter(guess)) {
      System.out.println("Guess must be a letter");
      return false;
    }

    if (guessStack.contains(guess)) {
        System.out.println("You already guessed that letter");
      return false;
      }

      guessStack.push(guess);
    return true;
  }

  /** User story 3: As a player I want to be able to undo a letter so I can play the cryptogram */
  public void undoLetter() {

  }

  public void viewFrequencies() {

  }

  public void saveGame() {

  }

  public void loadGame() {

  }

  public void showSolution(){

 }

}
