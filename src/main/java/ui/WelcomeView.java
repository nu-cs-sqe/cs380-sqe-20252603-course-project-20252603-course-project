package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class WelcomeView extends JFrame {

  private static final int TEXT_FIELD_COLUMNS = 16;
  private static final int PADDING_TOP_BOTTOM = 20;
  private static final int PADDING_LEFT_RIGHT = 30;
  private static final int INSET_SPACING = 6;
  private static final boolean IS_WINDOW_RESIZABLE = false;

  private JTextField player1NameField;
  private JTextField player2NameField;
  private final ResourceBundle messages;

  public WelcomeView() {
    initWelcomeScreen();
  }

  private void initWelcomeScreen() {
    setTitle("Chess — Welcome");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(
        PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT,
        PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(INSET_SIZE, INSET_SIZE, INSET_SIZE, INSET_SIZE);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    player1NameField = new JTextField(FIELD_COLUMNS);
    player2NameField = new JTextField(FIELD_COLUMNS);

    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(new JLabel(messages.getString("welcome.player1Label")), gbc);
    gbc.gridx = 1;
    panel.add(player1NameField, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(new JLabel(messages.getString("welcome.player2Label")), gbc);
    gbc.gridx = 1;
    panel.add(player2NameField, gbc);

    JButton startButton = new JButton(messages.getString("welcome.startButton"));
    gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;

    JButton startButton = new JButton("Start Game");
    panel.add(startButton, gbc);
    startButton.addActionListener(e -> handleStartGame());

    add(panel);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void handleStartGame() {
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
  }
}