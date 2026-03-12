/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25.gui;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import org.team25.Game;

public class LoginSignUpPanel extends JPanel {
  protected LoginSignUpPanel(GUI gui, Game game) {
    this.setLayout(new GridBagLayout());
    this.add(new JLabel("Log In or Sign Up:"), GUI.setConstraints(0, 0));

    JTabbedPane tabs = new JTabbedPane();
    tabs.addTab("Sign Up", new SignUp(gui, game));
    tabs.addTab("Log In", new Login(gui, game));
    this.add(tabs, GUI.setConstraints(0, 1, 1, 1));
    this.revalidate();
    this.repaint();

    // Set back button to go to Welcome Screen
    gui.getBackButton().setVisible(true);
    if (gui.getBackButton().getActionListeners().length > 0) {
      gui.getBackButton().removeActionListener(gui.getBackButton().getActionListeners()[0]);
    }
    gui.getBackButton().addActionListener(_ -> gui.switchContent(new WelcomePanel(gui, game)));
  }
}

class SignUp extends JPanel {
  private final GUI gui;
  private final Game game;
  private final JTextField usernameField;

  public SignUp(GUI gui, Game game) {
    this.game = game;
    this.gui = gui;

    this.setLayout(new GridBagLayout());
    this.add(new JLabel("Welcome to the app!:"), GUI.setConstraints(0, 0, 2, 1));
    this.add(new JLabel("Username:"), GUI.setConstraints(0, 1, 1, 1));

    usernameField = new JTextField(20);
    this.add(usernameField, GUI.setConstraints(1, 1, 1, 1));

    JButton signUpButton = new JButton("Sign Up");
    signUpButton.addActionListener(e -> signUpButton());
    this.add(signUpButton, GUI.setConstraints(0, 2, 2, 1));
  }

  private void signUpButton() {
    usernameField.setText(usernameField.getText().strip());
    // TODO: check user doesn't already exist

    gui.switchContent(new GameChoicePanel(gui, game));
  }
}

class Login extends JPanel {
  private final GUI gui;
  private final Game game;
  private final JTextField usernameField;

  public Login(GUI gui, Game game) {
    this.game = game;
    this.gui = gui;

    this.setLayout(new GridBagLayout());
    this.add(new JLabel("Welcome back!:"), GUI.setConstraints(0, 0, 2, 1));
    this.add(new JLabel("Username:"), GUI.setConstraints(0, 1, 1, 1));

    usernameField = new JTextField(20);
    this.add(usernameField, GUI.setConstraints(1, 1, 1, 1));

    JButton logInButton = new JButton("Log In");
    logInButton.addActionListener(e -> loginButton());
    this.add(logInButton, GUI.setConstraints(0, 2, 2, 1));
  }

  public void loginButton() {
    usernameField.setText(usernameField.getText().strip());
    // TODO: check user exists

    gui.switchContent(new GameChoicePanel(gui, game));
  }
}
