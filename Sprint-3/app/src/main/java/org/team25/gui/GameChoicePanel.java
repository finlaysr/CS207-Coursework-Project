/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25.gui;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.team25.Game;

/** Panel where user chooses what kind of cryptogram they want to play */
class GameChoicePanel extends JPanel {
  protected GameChoicePanel(GUI gui, Game game) {
    this.setLayout(new GridBagLayout());

    this.add(new JLabel("Continue previous game:"), GUI.setConstraints(0, 0));
    JButton prevButton = new JButton("Load Previous Game");
    this.add(prevButton, GUI.setConstraints(0, 1));
    prevButton.addActionListener(
        _ -> {
          if (game.loadGame()) {
            gui.switchContent(new GamePanel(gui, game));
          } else {
            JOptionPane.showMessageDialog(
                null, "No current cryptogram", "Error", JOptionPane.ERROR_MESSAGE);
          }
        });

    this.add(new JLabel("Start a new game:"), GUI.setConstraints(0, 2));

    // Button for Letter to Letter cryptogram
    JButton letterButton = new JButton("Letter to Letter");
    this.add(letterButton, GUI.setConstraints(0, 3));
    letterButton.addActionListener(
        _ -> {
          game.generateCryptogram(true);
          gui.switchContent(new GamePanel(gui, game));
        });

    // Button for Letter to Number cryptogram
    JButton numberButton = new JButton("Letter to Number");
    this.add(numberButton, GUI.setConstraints(0, 4));
    numberButton.addActionListener(
        _ -> {
          game.generateCryptogram(false);
          gui.switchContent(new GamePanel(gui, game));
        });

    // Set back button to go to Welcome Screen
    gui.getBackButton().setVisible(true);
    if (gui.getBackButton().getActionListeners().length > 0) {
      gui.getBackButton().removeActionListener(gui.getBackButton().getActionListeners()[0]);
    }
    gui.getBackButton().addActionListener(_ -> gui.switchContent(new LoginSignUpPanel(gui, game)));

    // show the user panel
    gui.showPlayerMenu(true);
  }
}
