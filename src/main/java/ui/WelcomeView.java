package ui;

import javax.swing.*;
import java.awt.*;

public class WelcomeView extends JFrame {

  private JTextField player1NameField;
  private JTextField player2NameField;

  public WelcomeView() {
    createWelcomeScreenUI();
  }

  private void createWelcomeScreenUI() {
    setTitle("Chess — Welcome");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(6, 6, 6, 6);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    player1NameField = new JTextField(16);
    player2NameField = new JTextField(16);

    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("Player 1 name:"), gbc);
    gbc.gridx = 1;
    panel.add(player1NameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel("Player 2 name:"), gbc);
    gbc.gridx = 1;
    panel.add(player2NameField, gbc);

    JButton startButton = new JButton("Start Game");
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(startButton, gbc);

    startButton.addActionListener(e -> {
      String p1 = player1NameField.getText().trim();
      String p2 = player2NameField.getText().trim();
      if (p1.isEmpty() || p2.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Please enter a name for both players.",
            "Missing Name", JOptionPane.WARNING_MESSAGE);
        return;
      }
      MainView mainView = new MainView(p1, p2);
      mainView.pack();
      mainView.setLocationRelativeTo(null);
      mainView.setVisible(true);
      dispose();
    });

    add(panel);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }
}
