/* CS207 Cryptogram Project - Sprint 2 - Team 25 2026 */
package org.team25.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import org.team25.Game;

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
    inputGroup.setPreferredSize(new Dimension(600, 300));
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
        _ -> {
          if (game.checkWin()) {
            gui.switchContent(new LeaderBoardPanel(gui, game));
          } else {
            JOptionPane.showMessageDialog(null, "Incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
          }
        });

    // Set back button to link to Game Choice panel
    if (gui.getBackButton().getActionListeners().length > 0) {
      gui.getBackButton().removeActionListener(gui.getBackButton().getActionListeners()[0]);
    }
    gui.getBackButton().addActionListener(_ -> {
         JOptionPane.showMessageDialog(null, "Game has been saved","Information", JOptionPane.INFORMATION_MESSAGE);
         gui.switchContent(new GameChoicePanel(gui, game));
    });
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
