package ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.awt.GraphicsEnvironment;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WelcomeViewTest {

  @BeforeAll
  static void skipIfHeadless() {
    assumeFalse(GraphicsEnvironment.isHeadless(),
        "Skipping WelcomeView tests in headless environment");
  }

  @Test
  void welcomeView_ConstructWithEnUsLocale_DoesNotThrow() {
    assertDoesNotThrow(() -> new WelcomeView(Locale.US));
  }

  @Test
  void welcomeView_ConstructWithEsUsLocale_BundleLocaleMatchesRequested() {
    Locale esUs = new Locale("es", "US");
    WelcomeView view = new WelcomeView(esUs);
    assertEquals(esUs, view.getBundle().getLocale());
  }

  @Test
  void welcomeView_ConstructWithRootLocale_DoesNotThrow() {
    assertDoesNotThrow(() -> new WelcomeView(Locale.ROOT));
  }

  @Test
  void welcomeView_ConstructWithUnsupportedLocale_BundleFallsBackToDefault() {
    WelcomeView view = new WelcomeView(new Locale("fr", "FR"));
    assertEquals(Locale.ROOT, view.getBundle().getLocale());
  }

  @Test
  void welcomeView_ConstructWithNullLocale_ThrowsNullPointerException() {
    assertThrows(NullPointerException.class, () -> new WelcomeView(null));
  }
}
