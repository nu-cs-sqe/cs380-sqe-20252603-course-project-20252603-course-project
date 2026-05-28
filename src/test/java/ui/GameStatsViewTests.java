package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class GameStatsViewTests {

  private final ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", Locale.US);

  @Test
  void panelIsCreated() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    assertNotNull(view);
  }

  @Test
  void layoutIsBoxLayoutYAxis() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    assertTrue(view.getLayout() instanceof BoxLayout);
  }

  @Test
  void hasFourComponents() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    assertEquals(4, view.getComponentCount());
  }

  @Test
  void player1LabelCorrect() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    JLabel label = (JLabel) view.getComponent(1);

    assertTrue(label.getText().contains("Alice"));
    assertTrue(label.getText().contains("White"));
  }

  @Test
  void player2LabelCorrect() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    JLabel label = (JLabel) view.getComponent(2);

    assertTrue(label.getText().contains("Bob"));
    assertTrue(label.getText().contains("Black"));
  }

  @Test
  void currentPlayerStartsAsPlayer1() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    JLabel label = (JLabel) view.getComponent(3);

    assertTrue(label.getText().contains("Alice"));
  }

  @Test
  void labelsHaveStyling() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    JLabel label = (JLabel) view.getComponent(0);

    assertEquals(new Font("Arial", Font.BOLD, 30), label.getFont());
  }

  @Test
  void backgroundColorCorrect() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    assertEquals(new Color(104, 76, 150), view.getBackground());
  }

  @Test
  void labelsAreInCorrectOrder() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    assertEquals("Player Information", ((JLabel) view.getComponent(0)).getText());
  }

  @Test
  void nullNamesDoNotCrash() {
    GameStatsView view = new GameStatsView(null, "Bob", messages);

    assertNotNull(view);
  }
}
