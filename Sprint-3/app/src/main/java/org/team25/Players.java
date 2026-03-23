/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25;

// packages
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import org.jspecify.annotations.NonNull;

/** A collection of all the players, this is what Game interacts with */
public class Players {

  // instance variables
  private ArrayList<Player> allPlayers = new ArrayList<>();

  // player file
  private final File playersDir =
      new File("src" + File.separator + "data" + File.separator + "players");

  /** Constructor method - loads in the players */
  public Players() {
    loadData(playersDir, allPlayers);
  }

  public void saveAllData() {
    saveData(playersDir, allPlayers);
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
