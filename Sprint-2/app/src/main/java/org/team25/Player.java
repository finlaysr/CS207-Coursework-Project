/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

public class Player {

  // fields
  private String Username;
  private int TotalGuesses;
  private int TotalCorrectGuesses;
  private int CryptogramsPlayed;
  private int CryptogramsCompleted;
  private double Accuracy;

  // player constructor
  public Player(String Username) {
    this.Username = Username;
    this.TotalGuesses = 0;
    this.TotalCorrectGuesses = 0;
    this.CryptogramsPlayed = 0;
    this.CryptogramsCompleted = 0;
    this.Accuracy = 0.0;
  }

  public void UpdateAccuracy() {
    Accuracy = (double) TotalCorrectGuesses / TotalGuesses;
  }

  public void IncrementCryptogramsPlayed() {
    CryptogramsPlayed++;
  }

  public void IncrementCryptogramsCompleted() {
    CryptogramsCompleted++;
  }

  public double GetAccuracy() {
    return Accuracy;
  }

  public int GetNumCryptogramsPlayed() {
    return CryptogramsPlayed;
  }

  public int GetNumCryptogramsCompleted() {
    return CryptogramsCompleted;
  }

  // not in the sample solution but might be needed

  public String GetUsername() {
    return Username;
  }

  public int GetTotalGuesses() {
    return TotalGuesses;
  }

  public void IncrementTotalGuesses() {
    TotalGuesses++;
    System.out.println("Total guesses: " + TotalGuesses);
  }

  public int GetTotalCorrectGuesses() {
    return TotalCorrectGuesses;
  }

  public void IncrementTotalCorrectGuesses() {
    TotalCorrectGuesses++;
    System.out.println("Total correct guesses: " + TotalCorrectGuesses);
  }
}
