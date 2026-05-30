package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
  private JLabel player1Label;
  private JLabel player2Label;
  private JLabel languageLabel;
  private JButton startButton;
  private JComboBox<LanguageOption> languageSelector;
  private ResourceBundle messages;
  private boolean isUpdating = false;

  private static class LanguageOption {
    private final Locale locale;
    private final String displayName;

    public LanguageOption(Locale locale, String displayName) {
      this.locale = locale;
      this.displayName = displayName;
    }

    @Override
    public String toString() {
      return displayName;
    }
  }

  public WelcomeView(Locale locale) {
    this(ResourceBundle.getBundle(UiConstants.BUNDLE_NAME, locale));
  }

  public WelcomeView(ResourceBundle messages) {
    this.messages = messages;
    initWelcomeScreen();
  }

  private void initWelcomeScreen() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    JPanel panel = buildMainPanel();
    add(panel);
    refreshLabels();
    pack();
    setLocationRelativeTo(null);
  }

  private JPanel buildMainPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(
        PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT,
        PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT));

    addLanguageSelector(panel);
    addPlayerFields(panel);
    addStartButton(panel);
    return panel;
  }

  private void addLanguageSelector(JPanel panel) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(INSET_SPACING, INSET_SPACING, INSET_SPACING, INSET_SPACING);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    languageLabel = new JLabel();
    languageSelector = new JComboBox<>();

    Map<Locale, String> locales = LocaleLoader.getSupportedLocales();
    LanguageOption selectedOption = null;
    for (Map.Entry<Locale, String> entry : locales.entrySet()) {
      LanguageOption option = new LanguageOption(entry.getKey(), messages.getString(entry.getValue()));
      languageSelector.addItem(option);
      if (entry.getKey().equals(messages.getLocale())) {
        selectedOption = option;
      }
    }

    if (selectedOption != null) {
      languageSelector.setSelectedItem(selectedOption);
    }

    languageSelector.addActionListener(e -> {
      if (isUpdating) {
        return;
      }
      LanguageOption option = (LanguageOption) languageSelector.getSelectedItem();
      if (option != null) {
        updateLocale(option.locale);
      }
    });

    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(languageLabel, gbc);
    gbc.gridx = 1;
    panel.add(languageSelector, gbc);
  }

  void updateLocale(Locale locale) {
    isUpdating = true;
    try {
      this.messages = ResourceBundle.getBundle(UiConstants.BUNDLE_NAME, locale);
      refreshLabels();
    } finally {
      isUpdating = false;
    }
  }

  void refreshLabels() {
    setTitle(messages.getString("welcome.title"));
    languageLabel.setText(messages.getString("welcome.languageLabel"));
    player1Label.setText(messages.getString("welcome.player1Label"));
    player2Label.setText(messages.getString("welcome.player2Label"));
    startButton.setText(messages.getString("welcome.startButton"));

    // Update language names in selector
    Map<Locale, String> locales = LocaleLoader.getSupportedLocales();
    for (int i = 0; i < languageSelector.getItemCount(); i++) {
      LanguageOption option = languageSelector.getItemAt(i);
      String newDisplayName = messages.getString(locales.get(option.locale));
      // We need to update the display name. Since LanguageOption is immutable here, 
      // we might need a different approach or just recreate options.
      // But actually, we can just replace the items or have LanguageOption use messages.
    }
    // Simplest is to recreate options if we want them to change language too.
    LanguageOption currentSelected = (LanguageOption) languageSelector.getSelectedItem();
    languageSelector.removeAllItems();
    for (Map.Entry<Locale, String> entry : locales.entrySet()) {
      LanguageOption option = new LanguageOption(entry.getKey(), messages.getString(entry.getValue()));
      languageSelector.addItem(option);
      if (currentSelected != null && option.locale.equals(currentSelected.locale)) {
        languageSelector.setSelectedItem(option);
      }
    }
  }

  private void addPlayerFields(JPanel panel) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(INSET_SPACING, INSET_SPACING, INSET_SPACING, INSET_SPACING);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    player1NameField = new JTextField(TEXT_FIELD_COLUMNS);
    player2NameField = new JTextField(TEXT_FIELD_COLUMNS);
    player1Label = new JLabel();
    player2Label = new JLabel();

    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(player1Label, gbc);
    gbc.gridx = 1;
    panel.add(player1NameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(player2Label, gbc);
    gbc.gridx = 1;
    panel.add(player2NameField, gbc);
  }

  private void addStartButton(JPanel panel) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(INSET_SPACING, INSET_SPACING, INSET_SPACING, INSET_SPACING);
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;

    startButton = new JButton();
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
