/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

import java.io.Serializable;

public class Player implements Serializable {

  // fields
  private String username;
  private int totalGuesses;
  private int totalCorrectGuesses;
  private int cryptogramsPlayed;
  private int cryptogramsCompleted;
  private double accuracy;
  private Cryptogram currentCryptogram;

  // player constructor
  public Player(String username) {
    this.username = username;
    this.totalGuesses = 0;
    this.totalCorrectGuesses = 0;
    this.cryptogramsPlayed = 0;
    this.cryptogramsCompleted = 0;
    this.accuracy = 0.0;
    this.currentCryptogram = null;
  }

  public void updateAccuracy() {
    accuracy = (double) totalCorrectGuesses / totalGuesses;
  }

  public void incrementCryptogramsPlayed() {
    cryptogramsPlayed++;
  }

  public void incrementCryptogramsCompleted() {
    cryptogramsCompleted++;
  }

  public double getAccuracy() {
    return accuracy;
  }

  public int getNumCryptogramsPlayed() {
    return cryptogramsPlayed;
  }

  public int getNumCryptogramsCompleted() {
    return cryptogramsCompleted;
  }

  // not in the sample solution but might be needed

  public String getUsername() {
    return username;
  }

  public int getTotalGuesses() {
    return totalGuesses;
  }

  public void incrementTotalGuesses() {
    totalGuesses++;
    System.out.println("Total guesses: " + totalGuesses);
  }

  public int getTotalCorrectGuesses() {
    return totalCorrectGuesses;
  }

  public void incrementTotalCorrectGuesses() {
    totalCorrectGuesses++;
    System.out.println("Total correct guesses: " + totalCorrectGuesses);
  }

  /**
   * Accessory method to return the current cryptogram, used by Game
   *
   * @return the current Cryptogram of the player
   */
  public Cryptogram getCurrentCryptogram() {
    return currentCryptogram;
  }

  /**
   * Settor method for curren cryptogram used for game
   *
   * @param current the cryptogram to be set
   */
  public void setCurrentCryptogram(Cryptogram current) {
    this.currentCryptogram = current;
  }

  // for tests
  public void setGuesses(int guesses) {
      this.totalGuesses = guesses;
  }

  public void setCorrectGuesses(int guessesCorrect) {
    this.totalCorrectGuesses = guessesCorrect;
  }
}


