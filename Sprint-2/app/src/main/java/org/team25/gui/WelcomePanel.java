package org.team25.gui;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.team25.Game;

/** Welcome panel containing app logo */
class WelcomePanel extends JPanel {
  protected WelcomePanel(GUI gui, Game game) {
    this.setLayout(new GridBagLayout());

    JLabel welcomeLabel = new JLabel("Welcome to our Cryptogram Game!");
    welcomeLabel.setFont(new Font("Ariel", Font.BOLD, 18));
    this.add(welcomeLabel, GUI.setConstraints(0, 0));

    // Load app logo image, resize it, and display it
    try {
      BufferedImage logo = ImageIO.read(new File("src/resources/Logo-2.png"));
      Image logoScaled = logo.getScaledInstance(300, 400, Image.SCALE_SMOOTH);
      JLabel picLabel = new JLabel(new ImageIcon(logoScaled));
      this.add(picLabel, GUI.setConstraints(0, 1));
    } catch (IOException _) {
      System.out.println("Could not load logo image!");
    }

    // Go to Game Choice panel
    JButton startButton = new JButton("Start");
    startButton.addActionListener(_ -> gui.switchContent(new LoginSignUpPanel(gui, game)));
    this.add(startButton, GUI.setConstraints(0, 2));

    // Hide the back button since there is no previous screen
    gui.getBackButton().setVisible(false);
  }
}
