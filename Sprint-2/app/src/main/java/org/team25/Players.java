package org.team25;

//packages
import java.io.BufferedReader;
import java .util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * A collection of all the players, this is what Game interacts with
 */
public class Players {

  //instacne variables
  private ArrayList<Player> allPlayers;
  private File playersFile;


  /**
   * Constructor method- creates an empty array list and loads in the player file
   */
  public Players() {
    allPlayers= fillArray();
    playersFile = new File("/home/callum/CS207/GroupProject/Fork/CS207-Coursework-Project/Sprint-2/app/src/data/playerGameMapping.csv");
  }

  /**
   * This method is used to extract the players from the file line by line and assign to the array list
   * Private method that is called by the constructor
   */
  private ArrayList<Player> fillArray() {
    //local array list of players
    ArrayList<Player> players = new  ArrayList<>();

    //reading from the file and filling the array, using a scanner that can catch errors
      try (Scanner readFile = new Scanner(playersFile)) {
        //setting the delimeter to get a full line as a string
        readFile.useDelimiter("\n");

        //read from the file, do for each line
        while (readFile.hasNext()) {
          //set to the next line
          String nextLine = readFile.next();

          //extracting full line, split by a comma
          String [] myArray = nextLine.split(",");

          //construct new Player class, using the username which is first element
          Player temp = new Player (myArray[0]);

          //assign the player to the ArrayList
          players.add(temp);
        }
      } catch (FileNotFoundException e) {
        System.out.println("An error occured");
        e.printStackTrace();
      }
      return players;
  }
}
