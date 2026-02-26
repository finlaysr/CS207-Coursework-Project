/* CS207 Cryptogram Project - Sprint 1 - Team 25 2026 */
package org.team25;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;

public class GUI {
  private JPanel contentPane;

  public GUI() {
    JFrame mainFrame = new JFrame("Cryptogram Game");

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
    mainFrame.add(titleLabel, GUI.setConstraints(0, 0, 1, 1));

    contentPane = new JPanel();
    mainFrame.add(contentPane, GUI.setConstraints(0, 1, 1, 1));

    switchContent(new GamePanel());
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

class GamePanel extends JPanel {
  private JPanel inputGroup;
  private ArrayList<JTextField> inputFields;

  protected GamePanel() {
    this.setLayout(new GridBagLayout());
    this.inputFields = new ArrayList<>();

    this.add(new JLabel("Game"), GUI.setConstraints(0, 0, 1, 1));

    this.inputGroup = new JPanel(new FlowLayout());
    this.add(inputGroup, GUI.setConstraints(0, 1, 1, 1));

    ArrayList<String> words = new ArrayList<>();
    words.add("Hello");
    words.add("World");
    words.add("Test");
    addWords(words);

    JButton button = new JButton("Undo");
    this.add(button, GUI.setConstraints(0, 2, 1, 1));

    JButton submitButton = new JButton("Submit");
    this.add(submitButton, GUI.setConstraints(0, 3, 1, 1));
    submitButton.addActionListener(
        _ -> inputFields.forEach(inputField -> System.out.println(inputField.getText())));
  }

  private void addWords(ArrayList<String> words) {
    int i = -1;
    for (String word : words) {
      JPanel wordPanel = new JPanel(new FlowLayout());
      wordPanel.setBackground(Color.lightGray);
      for (char c : word.toCharArray()) {
        i++;
        JPanel charPanel = new JPanel(new GridBagLayout());
        charPanel.setBackground(Color.lightGray);
        JTextField textField = buildCharInput(i);

        inputFields.add(textField);
        charPanel.add(textField, GUI.setConstraints(0, 0, 1, 1));

        charPanel.add(new JLabel(Character.toString(c)), GUI.setConstraints(0, 1, 1, 1));
        wordPanel.add(charPanel);
      }
      inputGroup.add(wordPanel);
    }
  }

  private JTextField buildCharInput(int index) {
    JTextField textField = new JTextField(1);
    textField.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    textField.addKeyListener(
        new java.awt.event.KeyAdapter() {
          @Override
          public void keyTyped(java.awt.event.KeyEvent evt) {
            if (evt.getKeyChar() == KeyEvent.VK_DELETE
                || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
              textField.setText("");
              if (index > 0 && evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                inputFields.get(index - 1).requestFocus();
              }
            } else {
              textField.setText(Character.toString(evt.getKeyChar()));
              if (index < inputFields.size() - 1) {
                inputFields.get(index + 1).requestFocus();
              }
              System.out.println("Letter entered: " + evt.getKeyChar());
            }
            evt.consume();
          }
        });
    textField.addFocusListener(
        new java.awt.event.FocusAdapter() {
          @Override
          public void focusGained(java.awt.event.FocusEvent evt) {
            inputFields.forEach(field -> field.setBackground(Color.WHITE));
            inputFields.forEach(field -> field.setCaretColor(Color.WHITE));
            // light blue
            textField.setBackground(Color.YELLOW);
            textField.setCaretColor(Color.YELLOW);
          }
        });
    return textField;
  }
}
