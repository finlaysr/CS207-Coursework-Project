/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
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
}

class Game extends JPanel {

  private JPanel inputGroup;
  private ArrayList<JTextField> inputFields;

  protected Game() {
    this.inputGroup = new JPanel(new FlowLayout());
    this.setLayout(new GridBagLayout());
    this.inputFields = new ArrayList<>();

    this.add(new JLabel("Game"), GUI.setConstraints(0, 0, 1, 1));

    JButton button = new JButton("Undo");
    this.add(button, GUI.setConstraints(0, 1, 1, 1));

    this.add(inputGroup, GUI.setConstraints(0, 2, 1, 1));

    ArrayList<String> words = new ArrayList<>();
    words.add("Hello");
    words.add("World");
    words.add("Test");
    addWords(words);
  }

  protected void addWords(ArrayList<String> words) {
    int i = -1;
    for (String word : words) {
      JPanel wordPanel = new JPanel(new FlowLayout());
      wordPanel.setBackground(Color.GRAY);
      for (char c : word.toCharArray()) {
        i++;
        JPanel charPanel = new JPanel(new GridBagLayout());
        JTextField textField = new JTextField(1);
        int finalI = i;
        textField.addKeyListener(
            new java.awt.event.KeyAdapter() {
              @Override
              public void keyTyped(java.awt.event.KeyEvent evt) {
                if (evt.getKeyChar() == KeyEvent.VK_DELETE
                    || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                  textField.setText("");
                  if (finalI > 0) {
                    inputFields.get(finalI - 1).requestFocus();
                  }
                } else {
                  textField.setText(Character.toString(evt.getKeyChar()));
                  if (finalI < inputFields.size() - 1) {
                    inputFields.get(finalI + 1).requestFocus();
                  }
                }
                evt.consume();
              }
            });
        textField.addFocusListener(
            new java.awt.event.FocusAdapter() {
              @Override
              public void focusGained(java.awt.event.FocusEvent evt) {
                inputFields.forEach(field -> field.setBackground(Color.WHITE));
                textField.setBackground(Color.YELLOW);
              }
            });
        inputFields.add(textField);
        charPanel.add(textField, GUI.setConstraints(0, 0, 1, 1));

        charPanel.add(new JLabel(Character.toString(c)), GUI.setConstraints(0, 1, 1, 1));
        wordPanel.add(charPanel);
      }
      inputGroup.add(wordPanel);
    }
  }
}
