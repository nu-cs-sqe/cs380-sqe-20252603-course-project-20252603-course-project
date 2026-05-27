package ui;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTests {

  @Test
  void mainRunsWithoutCrash() {
    Main.main(new String[]{});
  }

  @Test
  void mainHandlesUnusedArgs() {
    Main.main(new String[]{"test"});
  }

  @Test
  void mainHandlesNullArgs() {
    Main.main(null);
  }

  @Test
  void mainCreatesView() {
    Main.main(new String[]{});
  }

  @Test
  void welcomeViewIsVisible() {
    Map<Locale, String> supportedLocales = LocaleLoader.getSupportedLocales();
    final String BUNDLE_BASE_NAME = "MessagesBundle";
    Locale targetLocale = supportedLocales.isEmpty()
        ? Locale.getDefault()
        : supportedLocales.keySet().iterator().next();
    ResourceBundle messages = ResourceBundle.getBundle(BUNDLE_BASE_NAME, targetLocale);

    SwingUtilities.invokeLater(() -> {
      Main.main(new String[]{});

      assertDoesNotThrow(() -> new WelcomeView(messages).setVisible(true));
    });
  }

  @Test
  void uiLaunchRunsOnEdt() throws Exception {

    final boolean[] ranOnEdt = {false};

    SwingUtilities.invokeAndWait(() -> {
      Main.main(new String[]{});

      ranOnEdt[0] = SwingUtilities.isEventDispatchThread();
    });

    assertTrue(ranOnEdt[0]);
  }
}
