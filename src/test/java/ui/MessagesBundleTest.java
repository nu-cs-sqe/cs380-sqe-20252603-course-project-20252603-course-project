package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.jupiter.api.Test;

class MessagesBundleTest {

  private static final String BUNDLE_NAME = "MessagesBundle";

  private static final Set<String> REQUIRED_KEYS = Set.of(
      "game.currentPlayer",
      "mainWindow.title",
      "welcome.missingNameMessage",
      "welcome.missingNameTitle",
      "welcome.player1Label",
      "welcome.player2Label",
      "welcome.startButton",
      "welcome.title"
  );

  @Test
  void messagesBundle_LoadWithLocaleUS_IsNotNull() {
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.US);
    assertNotNull(bundle);
  }

  @Test
  void messagesBundle_DefaultBundle_ContainsAllRequiredKeys() {
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT);
    for (String key : REQUIRED_KEYS) {
      assertTrue(bundle.containsKey(key), "Default bundle missing key: " + key);
    }
  }

  @Test
  void messagesBundle_EnUsBundle_ContainsSameKeysAsDefault() {
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.US);
    assertEquals(new Locale("en", "US"), bundle.getLocale());
    for (String key : REQUIRED_KEYS) {
      assertTrue(bundle.containsKey(key), "en_US bundle missing key: " + key);
    }
  }
}
