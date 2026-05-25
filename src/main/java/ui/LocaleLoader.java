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
    Map<Locale, String> workingMap = new LinkedHashMap<>();
    Properties properties = new Properties();

    try (InputStream input = LocaleLoader.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {
      if (input == null) {
        throw new IllegalStateException("Critical Configuration Error: " + CONFIG_PATH + " not found on classpath.");
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

    supportedLocales = Collections.unmodifiableMap(workingMap);
  }

  /**
   * Returns the parsed system locales mapped to their UI string resource keys.
   * @return Immutable Map containing Locale references and translation bundle keys.
   */
  public static Map<Locale, String> getSupportedLocales() {
    return supportedLocales;
  }
}