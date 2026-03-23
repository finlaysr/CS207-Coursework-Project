/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25;

import org.team25.gui.GUI;

public class App {
  static void main() {
    // Create a new game
    Game game = new Game();
    // Start the gui
    GUI gui = new GUI(game);
  }
}
