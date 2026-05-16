package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
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
  void messagesBundle_LoadWithLocaleUS_HasExpectedTitle() {
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.US);
    String title = bundle.getString("mainWindow.title");
    assertNotNull(title);
    assertTrue(!title.isBlank(), "mainWindow.title should not be blank in en_US bundle");
  }

  @Test
  void messagesBundle_DefaultBundle_ContainsAllRequiredKeys() {
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT);
    for (String key : REQUIRED_KEYS) {
      assertTrue(bundle.containsKey(key), "Default bundle missing key: " + key);
    }
  }

  @Test
  void messagesBundle_EsUsBundle_ContainsAllRequiredKeys() {
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale("es", "US"));
    for (String key : REQUIRED_KEYS) {
      assertTrue(bundle.containsKey(key), "es_US bundle missing key: " + key);
    }
  }

  @Test
  void messagesBundle_EnUsBundle_ContainsSameKeysAsDefault() {
    ResourceBundle defaultBundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT);
    ResourceBundle enUsBundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.US);
    assertEquals(new Locale("en", "US"), enUsBundle.getLocale());
    Set<String> defaultKeys = new HashSet<>(Collections.list(defaultBundle.getKeys()));
    Set<String> enUsKeys = new HashSet<>(Collections.list(enUsBundle.getKeys()));
    assertEquals(defaultKeys, enUsKeys, "en_US bundle key set differs from default bundle key set");
  }
}
