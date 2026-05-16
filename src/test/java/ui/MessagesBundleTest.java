package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    assertFalse(title.isBlank(), "mainWindow.title should not be blank in en_US bundle");
  }

  @Test
  void messagesBundle_DefaultBundle_ContainsAllRequiredKeys() {
    assertContainsAllRequiredKeys(ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT));
  }

  @Test
  void messagesBundle_EsUsBundle_ContainsSameKeysAsDefault() {
    assertSameKeysAsDefault(new Locale("es", "US"));
  }

  @Test
  void messagesBundle_EnUsBundle_ContainsSameKeysAsDefault() {
    assertEquals(Locale.US, ResourceBundle.getBundle(BUNDLE_NAME, Locale.US).getLocale());
    assertSameKeysAsDefault(Locale.US);
  }

  private void assertContainsAllRequiredKeys(ResourceBundle bundle) {
    for (String key : REQUIRED_KEYS) {
      assertTrue(bundle.containsKey(key), bundle.getLocale() + " bundle missing key: " + key);
    }
  }

  private void assertSameKeysAsDefault(Locale locale) {
    ResourceBundle defaultBundle = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT);
    ResourceBundle localeBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    Set<String> defaultKeys = new HashSet<>(Collections.list(defaultBundle.getKeys()));
    Set<String> localeKeys = new HashSet<>(Collections.list(localeBundle.getKeys()));
    assertEquals(defaultKeys, localeKeys, locale + " bundle key set differs from default bundle key set");
  }
}
