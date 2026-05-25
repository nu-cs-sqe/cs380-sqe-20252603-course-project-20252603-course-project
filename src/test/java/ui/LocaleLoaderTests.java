package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
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

  @Test
  public void testTestCase2_MissingResourceThrowsException() {
    assertThrows(IllegalStateException.class, () -> {
      String fakePath = "non-existent-file.properties";
      InputStream input = LocaleLoader.class.getClassLoader().getResourceAsStream(fakePath);
      if (input == null) {
        throw new IllegalStateException("Critical Configuration Error: " + fakePath + " not found on classpath.");
      }
    }, "TC2 Failure: System must throw an IllegalStateException when the resource stream resolves to null.");
  }
}