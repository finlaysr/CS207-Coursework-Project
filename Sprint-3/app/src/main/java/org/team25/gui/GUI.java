/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.team25.Game;

/** Main GUI class that sets up the window and loads the first screen */
public class GUI {
  final Font titleFont = new Font("Ariel", Font.BOLD, 25);
  private final JPanel contentPane;
  private final JButton backButton;
  private final PlayerMenu playerMenu;

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
            game.shutdown();
            System.exit(0);
          }
        });
    mainFrame.setSize(1000, 800);
    mainFrame.setLocationRelativeTo(null); // opens window in centre of screen
    mainFrame.setLayout(new BorderLayout());
    mainFrame.setVisible(true);

    JLabel titleLabel = new JLabel("Group 25 Cryptogram Game");
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titleLabel.setFont(titleFont);
    mainFrame.add(titleLabel, BorderLayout.NORTH);

    // Part of the app that all the different screens will insert into
    contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout());
    mainFrame.add(contentPane, BorderLayout.CENTER);

    // Panel containing back button and font options
    JPanel bottomPanel = new JPanel(new GridBagLayout());
    mainFrame.add(bottomPanel, BorderLayout.SOUTH);

    // Button to go back to previous screen
    backButton = new JButton("Back");
    bottomPanel.add(backButton, setConstraints(0, 0, new Insets(0, 0, 10, 0)));

    playerMenu = new PlayerMenu(this, game);
    bottomPanel.add(playerMenu, GUI.setConstraints(0, 1, new Insets(0, 0, 10, 0)));

    // Add panel for increasing and decreasing the font size
    JPanel fontPanel = new JPanel(new GridBagLayout());
    fontPanel.setBackground(Color.lightGray);
    fontPanel.add(new JLabel("Font Size  "), GUI.setConstraints(0, 0));
    bottomPanel.add(fontPanel, GUI.setConstraints(0, 2));

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

    // Go to first screen
    switchContent(new WelcomePanel(this, game));
  }

  /** Switch the main content panel to a new panel */
  protected void switchContent(JPanel pane) {
    // Use invokeLater to ensure thread safety
    if (SwingUtilities.isEventDispatchThread()) {
      this.contentPane.removeAll();
      this.contentPane.add(pane, BorderLayout.CENTER);
      resizeFont(this.contentPane, this.fontChange * FONT_INCREMENT);

      this.contentPane.revalidate();
      this.contentPane.repaint();
    } else {
      SwingUtilities.invokeLater(() -> switchContent(pane));
    }
  }

  public void showPlayerMenu(boolean visible) {
    playerMenu.showThis(visible);
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

  protected int getFontChangeTotal() {
    return this.fontChange * FONT_INCREMENT;
  }
}

class PlayerMenu extends JPanel {
  private final JLabel usernameLabel = new JLabel("");
  private final Game game;

  public PlayerMenu(GUI gui, Game game) {
    this.game = game;
    this.setLayout(new GridBagLayout());
    this.setVisible(false);
    this.setBackground(Color.lightGray);

    // Load app user icon image, resize it, and display it
    try {
      BufferedImage logo = ImageIO.read(new File("src/resources/user_icon.png"));
      Image logoScaled = logo.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      JLabel picLabel = new JLabel(new ImageIcon(logoScaled));
      this.add(picLabel, GUI.setConstraints(0, 0, new Insets(0, 5, 0, 10)));
    } catch (IOException _) {
      System.out.println("Could not load user icon image!");
    }

    this.add(usernameLabel, GUI.setConstraints(1, 0, new Insets(0, 0, 0, 10)));

    JButton statsButton = new JButton("Stats");
    statsButton.addActionListener(_ -> new StatsFrame(gui, game));
    this.add(statsButton, GUI.setConstraints(2, 0, new Insets(0, 0, 0, 5)));

    JButton logOutButton = new JButton("Log Out");
    logOutButton.addActionListener(_ -> gui.switchContent(new LoginSignUpPanel(gui, game)));
    this.add(logOutButton, GUI.setConstraints(3, 0));
  }

  public void showThis(boolean visible) {
    this.setVisible(visible);
    if (visible) {
      usernameLabel.setText(game.getCurrentPlayer().getUsername());
    }
    this.revalidate();
    this.repaint();
  }
}

class StatsFrame extends JFrame {
  public StatsFrame(GUI gui, Game game) {
    super("Player Stats");
    this.setSize(600, 400);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setLayout(new BorderLayout());

    JLabel titleLabel = new JLabel(game.getCurrentPlayer().getUsername() + "'s Stats");
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(titleLabel, BorderLayout.NORTH);

    JPanel statsPanel = new JPanel(new GridBagLayout());
    statsPanel.setLayout(new GridBagLayout());
    this.add(statsPanel, BorderLayout.CENTER);

    AtomicInteger i = new AtomicInteger();

    game.getPlayerStats()
        .keySet()
        .forEach(
            stat -> {
              statsPanel.add(
                  new JLabel(stat), GUI.setConstraints(0, i.get(), new Insets(0, 0, 0, 5)));
              statsPanel.add(
                  new JLabel(game.getPlayerStats().get(stat)), GUI.setConstraints(1, i.get()));
              i.getAndIncrement();
            });

    JPanel buttonPanel = new JPanel();
    this.add(buttonPanel, BorderLayout.SOUTH);
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(_ -> this.dispose());
    buttonPanel.add(closeButton);

    gui.resizeFont(this, gui.getFontChangeTotal());
  }
}
