package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class GameStateTests {

  @Test
  void gameState_WhiteTurnAndBlackTurn_AreDistinct() {
    assertNotEquals(GameState.WHITE_TURN, GameState.BLACK_TURN);
  }

  @Test
  void gameState_WhiteTurn_HasOrdinalZero() {
    assertEquals(0, GameState.WHITE_TURN.ordinal());
  }

  @Test
  void gameState_BlackTurn_HasOrdinalOne() {
    assertEquals(1, GameState.BLACK_TURN.ordinal());
  }
}