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
        assertEquals(loc, board.getWhiteKingLocation());
    }

    @Test
    void updateWhiteKingLocation_MaxCorner_UpdatesLocation() {
        Location loc = new Location(7, 7);
        board.updateWhiteKingLocation(loc);
        assertEquals(loc, board.getWhiteKingLocation());
    }

    @Test
    void updateWhiteKingLocation_Interior_UpdatesLocation() {
        Location loc = new Location(4, 4);
        board.updateWhiteKingLocation(loc);
        assertEquals(loc, board.getWhiteKingLocation());
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
        assertEquals(loc, board.getBlackKingLocation());
    }

    @Test
    void updateBlackKingLocation_MaxCorner_UpdatesLocation() {
        Location loc = new Location(7, 7);
        board.updateBlackKingLocation(loc);
        assertEquals(loc, board.getBlackKingLocation());
    }

    @Test
    void updateBlackKingLocation_Interior_UpdatesLocation() {
        Location loc = new Location(4, 4);
        board.updateBlackKingLocation(loc);
        assertEquals(loc, board.getBlackKingLocation());
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

    @Test
    void checkNotMoveIntoCheck_MoveDoesNotExposeKing_ReturnsTrueAndAppliesMove() {
        // White rook at (4,4), white king at (7,4) — moving rook to (4,5) leaves king safe
        Piece whiteRook = new Rook(PieceColor.WHITE);
        Piece whiteKing = new King(PieceColor.WHITE);
        board.setPiece(4, 4, whiteRook);
        board.setPiece(7, 4, whiteKing);
        board.updateWhiteKingLocation(new Location(7, 4));

        boolean result = board.checkNotMoveIntoCheck(new Location(4, 4), new Location(4, 5));

        assertTrue(result);
        assertNull(board.getPiece(4, 4));
        assertEquals(whiteRook, board.getPiece(4, 5));
    }

    @Test
    void checkNotMoveIntoCheck_MovePinnedPieceExposesKing_ReturnsFalseAndBoardUnchanged() {
        // White rook at (7,2) blocks black rook at (7,0) from reaching white king at (7,4)
        // Moving white rook away exposes the king
        Piece blackRook = new Rook(PieceColor.BLACK);
        Piece whiteBlocker = new Rook(PieceColor.WHITE);
        Piece whiteKing = new King(PieceColor.WHITE);
        board.setPiece(7, 0, blackRook);
        board.setPiece(7, 2, whiteBlocker);
        board.setPiece(7, 4, whiteKing);
        board.updateWhiteKingLocation(new Location(7, 4));

        boolean result = board.checkNotMoveIntoCheck(new Location(7, 2), new Location(3, 2));

        assertFalse(result);
        assertEquals(whiteBlocker, board.getPiece(7, 2));
        assertNull(board.getPiece(3, 2));
    }

    @Test
    void checkNotMoveIntoCheck_KingMovesToSafeSquare_ReturnsTrueAndUpdatesKingLocation() {
        // White king at (4,4), no threats — move to (4,5)
        Piece whiteKing = new King(PieceColor.WHITE);
        board.setPiece(4, 4, whiteKing);
        board.updateWhiteKingLocation(new Location(4, 4));

        boolean result = board.checkNotMoveIntoCheck(new Location(4, 4), new Location(4, 5));

        assertTrue(result);
        assertEquals(new Location(4, 5).getX(), board.getWhiteKingLocation().getX());
        assertEquals(new Location(4, 5).getY(), board.getWhiteKingLocation().getY());
    }

    @Test
    void checkNotMoveIntoCheck_KingMovesToAttackedSquare_ReturnsFalseAndLocationUnchanged() {
        // White king at (4,4), black bishop at (2,2) — moving king to (3,3) lands on attacked square
        Piece whiteKing = new King(PieceColor.WHITE);
        Piece blackBishop = new Bishop(PieceColor.BLACK);
        board.setPiece(4, 4, whiteKing);
        board.setPiece(2, 2, blackBishop);
        board.updateWhiteKingLocation(new Location(4, 4));

        boolean result = board.checkNotMoveIntoCheck(new Location(4, 4), new Location(3, 3));

        assertFalse(result);
        assertEquals(4, board.getWhiteKingLocation().getX());
        assertEquals(4, board.getWhiteKingLocation().getY());
        assertEquals(whiteKing, board.getPiece(4, 4));
    }

    // ── castle ───────────────────────────────────────────────────────────────

    @Test
    void castle_NullKingFrom_ThrowsIllegalArgumentException() {
        Location to = new Location(7, 6);
        Location rookFrom = new Location(7, 7);
        Location rookTo = new Location(7, 5);
        assertThrows(IllegalArgumentException.class,
                () -> board.castle(null, to, rookFrom, rookTo));
    }

    @Test
    void castle_NullKingTo_ThrowsIllegalArgumentException() {
        Location kingFrom = new Location(7, 4);
        Location rookFrom = new Location(7, 7);
        Location rookTo = new Location(7, 5);
        assertThrows(IllegalArgumentException.class,
                () -> board.castle(kingFrom, null, rookFrom, rookTo));
    }

    @Test
    void castle_NullRookFrom_ThrowsIllegalArgumentException() {
        Location kingFrom = new Location(7, 4);
        Location kingTo = new Location(7, 6);
        Location rookTo = new Location(7, 5);
        assertThrows(IllegalArgumentException.class,
                () -> board.castle(kingFrom, kingTo, null, rookTo));
    }

    @Test
    void castle_NullRookTo_ThrowsIllegalArgumentException() {
        Location kingFrom = new Location(7, 4);
        Location kingTo = new Location(7, 6);
        Location rookFrom = new Location(7, 7);
        assertThrows(IllegalArgumentException.class,
                () -> board.castle(kingFrom, kingTo, rookFrom, null));
    }

    // castle() uses Rook mocks for both slots — castle() only calls hasMoved() and getColor()
    // on the pieces at those positions, so the concrete type at each position doesn't matter.

    @Test
    void castle_KingHasMoved_ReturnsFalseAndBoardUnchanged() {
        Piece mockKing = EasyMock.createMock(Rook.class);
        Piece mockRook = EasyMock.createMock(Rook.class);
        EasyMock.expect(mockKing.hasMoved()).andStubReturn(true);
        EasyMock.replay(mockKing, mockRook);

        Location kingFrom = new Location(7, 4);
        Location kingTo = new Location(7, 6);
        Location rookFrom = new Location(7, 7);
        Location rookTo = new Location(7, 5);
        board.setPiece(7, 4, mockKing);
        board.setPiece(7, 7, mockRook);
        board.updateWhiteKingLocation(kingFrom);

        assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
        assertEquals(mockKing, board.getPiece(7, 4));
        assertEquals(mockRook, board.getPiece(7, 7));
        EasyMock.verify(mockKing, mockRook);
    }

    @Test
    void castle_RookHasMoved_ReturnsFalseAndBoardUnchanged() {
        Piece mockKing = EasyMock.createMock(Rook.class);
        Piece mockRook = EasyMock.createMock(Rook.class);
        EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
        EasyMock.expect(mockRook.hasMoved()).andStubReturn(true);
        EasyMock.replay(mockKing, mockRook);

        Location kingFrom = new Location(7, 4);
        Location kingTo = new Location(7, 6);
        Location rookFrom = new Location(7, 7);
        Location rookTo = new Location(7, 5);
        board.setPiece(7, 4, mockKing);
        board.setPiece(7, 7, mockRook);
        board.updateWhiteKingLocation(kingFrom);

        assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
        assertEquals(mockKing, board.getPiece(7, 4));
        assertEquals(mockRook, board.getPiece(7, 7));
        EasyMock.verify(mockKing, mockRook);
    }

    @Test
    void castle_PieceBetweenKingAndRook_ReturnsFalseAndBoardUnchanged() {
        Piece mockKing = EasyMock.createMock(Rook.class);
        Piece mockRook = EasyMock.createMock(Rook.class);
        Piece blocker = new Bishop(PieceColor.WHITE);
        EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
        EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
        EasyMock.replay(mockKing, mockRook);

        Location kingFrom = new Location(7, 4);
        Location kingTo = new Location(7, 6);
        Location rookFrom = new Location(7, 7);
        Location rookTo = new Location(7, 5);
        board.setPiece(7, 4, mockKing);
        board.setPiece(7, 5, blocker);
        board.setPiece(7, 7, mockRook);
        board.updateWhiteKingLocation(kingFrom);

        assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
        assertEquals(mockKing, board.getPiece(7, 4));
        assertEquals(blocker, board.getPiece(7, 5));
        assertEquals(mockRook, board.getPiece(7, 7));
        EasyMock.verify(mockKing, mockRook);
    }

    @Test
    void castle_KingCurrentlyInCheck_ReturnsFalseAndBoardUnchanged() {
        Piece mockKing = EasyMock.createMock(Rook.class);
        Piece mockRook = EasyMock.createMock(Rook.class);
        Piece blackRook = new Rook(PieceColor.BLACK);
        EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
        EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
        EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
        EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
        EasyMock.replay(mockKing, mockRook);

        // King at (7,4), black rook at (0,4) attacks king along column 4
        Location kingFrom = new Location(7, 4);
        Location kingTo = new Location(7, 6);
        Location rookFrom = new Location(7, 7);
        Location rookTo = new Location(7, 5);
        board.setPiece(7, 4, mockKing);
        board.setPiece(7, 7, mockRook);
        board.setPiece(0, 4, blackRook);
        board.updateWhiteKingLocation(kingFrom);

        assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
        assertEquals(mockKing, board.getPiece(7, 4));
        assertEquals(mockRook, board.getPiece(7, 7));
        EasyMock.verify(mockKing, mockRook);
    }

    @Test
    void castle_KingTransitSquareAttacked_ReturnsFalseAndBoardUnchanged() {
        Piece mockKing = EasyMock.createMock(Rook.class);
        Piece mockRook = EasyMock.createMock(Rook.class);
        Piece blackRook = new Rook(PieceColor.BLACK);
        EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
        EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
        EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
        EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
        EasyMock.replay(mockKing, mockRook);

        // King at (7,4) -> (7,6); transit = (7,5); black rook at (0,5) attacks (7,5)
        Location kingFrom = new Location(7, 4);
        Location kingTo = new Location(7, 6);
        Location rookFrom = new Location(7, 7);
        Location rookTo = new Location(7, 5);
        board.setPiece(7, 4, mockKing);
        board.setPiece(7, 7, mockRook);
        board.setPiece(0, 5, blackRook);
        board.updateWhiteKingLocation(kingFrom);

        assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
        assertEquals(mockKing, board.getPiece(7, 4));
        assertEquals(mockRook, board.getPiece(7, 7));
        EasyMock.verify(mockKing, mockRook);
    }

    @Test
    void castle_KingLandingSquareAttacked_ReturnsFalseAndBoardUnchanged() {
        Piece mockKing = EasyMock.createMock(Rook.class);
        Piece mockRook = EasyMock.createMock(Rook.class);
        Piece blackRook = new Rook(PieceColor.BLACK);
        EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
        EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
        EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
        EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
        EasyMock.replay(mockKing, mockRook);

        // King at (7,4) -> (7,6); landing = (7,6); black rook at (0,6) attacks (7,6)
        Location kingFrom = new Location(7, 4);
        Location kingTo = new Location(7, 6);
        Location rookFrom = new Location(7, 7);
        Location rookTo = new Location(7, 5);
        board.setPiece(7, 4, mockKing);
        board.setPiece(7, 7, mockRook);
        board.setPiece(0, 6, blackRook);
        board.updateWhiteKingLocation(kingFrom);

        assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
        assertEquals(mockKing, board.getPiece(7, 4));
        assertEquals(mockRook, board.getPiece(7, 7));
        EasyMock.verify(mockKing, mockRook);
    }

    @Test
    void castle_AllPreconditionsMet_SucceedsAndRepositionesPiecesAndUpdatesKingLocation() {
        Piece mockKing = EasyMock.createMock(Rook.class);
        Piece mockRook = EasyMock.createMock(Rook.class);
        EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
        EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
        EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
        EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
        EasyMock.replay(mockKing, mockRook);

        // King (7,4)->(7,6), Rook (7,7)->(7,5), path clear, no checks
        Location kingFrom = new Location(7, 4);
        Location kingTo = new Location(7, 6);
        Location rookFrom = new Location(7, 7);
        Location rookTo = new Location(7, 5);
        board.setPiece(7, 4, mockKing);
        board.setPiece(7, 7, mockRook);
        board.updateWhiteKingLocation(kingFrom);

        assertTrue(board.castle(kingFrom, kingTo, rookFrom, rookTo));
        assertEquals(mockKing, board.getPiece(7, 6));
        assertEquals(mockRook, board.getPiece(7, 5));
        assertNull(board.getPiece(7, 4));
        assertNull(board.getPiece(7, 7));
        assertEquals(7, board.getWhiteKingLocation().getX());
        assertEquals(6, board.getWhiteKingLocation().getY());
        EasyMock.verify(mockKing, mockRook);
    }
}