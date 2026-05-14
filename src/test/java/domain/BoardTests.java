package domain;

import domain.piece.*;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTests {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    // ── getCurrentGameState ──────────────────────────────────────────────────

    @Test
    void getCurrentGameState_NewBoard_ReturnsWhiteTurn() {
        assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
    }

    @Test
    void getCurrentGameState_AfterOneSwitchTurn_ReturnsBlackTurn() {
        board.switchTurn();
        assertEquals(GameState.BLACK_TURN, board.getCurrentGameState());
    }
}
