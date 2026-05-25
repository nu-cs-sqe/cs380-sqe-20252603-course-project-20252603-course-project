package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.Locale;

public class LocaleLoaderTests {

  @Test
  public void testGetSupportedLocalesParsesValidBaselineEntries() {
    Map<Locale, String> locales = LocaleLoader.getSupportedLocales();

    assertNotNull(locales, "The returned supported locales map must not be null.");
    assertEquals(2, locales.size(), "The map should contain exactly 2 registered locales based on the properties file.");

    Locale enUS = Locale.forLanguageTag("en-US");
    Locale esUS = Locale.forLanguageTag("es-US");

    assertTrue(locales.containsKey(enUS), "The map must dynamically parse and contain the 'en-US' locale.");
    assertTrue(locales.containsKey(esUS), "The map must dynamically parse and contain the 'es-US' locale.");

    assertEquals("language.english", locales.get(enUS), "The parsed resource key for en-US mapping is incorrect.");
    assertEquals("language.spanish", locales.get(esUS), "The parsed resource key for es-US mapping is incorrect.");
  }
}