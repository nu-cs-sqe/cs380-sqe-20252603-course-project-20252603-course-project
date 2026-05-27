package ui;

import javax.swing.SwingUtilities;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Launches the chess application.
 */
public class Main {

  private static final String BUNDLE_BASE_NAME = "MessagesBundle";

  /**
   * Starts the application UI.
   */
  public static void main(String[] args) {
    Map<Locale, String> supportedLocales = LocaleLoader.getSupportedLocales();

    Locale targetLocale = supportedLocales.isEmpty()
        ? Locale.getDefault()
        : supportedLocales.keySet().iterator().next();

    ResourceBundle messages = ResourceBundle.getBundle(BUNDLE_BASE_NAME, targetLocale);

    SwingUtilities.invokeLater(() -> {
      WelcomeView welcomeView = new WelcomeView(messages);
      welcomeView.setVisible(true);
    });
  }
}