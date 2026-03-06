/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class GUI {
  final Font titleFont = new Font("Ariel", Font.BOLD, 20);
  private JPanel contentPane;
  private Game game;

  public GUI(Game game) {
    this.game = game;
    JFrame mainFrame = new JFrame("Cryptogram Game");
    GitSetup.configureHooksPath();

    mainFrame.addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent windowEvent) {
            System.exit(0);
          }
        });

    mainFrame.setSize(800, 600);
    mainFrame.setLayout(new GridBagLayout());
    mainFrame.setVisible(true);

    JLabel titleLabel = new JLabel("Group 25 Cryptogram Game");
    titleLabel.setFont(titleFont);
    mainFrame.add(titleLabel, GUI.setConstraints(0, 0, 1, 1));

    contentPane = new JPanel();
    mainFrame.add(contentPane, GUI.setConstraints(0, 1, 1, 1));

    JPanel fontPanel = new JPanel(new GridBagLayout());
    fontPanel.setBackground(Color.lightGray);
    fontPanel.add(new JLabel("Font Size  "), GUI.setConstraints(0, 0, 1, 1));
    mainFrame.add(fontPanel, GUI.setConstraints(0, 2, 1, 1));

    JButton fontUp = new JButton("+");
    fontPanel.add(fontUp, GUI.setConstraints(1, 0, 1, 1));
    fontUp.addActionListener(_ -> resizeFont(mainFrame, 3));

    JButton fontDown = new JButton("-");
    fontPanel.add(fontDown, GUI.setConstraints(2, 0, 1, 1));
    fontDown.addActionListener(_ -> resizeFont(mainFrame, -3));

    switchContent(new GameChoicePanel(this, game));
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

  protected static GridBagConstraints setConstraints(int x, int y, int width, int height) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = x;
    gbc.gridy = y;
    gbc.gridwidth = width;
    gbc.gridheight = height;
    return gbc;
  }

  protected static void resizeFont(Container container, int change) {
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

class GameChoicePanel extends JPanel {
  private final GUI gui;
  private Game game;

  protected GameChoicePanel(GUI gui, Game game) {
    this.gui = gui;
    this.game = game;
    this.setLayout(new GridBagLayout());
    this.add(new JLabel("Choose Game Type:"), GUI.setConstraints(0, 0, 1, 1));

    JButton letterButton = new JButton("Letter");
    this.add(letterButton, GUI.setConstraints(0, 1, 1, 1));
    letterButton.addActionListener(
        _ -> {
          game.generateCryptogram(true);
          this.gui.switchContent(new GamePanel(this.gui, game));
        });

    JButton numberButton = new JButton("Number");
    this.add(numberButton, GUI.setConstraints(0, 2, 1, 1));
    numberButton.addActionListener(
        _ -> {
          game.generateCryptogram(false);
          this.gui.switchContent(new GamePanel(this.gui, game));
        });
  }
}

class GamePanel extends JPanel {
  private final GUI gui;
  private Game game;

  private JPanel inputGroup;
  // Key: Encrypted, Value: Array of input fields corresponding to that key
  private HashMap<String, ArrayList<JTextField>> inputFields;

  protected GamePanel(GUI gui, Game game) {
    this.gui = gui;
    this.game = game;
    this.setLayout(new GridBagLayout());
    this.inputFields = new HashMap<>();

    this.add(new JLabel("Game"), GUI.setConstraints(0, 0, 1, 1));

    this.inputGroup = new JPanel(new FlowLayout());
    this.add(inputGroup, GUI.setConstraints(0, 1, 1, 1));

    addWords(game.getEncryptedPhrase());

    JButton undoButton = new JButton("Undo");
    this.add(undoButton, GUI.setConstraints(0, 2, 1, 1));
    undoButton.addActionListener(
        _ -> {
          game.undoLetter();
          regenerateGuess();
        });

    JButton submitButton = new JButton("Submit");
    this.add(submitButton, GUI.setConstraints(0, 3, 1, 1));
    submitButton.addActionListener(
        _ ->
            inputFields.forEach(
                (enc, arr) -> {
                  System.out.print("\n" + enc + ": ");
                  arr.forEach(f -> System.out.print(f.getText() + ", "));
                }));

    JButton backButton = new JButton("Back");
    this.add(backButton, GUI.setConstraints(0, 4, 1, 1));
    backButton.addActionListener(_ -> gui.switchContent(new GameChoicePanel(gui, game)));
  }

  private void addWords(ArrayList<String> characters) {
    int i = -1;
    JPanel wordPanel = new JPanel(new FlowLayout());
    wordPanel.setBackground(Color.lightGray);

    for (String encrypted : characters) {
      if (encrypted.equals(" ")) {
        inputGroup.add(wordPanel);
        wordPanel = new JPanel(new FlowLayout());
        wordPanel.setBackground(Color.lightGray);

      } else {
        i++;
        JPanel charPanel = new JPanel(new GridBagLayout());
        charPanel.setBackground(Color.lightGray);

        // Check if it's a character for input, not punctuation
        if (encrypted.matches("^[a-z0-9]*$")) {
          JTextField textField = buildCharInput(i, encrypted);
          if (!inputFields.containsKey(encrypted)) {
            inputFields.put(encrypted, new ArrayList<>());
          }
          inputFields.get(encrypted).add(textField);
          charPanel.add(textField, GUI.setConstraints(0, 0, 1, 1));
        }

        charPanel.add(new JLabel(encrypted), GUI.setConstraints(0, 1, 1, 1));
        wordPanel.add(charPanel);
      }
    }
    inputGroup.add(wordPanel);
  }

  private void regenerateGuess() {
    System.out.println("Regenerating Guess");
    inputFields.forEach(
        (encrypted, arr) -> {
          if (game.getGuessStack().containsKey(encrypted)) {
            arr.forEach(field -> field.setText(game.getGuessStack().get(encrypted).toString()));
          } else {
            arr.forEach(field -> field.setText(""));
          }
        });
  }

  private JTextField buildCharInput(int index, String encrypted) {
    JTextField textField = new JTextField(1);
    textField.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    textField.addKeyListener(
        new java.awt.event.KeyAdapter() {
          @Override
          public void keyTyped(java.awt.event.KeyEvent evt) {
            if (evt.getKeyChar() == KeyEvent.VK_DELETE
                || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
              game.getGuessStack().remove(encrypted);
              regenerateGuess();
              // move to previous input if not at start
              if (index > 0 && evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                // TODO: Move focus backwards
              }
            } else {
              char guess = Character.toLowerCase(evt.getKeyChar());
              System.out.println("Letter entered: " + guess + " encrypted: " + encrypted);

              // Check it was a valid input
              if (game.enterLetter(guess, encrypted)) {
                // move to next text field if not at end
                if (index < inputFields.size() - 1) {
                  // TODO: Move focus forwards
                }
              } else {
                JOptionPane.showMessageDialog(
                    null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
              }
            }
            regenerateGuess();
            evt.consume();
          }
        });

    textField.addFocusListener(
        new java.awt.event.FocusAdapter() {
          @Override
          public void focusGained(java.awt.event.FocusEvent evt) {
            inputFields.forEach(
                (c, fields) -> {
                  if (c.equals(encrypted)) {
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
          }

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

// Temporary, will be removed after everyone has this set up
class GitSetup {
  public static void configureHooksPath() {
    ProcessBuilder pb = new ProcessBuilder("git", "config", "core.hooksPath", ".githooks");

    pb.inheritIO();
    int exit = 0;
    try {
      Process p = pb.start();
      exit = p.waitFor();
    } catch (IOException | InterruptedException e) {

      System.out.println("Failed to set core.hooksPath: " + e);
    }

    if (exit != 0) {
      System.out.println("Failed to set core.hooksPath (exit " + exit + ")");
    }
  }
}
