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
  void welcomeView_ConstructWithEsUsLocale_TitleIsLocalised() {
    Locale esUs = new Locale("es", "US");
    WelcomeView view = new WelcomeView(esUs);
    assertEquals("Ajedrez — Bienvenido", view.getTitle());
  }

  @Test
  void welcomeView_ConstructWithRootLocale_DoesNotThrow() {
    assertDoesNotThrow(() -> new WelcomeView(Locale.ROOT));
  }

  @Test
  void welcomeView_ConstructWithUnsupportedLocale_TitleUsesDefaultBundle() {
    WelcomeView view = new WelcomeView(new Locale("fr", "FR"));
    assertEquals("Chess — Welcome", view.getTitle());
  }

  @Test
  void welcomeView_ConstructWithNullLocale_ThrowsNullPointerException() {
    assertThrows(NullPointerException.class, () -> new WelcomeView((Locale) null));
  }

  @Test
  void welcomeView_SwitchLanguage_UpdatesTitle() {
    WelcomeView view = new WelcomeView(Locale.US);
    assertEquals("Chess — Welcome", view.getTitle());

    // Switch to Spanish
    Locale esUs = new Locale("es", "US");
    view.updateLocale(esUs);
    assertEquals("Ajedrez — Bienvenido", view.getTitle());
  }
}
