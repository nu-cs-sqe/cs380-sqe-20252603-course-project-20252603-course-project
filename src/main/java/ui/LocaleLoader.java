package ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Utility helper responsible for reading the supported application locales from properties configuration.
 */
public class LocaleLoader {

  private LocaleLoader() {}

  private static final String CONFIG_PATH = "supported-locales.properties";
  private static final Map<Locale, String> supportedLocales;

  static {
    // Initialize the real map using the default configuration file path
    supportedLocales = Collections.unmodifiableMap(loadLocalesFromPath(CONFIG_PATH));
  }

  /**
   * Helper method extracted to accept an injectable resource path for testing (Clean Code T1).
   * It handles reading the stream and parsing properties into a working map.
   */
  static Map<Locale, String> loadLocalesFromPath(String resourcePath) {
    Map<Locale, String> workingMap = new LinkedHashMap<>();
    Properties properties = new Properties();

    try (InputStream input = LocaleLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
      if (input == null) {
        throw new IllegalStateException("Critical Configuration Error: " + resourcePath + " not found on classpath.");
      }

      properties.load(input);

      for (String localeTag : properties.stringPropertyNames()) {
        Locale locale = Locale.forLanguageTag(localeTag);
        String displayNameKey = properties.getProperty(localeTag);
        workingMap.put(locale, displayNameKey);
      }

    } catch (IOException e) {
      throw new RuntimeException("Failed to initialize system locales from configuration properties.", e);
    }

    return workingMap;
  }

  /**
   * Returns the parsed system locales mapped to their UI string resource keys.
   * @return Immutable Map containing Locale references and translation bundle keys.
   */
  public static Map<Locale, String> getSupportedLocales() {
    return supportedLocales;
  }
}