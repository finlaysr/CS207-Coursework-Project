/* CS207 Cryptogram Project - Sprint 3 - Team 25 2026 */
package org.team25.gui;

import java.awt.BorderLayout;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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
    this.setSize(600, 600);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setLayout(new BorderLayout());

    JLabel titleLabel = new JLabel("Letter Frequencies");
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(titleLabel, BorderLayout.NORTH);

    String[][] data = new String[26][3];

    System.out.println(game.viewFrequencies());

    // read in letter frequencies from csv file
    final int[] i = {0}; // has to be an array for some reason, java moans otherwise
    try (Stream<String> lines = Files.lines(Paths.get("src/resources/letter-freq.csv"))) {
      lines.forEach(
          line -> {
            String[] freq = line.split(",");
            System.out.println(Arrays.toString(freq));
            data[i[0]][0] = freq[0];
            data[i[0]][1] = String.valueOf(game.viewFrequencies().get(freq[0].trim()));
            data[i[0]][2] = String.format("%.2f%%", Float.parseFloat(freq[1]) * 100);
            i[0]++;
          });
    } catch (Exception e) {
      System.out.println("Couldn't read csv frequencies file");
      System.exit(1);
    }

    String[] columnNames = {"Encrypted Letter", "This Game", "English Language"};

    JTable table = new JTable(data, columnNames);
    table.setDefaultEditor(Object.class, null); // make table non-editable
    table.setRowHeight(30);

    JScrollPane scrollPane =
        new JScrollPane(
            table,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    this.add(buttonPanel, BorderLayout.SOUTH);
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(_ -> this.dispose());
    buttonPanel.add(closeButton);

    gui.resizeFont(this, gui.getFontChangeTotal());
  }
}
