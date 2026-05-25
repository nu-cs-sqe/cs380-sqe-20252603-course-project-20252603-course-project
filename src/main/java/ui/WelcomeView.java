package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class WelcomeView extends JFrame {

  // Dimensional Constants to eliminate Magic Numbers
  private static final int TEXT_FIELD_COLUMNS = 16;
  private static final int PADDING_TOP_BOTTOM = 20;
  private static final int PADDING_LEFT_RIGHT = 30;
  private static final int INSET_SPACING = 6;
  private static final boolean IS_WINDOW_RESIZABLE = false;

  private JTextField player1NameField;
  private JTextField player2NameField;
  private final ResourceBundle messages; // Localization bundle instance

  public WelcomeView(ResourceBundle messages) {
    this.messages = messages;
    createWelcomeScreenUI();
  }

  private void createWelcomeScreenUI() {
    // Audit Fixed: String constants extracted to bundles
    setTitle(messages.getString("ui.welcome.title"));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setResizable(IS_WINDOW_RESIZABLE);

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(
        PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT, PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT
    ));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(INSET_SPACING, INSET_SPACING, INSET_SPACING, INSET_SPACING);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    player1NameField = new JTextField(TEXT_FIELD_COLUMNS);
    player2NameField = new JTextField(TEXT_FIELD_COLUMNS);

    // Row 0: Player 1
    gbc.gridx = 0; gbc.gridy = 0;
    panel.add(new JLabel(messages.getString("ui.welcome.player1Label")), gbc);
    gbc.gridx = 1;
    panel.add(player1NameField, gbc);

    // Row 1: Player 2
    gbc.gridx = 0; gbc.gridy = 1;
    panel.add(new JLabel(messages.getString("ui.welcome.player2Label")), gbc);
    gbc.gridx = 1;
    panel.add(player2NameField, gbc);

    // Row 2: Action Button
    JButton startButton = new JButton(messages.getString("ui.welcome.startButton"));
    gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(startButton, gbc);

    startButton.addActionListener(e -> {
      String p1 = player1NameField.getText().trim();
      String p2 = player2NameField.getText().trim();

      if (p1.isEmpty() || p2.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            messages.getString("ui.welcome.missingNameMessage"),
            messages.getString("ui.welcome.missingNameTitle"),
            JOptionPane.WARNING_MESSAGE);
        return;
      }

      // FIXES COMPILATION ERROR: Pass down the bundle resource reference cleanly
      MainView mainView = new MainView(p1, p2, messages);
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