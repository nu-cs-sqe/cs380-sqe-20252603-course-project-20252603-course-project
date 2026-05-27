package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    assertThrows(
        IllegalStateException.class,
        () -> LocaleLoader.loadLocalesFromPath("non-existent-file.properties"),
        "TC2 Failure: System must throw an IllegalStateException when the resource stream resolves to null."
    );
  }

  @Test
  public void testTestCase3_EmptyConfigurationYieldsEmptyMap() {
    Map<Locale, String> locales = LocaleLoader.loadLocalesFromPath("empty-locales.properties");

    assertNotNull(locales, "TC3 Failure: Retrieved map instance should not be null.");
    assertTrue(locales.isEmpty(), "TC3 Failure: Map should be empty for an empty configuration profile.");
    assertEquals(0, locales.size(), "TC3 Failure: Map size should be 0 for an empty configuration profile.");
  }

  @Test
  public void testTestCase4And5_VerifyNoEmptyOrBlankBundleKeys() {
    Map<Locale, String> locales = LocaleLoader.getSupportedLocales();

    for (Map.Entry<Locale, String> entry : locales.entrySet()) {
      String value = entry.getValue();

      // TC4: Check against empty strings
      assertNotNull(value, "TC4 Failure: Resource key string cannot be null.");
      assertFalse(value.isEmpty(), "TC4 Failure: Key mapping value cannot be empty (e.g. 'en-US=').");

      // TC5: Check against blank whitespace configurations
      assertFalse(value.trim().isEmpty(), "TC5 Failure: Key mapping value cannot consist solely of whitespace characters.");
    }
  }

  @Test
  public void testTestCase6_ReturnedMapIsStrictlyImmutable() {
    Map<Locale, String> locales = LocaleLoader.getSupportedLocales();
    assertNotNull(locales, "TC6 Failure: Target collection is null.");

    Locale rogueLocale = Locale.forLanguageTag("fr-FR");
    String rogueKey = "language.french";

    // Verify that mutating mutations throw an UnsupportedOperationException
    assertThrows(UnsupportedOperationException.class, () -> {
      locales.put(rogueLocale, rogueKey);
    }, "TC6 Failure: Mutating getSupportedLocales() via .put() must throw an UnsupportedOperationException.");

    assertThrows(UnsupportedOperationException.class, () -> {
      locales.clear();
    }, "TC6 Failure: Mutating getSupportedLocales() via .clear() must throw an UnsupportedOperationException.");

    assertThrows(UnsupportedOperationException.class, () -> {
      if (!locales.isEmpty()) {
        locales.remove(locales.keySet().iterator().next());
      }
    }, "TC6 Failure: Mutating getSupportedLocales() via .remove() must throw an UnsupportedOperationException.");
  }
}