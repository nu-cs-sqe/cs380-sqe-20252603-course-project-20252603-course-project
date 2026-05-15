package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GameStateTests {

    @Test
    void GameState_WhiteTurnAndBlackTurn_AreDistinct() {
        assertNotEquals(GameState.WHITE_TURN, GameState.BLACK_TURN);
    }

    @Test
    void GameState_WhiteTurn_HasOrdinalZero() {
        assertEquals(0, GameState.WHITE_TURN.ordinal());
    }

    @Test
    void GameState_BlackTurn_HasOrdinalOne() {
        assertEquals(1, GameState.BLACK_TURN.ordinal());
    }
}
