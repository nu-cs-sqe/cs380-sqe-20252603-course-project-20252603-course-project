package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WelcomeView extends JFrame {

  private static final int TEXT_FIELD_COLUMNS = 16;
  private static final int PADDING_TOP_BOTTOM = 20;
  private static final int PADDING_LEFT_RIGHT = 30;
  private static final int INSET_SPACING = 6;

  private JTextField player1NameField;
  private JTextField player2NameField;
  private final ResourceBundle messages;

  public WelcomeView(Locale locale) {
    this(ResourceBundle.getBundle(UiConstants.BUNDLE_NAME, locale));
  }

  public WelcomeView(ResourceBundle messages) {
    this.messages = messages;
    initWelcomeScreen();
  }

  private void initWelcomeScreen() {
    setTitle(messages.getString("welcome.title"));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    JPanel panel = buildMainPanel();
    add(panel);
    pack();
    setLocationRelativeTo(null);
  }

  private JPanel buildMainPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(
        PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT,
        PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT));

    addPlayerFields(panel);
    addStartButton(panel);
    return panel;
  }

  private void addPlayerFields(JPanel panel) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(INSET_SPACING, INSET_SPACING, INSET_SPACING, INSET_SPACING);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    player1NameField = new JTextField(TEXT_FIELD_COLUMNS);
    player2NameField = new JTextField(TEXT_FIELD_COLUMNS);

    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel(messages.getString("welcome.player1Label")), gbc);
    gbc.gridx = 1;
    panel.add(player1NameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel(messages.getString("welcome.player2Label")), gbc);
    gbc.gridx = 1;
    panel.add(player2NameField, gbc);
  }

  private void addStartButton(JPanel panel) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(INSET_SPACING, INSET_SPACING, INSET_SPACING, INSET_SPACING);
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;

    JButton startButton = new JButton(messages.getString("welcome.startButton"));
    panel.add(startButton, gbc);
    startButton.addActionListener(e -> handleStartGame());
  }

  private void handleStartGame() {
    String p1 = player1NameField.getText().trim();
    String p2 = player2NameField.getText().trim();

    if (p1.isEmpty() || p2.isEmpty()) {
      JOptionPane.showMessageDialog(this,
          messages.getString("welcome.missingNameMessage"),
          messages.getString("welcome.missingNameTitle"),
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    MainView mainView = new MainView(p1, p2, messages);
    mainView.pack();
    mainView.setLocationRelativeTo(null);
    mainView.setVisible(true);
    dispose();
  }
}
