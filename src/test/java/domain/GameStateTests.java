package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameStateTests {

    @Test
    void GameState_WhiteTurn_IsNonNullWithCorrectName() {
        assertNotNull(GameState.WHITE_TURN);
        assertEquals("WHITE_TURN", GameState.WHITE_TURN.name());
    }

    @Test
    void GameState_BlackTurn_IsNonNullWithCorrectName() {
        assertNotNull(GameState.BLACK_TURN);
        assertEquals("BLACK_TURN", GameState.BLACK_TURN.name());
    }
}
