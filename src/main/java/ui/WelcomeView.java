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

  private static final int FIELD_COLUMNS = 16;
  private static final int PANEL_PADDING_V = 20;
  private static final int PANEL_PADDING_H = 30;
  private static final int COMPONENT_INSET = 6;
  private static final String BUNDLE_NAME = "MessagesBundle";

  private JTextField player1NameField;
  private JTextField player2NameField;
  private ResourceBundle bundle;
  private Locale selectedLocale;

  public WelcomeView(Locale locale) {
    this.selectedLocale = locale;
    this.bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    buildWelcomeUi();
  }

  ResourceBundle getBundle() {
    return bundle;
  }

  private void buildWelcomeUi() {
    setTitle(bundle.getString("welcome.title"));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    add(buildPanel());
    pack();
    setLocationRelativeTo(null);
  }

  private JPanel buildPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(
        PANEL_PADDING_V, PANEL_PADDING_H, PANEL_PADDING_V, PANEL_PADDING_H));
    player1NameField = new JTextField(FIELD_COLUMNS);
    player2NameField = new JTextField(FIELD_COLUMNS);
    addPlayerFields(panel);
    addStartButton(panel);
    return panel;
  }

  private GridBagConstraints createGbc() {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(COMPONENT_INSET, COMPONENT_INSET, COMPONENT_INSET, COMPONENT_INSET);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    return gbc;
  }

  private void addPlayerFields(JPanel panel) {
    GridBagConstraints gbc = createGbc();
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel(bundle.getString("welcome.player1Label")), gbc);
    gbc.gridx = 1;
    panel.add(player1NameField, gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel(bundle.getString("welcome.player2Label")), gbc);
    gbc.gridx = 1;
    panel.add(player2NameField, gbc);
  }

  private void addStartButton(JPanel panel) {
    GridBagConstraints gbc = createGbc();
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;
    JButton startButton = new JButton(bundle.getString("welcome.startButton"));
    panel.add(startButton, gbc);
    startButton.addActionListener(e -> onStartClicked());
  }

  private void onStartClicked() {
    String p1 = player1NameField.getText().trim();
    String p2 = player2NameField.getText().trim();
    if (p1.isEmpty() || p2.isEmpty()) {
      JOptionPane.showMessageDialog(this,
          bundle.getString("welcome.missingNameMessage"),
          bundle.getString("welcome.missingNameTitle"),
          JOptionPane.WARNING_MESSAGE);
      return;
    }
    MainView mainView = new MainView(p1, p2, selectedLocale);
    mainView.pack();
    mainView.setLocationRelativeTo(null);
    mainView.setVisible(true);
    dispose();
  }
}
