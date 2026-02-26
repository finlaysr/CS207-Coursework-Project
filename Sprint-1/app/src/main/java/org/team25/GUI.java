/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class GUI {
  private JFrame mainFrame;
  private JLabel titleLabel;
  private JPanel contentPane;

  public GUI() {
    mainFrame = new JFrame("Cryptogram Game");

    mainFrame.addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent windowEvent) {
            // App.shutdown();
            System.exit(0);
          }
        });

    mainFrame.setSize(800, 600);
    mainFrame.setLayout(new GridBagLayout());
    mainFrame.setVisible(true);

    titleLabel = new JLabel("Group 25 Cryptogram Game");
    mainFrame.add(titleLabel, GUI.setConstraints(0, 0, 1, 1));

    contentPane = new JPanel();
    mainFrame.add(contentPane, GUI.setConstraints(0, 1, 1, 1));

    switchContent(new Game());
  }

  class Game extends JPanel {
    private Game() {
      this.add(new JLabel("Game"));

      JButton button = new JButton("Undo");
      this.add(button);
    }
  }

  protected void switchContent(JPanel pane) {
    // Use invokeLater to ensure thread safety
    if (SwingUtilities.isEventDispatchThread()) {
      this.contentPane.removeAll();
      this.contentPane.add(pane);
      this.contentPane.revalidate();
      this.contentPane.repaint();
    } else {
      SwingUtilities.invokeLater(() -> switchContent(pane));
    }
  }

  private static GridBagConstraints setConstraints(int x, int y, int width, int height) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = x;
    gbc.gridy = y;
    gbc.gridwidth = width;
    gbc.gridheight = height;
    return gbc;
  }
}
