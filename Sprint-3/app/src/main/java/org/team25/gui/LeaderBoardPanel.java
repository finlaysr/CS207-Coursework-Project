/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25.gui;

import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.team25.Game;

public class LeaderBoardPanel extends JPanel {
  protected LeaderBoardPanel(GUI gui, Game game) {
    this.setLayout(new GridBagLayout());

    JLabel welcomeLabel = new JLabel("Game Complete!");
    welcomeLabel.setFont(new Font("Ariel", Font.BOLD, 18));
    this.add(welcomeLabel, GUI.setConstraints(0, 0));

    this.add(new JLabel("Congratulations!"), GUI.setConstraints(0, 1));
    this.add(new JLabel("Leaderboard: "), GUI.setConstraints(0, 2));

    // Set back button to go to Game Choice Screen
    gui.getBackButton().setVisible(true);
    if (gui.getBackButton().getActionListeners().length > 0) {
      gui.getBackButton().removeActionListener(gui.getBackButton().getActionListeners()[0]);
    }
    gui.getBackButton().addActionListener(_ -> gui.switchContent(new GameChoicePanel(gui, game)));
  }
}
