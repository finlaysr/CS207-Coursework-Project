/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import org.team25.Game;

/** Main GUI class that sets up the window and loads the first screen */
public class GUI {
  final Font titleFont = new Font("Ariel", Font.BOLD, 20);
  private final JPanel contentPane;
  private final JButton backButton;

  private int fontChange = 0;
  private static final int FONT_INCREMENT = 3;

  public GUI(Game game) {
    // Main window of the game
    JFrame mainFrame = new JFrame("Cryptogram Game");
    // When window closes stop the program
    mainFrame.addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent windowEvent) {
            System.exit(0);
          }
        });
    mainFrame.setSize(1000, 800);
    mainFrame.setLayout(new GridBagLayout());
    mainFrame.setVisible(true);

    JLabel titleLabel = new JLabel("Group 25 Cryptogram Game");
    titleLabel.setFont(titleFont);
    mainFrame.add(titleLabel, GUI.setConstraints(0, 0));

    // Part of the app that all the different screens will insert into
    contentPane = new JPanel();
    mainFrame.add(contentPane, GUI.setConstraints(0, 1));

    // Add panel for increasing and decreasing the font size
    JPanel fontPanel = new JPanel(new GridBagLayout());
    fontPanel.setBackground(Color.lightGray);
    fontPanel.add(new JLabel("Font Size  "), GUI.setConstraints(0, 0));
    mainFrame.add(fontPanel, GUI.setConstraints(0, 2, new Insets(10, 0, 0, 0)));

    // Increase font size button
    JButton fontUp = new JButton("+");
    fontPanel.add(fontUp, GUI.setConstraints(1, 0));
    fontUp.addActionListener(
        _ -> {
          this.fontChange += 1;
          resizeFont(mainFrame, FONT_INCREMENT);
        });

    // Decrease font size button
    JButton fontDown = new JButton("-");
    fontPanel.add(fontDown, GUI.setConstraints(2, 0));
    fontDown.addActionListener(
        _ -> {
          resizeFont(mainFrame, -FONT_INCREMENT);
          this.fontChange -= 1;
        });

    // Button to go back to previous screen
    backButton = new JButton("Back");
    mainFrame.add(backButton, GUI.setConstraints(0, 3));

    // Go to first screen
    switchContent(new WelcomePanel(this, game));
  }

  /** Switch the main content panel to a new panel */
  protected void switchContent(JPanel pane) {
    // Use invokeLater to ensure thread safety
    if (SwingUtilities.isEventDispatchThread()) {
      this.contentPane.removeAll();
      this.contentPane.add(pane);
      resizeFont(this.contentPane, this.fontChange * FONT_INCREMENT);

      this.contentPane.revalidate();
      this.contentPane.repaint();
    } else {
      SwingUtilities.invokeLater(() -> switchContent(pane));
    }
  }

  public JButton getBackButton() {
    return backButton;
  }

  // Used to position items in a grid
  protected static GridBagConstraints setConstraints(int x, int y) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = x;
    gbc.gridy = y;
    return gbc;
  }

  protected static GridBagConstraints setConstraints(int x, int y, Insets margin) {
    GridBagConstraints gbc = setConstraints(x, y);
    gbc.insets = margin;
    return gbc;
  }

  protected static GridBagConstraints setConstraints(int x, int y, int width, int height) {
    GridBagConstraints gbc = setConstraints(x, y);
    gbc.gridwidth = width;
    gbc.gridheight = height;
    return gbc;
  }

  // Recursively increase the font size of all elements in the app
  protected void resizeFont(Container container, int change) {
    for (Component comp : container.getComponents()) {
      Font old = comp.getFont();
      if (old != null && (old.getSize() + change > 3)) {

        comp.setFont(old.deriveFont(old.getStyle(), old.getSize() + change));
        comp.repaint();
      }
      if (comp instanceof Container inner) {
        resizeFont(inner, change);
      }
    }
  }
}

