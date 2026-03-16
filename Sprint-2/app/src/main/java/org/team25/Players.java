/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25;

// packages
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import org.jspecify.annotations.NonNull;

/** A collection of all the players, this is what Game interacts with */
public class Players {

  // instance variables
  private ArrayList<Player> allPlayers;
  private ArrayList<Cryptogram> allCryptograms = new ArrayList<>();

  private final File playersFile = new File("src/data/playerGameMapping.csv");
  private final File playersDir =
      new File("src" + File.separator + "data" + File.separator + "players");
  private final File cryptogramsDir =
      new File("src" + File.separator + "data" + File.separator + "cryptograms");

  /** Constructor method - loads in the players and cryptograms from a file */
  public Players() {
    allPlayers = fillArray();
    loadData(playersDir, allPlayers);
    loadData(cryptogramsDir, allCryptograms);
  }

  public void saveAllData() {
    saveData(playersDir, allPlayers);
    saveData(cryptogramsDir, allCryptograms);
  }

  /**
   * This method is used to extract the players from the file line by line and assign to the array
   * list Private method that is called by the constructor
   */
  private @NonNull ArrayList<Player> fillArray() {
    // local array list of players
    ArrayList<Player> players = new ArrayList<>();

    // reading from the file and filling the array, using a scanner that can catch errors
    try (Scanner readFile = new Scanner(playersFile)) {
      // setting the delimeter to get a full line as a string
      readFile.useDelimiter("\n");

      // read from the file, do for each line
      while (readFile.hasNext()) {
        // set to the next line
        String nextLine = readFile.next();

        // extracting full line, split by a comma
        String[] myArray = nextLine.split(",");

        // construct new Player class, using the username which is first element
        Player temp = new Player(myArray[0]);

        // assign the player to the ArrayList
        players.add(temp);
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred when loading players");
      e.printStackTrace();
    }
    return players;
  }

  private <T extends Serializable> void loadData(@NonNull File path, ArrayList<T> output) {
    if (path.exists()) {
      File[] files = path.listFiles();
      assert files != null;
      for (File file : files) {
        if (file.getName().endsWith(".ser")) {
          try (FileInputStream fis = new FileInputStream(file);
              ObjectInputStream ois = new ObjectInputStream(fis)) {
            @SuppressWarnings("unchecked")
            T obj = (T) ois.readObject();
            output.add(obj);
          } catch (ClassNotFoundException | IOException error) {
            System.out.println(error.getMessage());
          }
        }
      }
    }
  }

  private <T extends Serializable> void saveData(@NonNull File path, ArrayList<T> data) {
    if (!path.exists() && !path.mkdirs()) {
      System.out.println("Error creating directory for saving data");
      return;
    }

    for (T thing : data) {
      String filename;
      if (thing.getClass() == Player.class) {
        filename = ((Player) thing).getUsername();
      } else if (thing.getClass() == Cryptogram.class) {
        filename = ((Cryptogram) thing).getCryptogramID().toString();
      } else {
        throw new IllegalArgumentException("Unknown class " + thing.getClass());
      }

      try (FileOutputStream fos = new FileOutputStream(path + File.separator + filename + ".ser");
          ObjectOutputStream oos = new ObjectOutputStream(fos)) {
        oos.writeObject(thing);
      } catch (IOException error) {
        System.out.println(error.getMessage());
      }
    }
  }

  public ArrayList<Player> getAllPlayers() {
    return allPlayers;
  }

  public void addPlayer(Player player) {
    allPlayers.add(player);
  }

  public Player findPlayer(String username) {
    for (Player player : allPlayers) {
      if (player.getUsername().equalsIgnoreCase(username)) {
        return player;
      }
    }
    return null;
  }
}
