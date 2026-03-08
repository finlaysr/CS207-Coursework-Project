/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

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
    startButton.addActionListener(_ -> gui.switchContent(new GameChoicePanel(gui, game)));
    this.add(startButton, GUI.setConstraints(0, 2));

    // Hide the back button since there is no previous screen
    gui.getBackButton().setVisible(false);
  }
}

/** Panel where user chooses what kind of cryptogram they want to play */
class GameChoicePanel extends JPanel {
  protected GameChoicePanel(GUI gui, Game game) {
    this.setLayout(new GridBagLayout());
    this.add(new JLabel("Choose Game Type:"), GUI.setConstraints(0, 0));

    // Button for Letter to Letter cryptogram
    JButton letterButton = new JButton("Letter to Letter");
    this.add(letterButton, GUI.setConstraints(0, 1));
    letterButton.addActionListener(
        _ -> {
          game.generateCryptogram(true);
          gui.switchContent(new GamePanel(gui, game));
        });

    // Button for Letter to Number cryptogram
    JButton numberButton = new JButton("Letter to Number");
    this.add(numberButton, GUI.setConstraints(0, 2));
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
    gui.getBackButton().addActionListener(_ -> gui.switchContent(new WelcomePanel(gui, game)));
  }
}

/** Panel where the main game is played */
class GamePanel extends JPanel {
  private final Game game;

  private final JPanel inputGroup;

  /** Key: Encrypted character / number, Value: Array of input fields corresponding to that key */
  private final HashMap<String, ArrayList<JTextField>> inputFields = new HashMap<>();

  /** All the encrypted characters in order (excluding punctuation etc) */
  private final ArrayList<String> encryptedChars = new ArrayList<>();

  protected GamePanel(GUI gui, Game game) {
    this.game = game;
    this.setLayout(new GridBagLayout());

    // Add title
    this.add(
        new JLabel(
            game.isLetterCrypto() ? "Letter to Letter Cryptogram" : "Letter to Number Cryptogram"),
        GUI.setConstraints(0, 0));

    // TODO: fix this scrollbar
    // Panel containing all the encrypted word groups
    inputGroup = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    inputGroup.setPreferredSize(new Dimension(800, 400));
    JScrollPane scrollPane =
        new JScrollPane(
            inputGroup,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    this.add(scrollPane, GUI.setConstraints(0, 1));

    addWords(game.getEncryptedPhrase());

    // Add undo button
    JButton undoButton = new JButton("Undo");
    this.add(undoButton, GUI.setConstraints(0, 2));
    undoButton.addActionListener(
        _ -> {
          game.undoLetter();
          regenerateGuess();
        });

    // Add submit button
    // TODO: make this do something
    JButton submitButton = new JButton("Submit");
    this.add(submitButton, GUI.setConstraints(0, 3));
    submitButton.addActionListener(
        _ ->
            inputFields.forEach(
                (enc, arr) -> {
                  System.out.print("\n" + enc + ": ");
                  arr.forEach(f -> System.out.print(f.getText() + ", "));
                }));

    // Set back button to link to Game Choice panel
    if (gui.getBackButton().getActionListeners().length > 0) {
      gui.getBackButton().removeActionListener(gui.getBackButton().getActionListeners()[0]);
    }
    gui.getBackButton().addActionListener(_ -> gui.switchContent(new GameChoicePanel(gui, game)));
  }

  /** Add all encrypted characters and inputs, grouped by word */
  private void addWords(ArrayList<String> encryptedPhrase) {
    int i = -1; // counter for valid input characters
    JPanel wordPanel = new JPanel(new FlowLayout()); // flow layout so words will wrap to next line
    wordPanel.setBackground(Color.lightGray);
    inputGroup.add(wordPanel);

    for (String encrypted : encryptedPhrase) {
      // Split up by word
      if (encrypted.equals(" ")) {
        // Holds a single word of encrypted characters and inputs
        wordPanel = new JPanel(new FlowLayout());
        wordPanel.setBackground(Color.lightGray);
        inputGroup.add(wordPanel);

      } else {
        // Holds one encrypted character and its input field
        JPanel charPanel = new JPanel(new GridBagLayout());
        charPanel.setBackground(Color.lightGray);

        // Check if it's a character for input, not punctuation or a number
        if (!encrypted.equals(game.getCryptoAlph().get(encrypted).toString())) {
          i++;
          encryptedChars.add(encrypted);

          // build a new custom input field and add it the character group
          JTextField textField = buildCharInput(i, encrypted);
          charPanel.add(textField, GUI.setConstraints(0, 0));

          // Store the new input field in the hashmap
          if (!inputFields.containsKey(encrypted)) {
            inputFields.put(encrypted, new ArrayList<>());
          }
          inputFields.get(encrypted).add(textField);
        } else {
          // Just add the encrypted character again instead of an input field
          charPanel.add(new JLabel(encrypted), GUI.setConstraints(0, 0));
        }

        // Add the encrypted character under the input
        charPanel.add(new JLabel(encrypted), GUI.setConstraints(0, 1));
        wordPanel.add(charPanel);
      }
    }
    inputGroup.revalidate();
    inputGroup.repaint();
  }

  // After guess stack has been changed, ensure all input fields reflect the change
  private void regenerateGuess() {
    inputFields.forEach(
        (encrypted, inputs) -> {
          if (game.getGuessStack().containsKey(encrypted)) {
            // if that character has a guess set all of its input fields to that guess
            inputs.forEach(field -> field.setText(game.getGuessStack().get(encrypted).toString()));
          } else {
            inputs.forEach(field -> field.setText(""));
          }
        });
  }

  /**
   * Returns a new input field that ensures all similar inputs stay equal, ability to move forwards
   * and backwards, and with automatic colouring if similar field selected
   */
  private JTextField buildCharInput(int index, String encrypted) {
    JTextField textField = new JTextField(1); // new input of length 1
    textField.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    // Do action when letter typed
    textField.addKeyListener(
        new java.awt.event.KeyAdapter() {
          @Override
          public void keyTyped(java.awt.event.KeyEvent evt) {
            // If deleting a character
            if (evt.getKeyChar() == KeyEvent.VK_DELETE
                || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
              game.getGuessStack().remove(encrypted);
              regenerateGuess();
              // move to previous input field if not at start
              if (index > 0 && evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                String prevChar = encryptedChars.get(index - 1);
                inputFields
                    .get(prevChar)
                    .get(
                        (int)
                            encryptedChars.subList(0, index - 1).stream()
                                .filter(e -> e.equals(prevChar))
                                .count())
                    .requestFocus();
              }
            } else { // If entering a new character
              char guess = Character.toLowerCase(evt.getKeyChar());

              // Check it was a valid input
              String error = game.enterLetter(encrypted, guess);
              if (error == null) {
                // move to next input field if not at end
                if (index < encryptedChars.size() - 1) {
                  String nextChar = encryptedChars.get(index + 1);
                  inputFields
                      .get(nextChar)
                      .get(
                          (int)
                              encryptedChars.subList(0, index + 1).stream()
                                  .filter(e -> e.equals(nextChar))
                                  .count())
                      .requestFocus();
                }
              } else {
                // If invalid character entered
                JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
              }
            }
            regenerateGuess(); // update all text fields
            evt.consume();
          }
        });

    // Sets background colour of field depending on if similar field in focus
    textField.addFocusListener(
        new java.awt.event.FocusAdapter() {
          @Override
          public void focusGained(java.awt.event.FocusEvent evt) {
            inputFields.forEach(
                (label, fields) -> {
                  if (label.equals(encrypted)) {
                    // If field of same encrypted value selected, set background to yellow
                    fields.forEach(
                        field -> {
                          field.setBackground(Color.YELLOW);
                          field.setCaretColor(Color.YELLOW);
                        });
                  } else {
                    fields.forEach(
                        field -> {
                          field.setBackground(Color.WHITE);
                          field.setCaretColor(Color.WHITE);
                        });
                  }
                });
            // Set currently selected field background to orange
            ((JTextField) evt.getSource()).setBackground(Color.ORANGE);
            ((JTextField) evt.getSource()).setCaretColor(Color.ORANGE);
          }

          // remove colour from all fields when focus lost, e.g. if button pressed
          @Override
          public void focusLost(FocusEvent e) {
            inputFields.forEach(
                (_, fields) ->
                    fields.forEach(
                        field -> {
                          field.setBackground(Color.WHITE);
                          field.setCaretColor(Color.WHITE);
                        }));
          }
        });

    return textField;
  }
}
