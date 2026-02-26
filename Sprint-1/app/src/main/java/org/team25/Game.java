/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.tem25;
import java.util.Scanner;

public class Game {

    private generateCryptogram() {
        hash
    }

    public void enterLetter() {
        boolean validGuess = false;
        while (!validGuess) {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter your guess: ");

            String userGuess = input.nextLine();
            if (userGuess.length() > 1 || userGuess.length() < 1) {
                System.out.println("Guess must be a single letter");
            }
            else {
                validGuess = true;
            }
        }
        
    }

    public void undoLetter() {

    }

}
