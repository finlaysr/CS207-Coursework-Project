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

  private int fontChange = 0;
  private static final int FONT_INCREMENT = 3;

  public GUI(Game game) {
    this.game = game;
    GitSetup.configureHooksPath(); // FIXME: Remove this later

    JFrame mainFrame = new JFrame("Cryptogram Game");
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
    mainFrame.add(titleLabel, GUI.setConstraints(0, 0, 1, 1));

    contentPane = new JPanel();
    mainFrame.add(contentPane, GUI.setConstraints(0, 1, 1, 1));

    JPanel fontPanel = new JPanel(new GridBagLayout());
    fontPanel.setBackground(Color.lightGray);
    fontPanel.add(new JLabel("Font Size  "), GUI.setConstraints(0, 0, 1, 1));
    mainFrame.add(fontPanel, GUI.setConstraints(0, 2, 1, 1));

    JButton fontUp = new JButton("+");
    fontPanel.add(fontUp, GUI.setConstraints(1, 0, 1, 1));
    fontUp.addActionListener(
        _ -> {
          this.fontChange += 1;
          resizeFont(mainFrame, FONT_INCREMENT);
        });

    JButton fontDown = new JButton("-");
    fontPanel.add(fontDown, GUI.setConstraints(2, 0, 1, 1));
    fontDown.addActionListener(
        _ -> {
          resizeFont(mainFrame, -FONT_INCREMENT);
          this.fontChange -= 1;
        });

    switchContent(new GameChoicePanel(this, game));
  }

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

  protected static GridBagConstraints setConstraints(int x, int y, int width, int height) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = x;
    gbc.gridy = y;
    gbc.gridwidth = width;
    gbc.gridheight = height;
    return gbc;
  }

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

class GameChoicePanel extends JPanel {
  private final GUI gui;
  private Game game;

  protected GameChoicePanel(GUI gui, Game game) {
    this.gui = gui;
    this.game = game;
    this.setLayout(new GridBagLayout());
    this.add(new JLabel("Choose Game Type:"), GUI.setConstraints(0, 0, 1, 1));

    JButton letterButton = new JButton("Letter to Letter");
    this.add(letterButton, GUI.setConstraints(0, 1, 1, 1));
    letterButton.addActionListener(
        _ -> {
          game.generateCryptogram(true);
          this.gui.switchContent(new GamePanel(this.gui, game));
        });

    JButton numberButton = new JButton("Letter to Number");
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
  private HashMap<String, ArrayList<JTextField>> inputFields = new HashMap<>();
  private ArrayList<String> encryptedChars = new ArrayList<>();

  protected GamePanel(GUI gui, Game game) {
    this.gui = gui;
    this.game = game;
    this.setLayout(new GridBagLayout());

    // TODO: fix this scrollbar
    inputGroup = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    inputGroup.setPreferredSize(new Dimension(800, 400)); // width controls wrapping
    JScrollPane scrollPane =
        new JScrollPane(
            inputGroup,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    this.add(scrollPane, GUI.setConstraints(0, 1, 1, 1));

    this.add(
        new JLabel(
            game.isLetterCrypto() ? "Letter to Letter Cryptogram" : "Letter to Number Cryptogram"),
        GUI.setConstraints(0, 0, 1, 1));

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
    inputGroup.add(wordPanel);

    for (String encrypted : characters) {
      if (encrypted.equals(" ")) {
        wordPanel = new JPanel(new FlowLayout());
        wordPanel.setBackground(Color.lightGray);
        inputGroup.add(wordPanel);

      } else {
        JPanel charPanel = new JPanel(new GridBagLayout());
        charPanel.setBackground(Color.lightGray);

        // Check if it's a character for input, not punctuation
        if ((game.isLetterCrypto() && encrypted.matches("^[a-z]*$"))
            || (!game.isLetterCrypto() && encrypted.matches("^[0-9]*$"))) {
          i++;
          encryptedChars.add(encrypted);

          JTextField textField = buildCharInput(i, encrypted);
          if (!inputFields.containsKey(encrypted)) {
            inputFields.put(encrypted, new ArrayList<>());
          }
          inputFields.get(encrypted).add(textField);
          charPanel.add(textField, GUI.setConstraints(0, 0, 1, 1));
        } else {
          charPanel.add(new JLabel(encrypted), GUI.setConstraints(0, 0, 1, 1));
        }

        charPanel.add(new JLabel(encrypted), GUI.setConstraints(0, 1, 1, 1));
        wordPanel.add(charPanel);
      }
    }
    inputGroup.revalidate();
    inputGroup.repaint();
  }

  private void regenerateGuess() {
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
            } else {
              char guess = Character.toLowerCase(evt.getKeyChar());
              System.out.println("Letter entered: " + guess + " encrypted: " + encrypted);

              // Check it was a valid input
              String error = game.enterLetter(guess, encrypted);
              if (error == null) {
                // move to next text field if not at end
                if (index < encryptedChars.size() - 1) {
                  String nextChar = encryptedChars.get(index + 1);
                  inputFields
                      .get(nextChar)
                      .get(
                          (int)
                              encryptedChars.subList(0, index).stream()
                                  .filter(e -> e.equals(nextChar))
                                  .count())
                      .requestFocus();
                }
              } else {
                JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
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
                (label, fields) -> {
                  if (label.equals(encrypted)) {
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
            ((JTextField) evt.getSource()).setBackground(Color.ORANGE);
            ((JTextField) evt.getSource()).setCaretColor(Color.ORANGE);
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
