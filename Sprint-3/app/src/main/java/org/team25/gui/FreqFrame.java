/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import org.team25.Game;

class FreqFrame extends JFrame {
  FreqFrame(GUI gui, Game game) {
    super("Player Stats");
    this.setSize(1000, 600);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setLayout(new BorderLayout());

    JLabel titleLabel = new JLabel("Letter Frequencies");
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(titleLabel, BorderLayout.NORTH);

    // English table
    // read in letter frequencies from csv file
    String[][] englishData = new String[26][3];
    ArrayList<String[]> englishFreq = new ArrayList<>();
    try (Stream<String> lines = Files.lines(Paths.get("src/resources/letter-freq.csv"))) {
      lines.forEach(line -> englishFreq.add(line.split(",")));
    } catch (Exception e) {
      System.out.println("Couldn't read csv frequencies file: " + e.getMessage());
    }

    final int[] i = {0}; // has to be an array for some reason, java moans otherwise
    System.out.println(game.viewFrequencies());
    englishFreq.forEach(
        freq -> {
          englishData[i[0]][0] = freq[0];
          englishData[i[0]][1] = String.format("%.2f%%", Float.parseFloat(freq[1]) * 100);
          i[0]++;
        });

    String[] englishColumnNames = {"Letter", "English Language"};

    JTable englishTable = new JTable(englishData, englishColumnNames);
    englishTable.setDefaultEditor(Object.class, null); // make table non-editable
    englishTable.setRowHeight(30);

    JScrollPane englishScrollPane =
        new JScrollPane(
            englishTable,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    englishScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(20, 0));

    // Game Table
    // Count of letters in the cryptogram excluding spaces and punctuation
    long letters =
        game.getEncryptedPhrase().stream()
            .filter(enc -> game.getCryptoAlph().containsKey(enc))
            .count();
    System.out.println("letters: " + letters);

    String[] gameColumnNames = {"Encrypted Letter", "This Game"};

    String[][] gameData =
        game.viewFrequencies().keySet().stream()
            .map(
                enc -> {
                  float percent =
                      (float) 100 * game.viewFrequencies().getOrDefault(enc, 0) / letters;
                  return new String[] {enc, String.format("%.2f%%", percent)};
                })
            .toArray(String[][]::new);

    JTable gameTable = new JTable(gameData, gameColumnNames);
    gameTable.setDefaultEditor(Object.class, null); // make table non-editable
    gameTable.setRowHeight(30);

    JScrollPane gameScrollPane =
        new JScrollPane(
            gameTable,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // Add tables to window
    JPanel tablePanel = new JPanel(new GridBagLayout());
    tablePanel.add(englishScrollPane, GUI.setConstraints(0, 0, new Insets(0, 0, 0, 10)));
    tablePanel.add(gameScrollPane, GUI.setConstraints(1, 0));
    tablePanel.add(
        new JLabel("Please increase the window size!"),
        GUI.setConstraints(0, 0, new Insets(0, 0, 60, 0)));
    this.add(tablePanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    this.add(buttonPanel, BorderLayout.SOUTH);
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(_ -> this.dispose());
    buttonPanel.add(closeButton);

    gui.resizeFont(this, gui.getFontChangeTotal());
  }
}
