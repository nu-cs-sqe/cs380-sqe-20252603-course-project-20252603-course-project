package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.GameState;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class GameStatsViewTests {

  private final ResourceBundle messages =
      ResourceBundle.getBundle("MessagesBundle", Locale.US);

  @Test
  void Constructor_ValidInput_CreatesNonNullInstance() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);
    assertNotNull(view);
  }

  @Test
  void Layout_ValidConstruction_IsBoxLayoutYAxis() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);
    assertTrue(view.getLayout() instanceof BoxLayout);
  }

  @Test
  void ComponentCount_ValidConstruction_HasFourComponents() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);
    assertEquals(4, view.getComponentCount());
  }

  @Test
  void Player1Label_ValidInput_ContainsNameAndTeam() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    JLabel label = (JLabel) view.getComponent(1);

    assertTrue(label.getText().contains("Alice"));
    assertTrue(label.getText().toLowerCase().contains("white"));
  }

  @Test
  void Player2Label_ValidInput_ContainsNameAndTeam() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    JLabel label = (JLabel) view.getComponent(2);

    assertTrue(label.getText().contains("Bob"));
    assertTrue(label.getText().toLowerCase().contains("black"));
  }

  @Test
  void CurrentPlayerLabel_InitialState_ShowsPlayer1() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    JLabel label = (JLabel) view.getComponent(3);

    assertTrue(label.getText().contains("Alice"));
  }

  @Test
  void Styling_HeaderLabel_UsesCorrectFont() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    JLabel label = (JLabel) view.getComponent(0);

    assertEquals(new Font("Arial", Font.BOLD, 30), label.getFont());
  }

  @Test
  void Background_ValidConstruction_HasExpectedColor() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    assertEquals(new Color(104, 76, 150), view.getBackground());
  }

  @Test
  void LabelOrder_ValidConstruction_ComponentsAreNonNull() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    assertNotNull(((JLabel) view.getComponent(0)).getText());
    assertNotNull(((JLabel) view.getComponent(1)).getText());
    assertNotNull(((JLabel) view.getComponent(2)).getText());
    assertNotNull(((JLabel) view.getComponent(3)).getText());
  }

  @Test
  void Constructor_NullPlayerName_DoesNotCrash() {
    GameStatsView view = new GameStatsView(null, "Bob", messages);
    assertNotNull(view);
  }

  @Test
  void UpdateGameState_BlackTurn_ShowsPlayer2() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    view.updateGameState(GameState.BLACK_TURN, "Alice", "Bob");

    JLabel label = (JLabel) view.getComponent(3);

    assertTrue(label.getText().contains("Bob"));
  }

  @Test
  void UpdateGameState_WhiteWin_ShowsPlayer1Winner() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    view.updateGameState(GameState.WHITE_WIN, "Alice", "Bob");

    JLabel label = (JLabel) view.getComponent(3);

    assertTrue(label.getText().contains("Alice"));
  }

  @Test
  void UpdateGameState_Draw_ShowsDrawState() {
    GameStatsView view = new GameStatsView("Alice", "Bob", messages);

    view.updateGameState(GameState.DRAW, "Alice", "Bob");

    JLabel label = (JLabel) view.getComponent(3);

    assertTrue(label.getText().toLowerCase().contains("draw"));
  }
}