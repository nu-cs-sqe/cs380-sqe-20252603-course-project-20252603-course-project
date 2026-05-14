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

    // ── switchTurn ───────────────────────────────────────────────────────────

    @Test
    void switchTurn_WhiteTurn_SwitchesToBlackTurn() {
        board.switchTurn();
        assertEquals(GameState.BLACK_TURN, board.getCurrentGameState());
    }

    @Test
    void switchTurn_BlackTurn_SwitchesToWhiteTurn() {
        board.switchTurn();
        board.switchTurn();
        assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
    }

    // ── updateWhiteKingLocation ──────────────────────────────────────────────

    @Test
    void updateWhiteKingLocation_NullLocation_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> board.updateWhiteKingLocation(null));
    }

    @Test
    void updateWhiteKingLocation_MinCorner_UpdatesLocation() {
        Location loc = new Location(0, 0);
        board.updateWhiteKingLocation(loc);
        assertEquals(loc, board.whiteKingLocation);
    }

    @Test
    void updateWhiteKingLocation_MaxCorner_UpdatesLocation() {
        Location loc = new Location(7, 7);
        board.updateWhiteKingLocation(loc);
        assertEquals(loc, board.whiteKingLocation);
    }

    @Test
    void updateWhiteKingLocation_Interior_UpdatesLocation() {
        Location loc = new Location(4, 4);
        board.updateWhiteKingLocation(loc);
        assertEquals(loc, board.whiteKingLocation);
    }

    // ── updateBlackKingLocation ──────────────────────────────────────────────

    @Test
    void updateBlackKingLocation_NullLocation_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> board.updateBlackKingLocation(null));
    }

    @Test
    void updateBlackKingLocation_MinCorner_UpdatesLocation() {
        Location loc = new Location(0, 0);
        board.updateBlackKingLocation(loc);
        assertEquals(loc, board.blackKingLocation);
    }

    @Test
    void updateBlackKingLocation_MaxCorner_UpdatesLocation() {
        Location loc = new Location(7, 7);
        board.updateBlackKingLocation(loc);
        assertEquals(loc, board.blackKingLocation);
    }

    @Test
    void updateBlackKingLocation_Interior_UpdatesLocation() {
        Location loc = new Location(4, 4);
        board.updateBlackKingLocation(loc);
        assertEquals(loc, board.blackKingLocation);
    }

    // ── checkNotMoveIntoCheck ────────────────────────────────────────────────

    @Test
    void checkNotMoveIntoCheck_NullFrom_ThrowsIllegalArgumentException() {
        Location to = new Location(4, 4);
        assertThrows(IllegalArgumentException.class,
                () -> board.checkNotMoveIntoCheck(null, to));
    }

    @Test
    void checkNotMoveIntoCheck_NullTo_ThrowsIllegalArgumentException() {
        Location from = new Location(4, 4);
        assertThrows(IllegalArgumentException.class,
                () -> board.checkNotMoveIntoCheck(from, null));
    }
}
