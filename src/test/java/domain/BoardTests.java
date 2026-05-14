package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.Queen;
import domain.piece.Rook;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTests {
  private static final int MAX_BOARD_INDEX = 7;
  private static final int CENTER_COORDINATE = 4;
  private static final int PINNED_ROOK_TARGET_ROW = 3;
  private static final int ATTACKED_KING_TARGET_ROW = 3;
  private static final int ATTACKED_KING_TARGET_COLUMN = 3;
  private static final int WHITE_HOME_ROW = 7;
  private static final int KING_START_COLUMN = 4;
  private static final int KINGSIDE_KING_TARGET_COLUMN = 6;
  private static final int KINGSIDE_ROOK_START_COLUMN = 7;
  private static final int KINGSIDE_ROOK_TARGET_COLUMN = 5;

  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
  }

  // getCurrentGameState

  @Test
  void getCurrentGameState_NewBoard_ReturnsWhiteTurn() {
    assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
  }

  @Test
  void getCurrentGameState_AfterOneSwitchTurn_ReturnsBlackTurn() {
    board.switchTurn();
    assertEquals(GameState.BLACK_TURN, board.getCurrentGameState());
  }

  // switchTurn

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

  // updateWhiteKingLocation

  @Test
  void updateWhiteKingLocation_NullLocation_ThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class,
        () -> board.updateWhiteKingLocation(null));
  }

  @Test
  void updateWhiteKingLocation_MinCorner_UpdatesLocation() {
    final Location loc = new Location(0, 0);
    board.updateWhiteKingLocation(loc);
    assertEquals(loc, board.getWhiteKingLocation());
  }

  @Test
  void updateWhiteKingLocation_MaxCorner_UpdatesLocation() {
    final Location loc = new Location(MAX_BOARD_INDEX, MAX_BOARD_INDEX);
    board.updateWhiteKingLocation(loc);
    assertEquals(loc, board.getWhiteKingLocation());
  }

  @Test
  void updateWhiteKingLocation_Interior_UpdatesLocation() {
    final Location loc = new Location(CENTER_COORDINATE, CENTER_COORDINATE);
    board.updateWhiteKingLocation(loc);
    assertEquals(loc, board.getWhiteKingLocation());
  }

  // updateBlackKingLocation

  @Test
  void updateBlackKingLocation_NullLocation_ThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class,
        () -> board.updateBlackKingLocation(null));
  }

  @Test
  void updateBlackKingLocation_MinCorner_UpdatesLocation() {
    final Location loc = new Location(0, 0);
    board.updateBlackKingLocation(loc);
    assertEquals(loc, board.getBlackKingLocation());
  }

  @Test
  void updateBlackKingLocation_MaxCorner_UpdatesLocation() {
    final Location loc = new Location(MAX_BOARD_INDEX, MAX_BOARD_INDEX);
    board.updateBlackKingLocation(loc);
    assertEquals(loc, board.getBlackKingLocation());
  }

  @Test
  void updateBlackKingLocation_Interior_UpdatesLocation() {
    final Location loc = new Location(CENTER_COORDINATE, CENTER_COORDINATE);
    board.updateBlackKingLocation(loc);
    assertEquals(loc, board.getBlackKingLocation());
  }

  // applyMoveIfKingSafe

  @Test
  void getSnapshot_NotImplemented_ThrowsUnsupportedOperationException() {
    assertThrows(UnsupportedOperationException.class, () -> board.getSnapshot());
  }

  @Test
  void applyMoveIfKingSafe_NullFrom_ThrowsIllegalArgumentException() {
    final Location to = new Location(CENTER_COORDINATE, CENTER_COORDINATE);
    assertThrows(IllegalArgumentException.class,
        () -> board.applyMoveIfKingSafe(null, to));
  }

  @Test
  void applyMoveIfKingSafe_NullTo_ThrowsIllegalArgumentException() {
    final Location from = new Location(CENTER_COORDINATE, CENTER_COORDINATE);
    assertThrows(IllegalArgumentException.class,
        () -> board.applyMoveIfKingSafe(from, null));
  }

  @Test
  void applyMoveIfKingSafe_MoveDoesNotExposeKing_ReturnsTrueAndAppliesMove() {
    // Moving the rook leaves the white king safe.
    final Piece whiteRook = new Rook(PieceColor.WHITE);
    final Piece whiteKing = new King(PieceColor.WHITE);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteRook);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, whiteKing);
    board.updateWhiteKingLocation(new Location(WHITE_HOME_ROW, KING_START_COLUMN));

    final boolean result = board.applyMoveIfKingSafe(
        new Location(CENTER_COORDINATE, CENTER_COORDINATE),
        new Location(CENTER_COORDINATE, KINGSIDE_ROOK_TARGET_COLUMN));

    assertTrue(result);
    assertNull(board.getPiece(CENTER_COORDINATE, CENTER_COORDINATE));
    assertEquals(whiteRook, board.getPiece(CENTER_COORDINATE, KINGSIDE_ROOK_TARGET_COLUMN));
  }

  @Test
  void applyMoveIfKingSafe_MovePinnedPieceExposesKing_ReturnsFalseAndBoardUnchanged() {
    // The white rook blocks the black rook from reaching the white king.
    // Moving white rook away exposes the king
    final Piece blackRook = new Rook(PieceColor.BLACK);
    final Piece whiteBlocker = new Rook(PieceColor.WHITE);
    final Piece whiteKing = new King(PieceColor.WHITE);
    board.setPiece(WHITE_HOME_ROW, 0, blackRook);
    board.setPiece(WHITE_HOME_ROW, 2, whiteBlocker);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, whiteKing);
    board.updateWhiteKingLocation(new Location(WHITE_HOME_ROW, KING_START_COLUMN));

    final boolean result = board.applyMoveIfKingSafe(
        new Location(WHITE_HOME_ROW, 2),
        new Location(PINNED_ROOK_TARGET_ROW, 2));

    assertFalse(result);
    assertEquals(whiteBlocker, board.getPiece(WHITE_HOME_ROW, 2));
    assertNull(board.getPiece(PINNED_ROOK_TARGET_ROW, 2));
  }

  @Test
  void applyMoveIfKingSafe_KingMovesToSafeSquare_ReturnsTrueAndUpdatesKingLocation() {
    // White king moves one file with no threats.
    final Piece whiteKing = new King(PieceColor.WHITE);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    final boolean result = board.applyMoveIfKingSafe(
        new Location(CENTER_COORDINATE, CENTER_COORDINATE),
        new Location(CENTER_COORDINATE, KINGSIDE_ROOK_TARGET_COLUMN));

    assertTrue(result);
    assertEquals(
        new Location(CENTER_COORDINATE, KINGSIDE_ROOK_TARGET_COLUMN).getX(),
        board.getWhiteKingLocation().getX());
    assertEquals(
        new Location(CENTER_COORDINATE, KINGSIDE_ROOK_TARGET_COLUMN).getY(),
        board.getWhiteKingLocation().getY());
  }

  @Test
  void applyMoveIfKingSafe_KingMovesToAttackedSquare_ReturnsFalseAndLocationUnchanged() {
    // Moving the king onto the bishop diagonal lands on an attacked square.
    final Piece whiteKing = new King(PieceColor.WHITE);
    final Piece blackBishop = new Bishop(PieceColor.BLACK);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(2, 2, blackBishop);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    final boolean result = board.applyMoveIfKingSafe(
        new Location(CENTER_COORDINATE, CENTER_COORDINATE),
        new Location(ATTACKED_KING_TARGET_ROW, ATTACKED_KING_TARGET_COLUMN));

    assertFalse(result);
    assertEquals(CENTER_COORDINATE, board.getWhiteKingLocation().getX());
    assertEquals(CENTER_COORDINATE, board.getWhiteKingLocation().getY());
    assertEquals(whiteKing, board.getPiece(CENTER_COORDINATE, CENTER_COORDINATE));
  }

  @Test
  void applyMoveIfKingSafe_EmptySource_ReturnsTrueAndLeavesDestinationEmpty() {
    final Location from = new Location(0, 0);
    final Location to = new Location(0, 1);
    board.updateWhiteKingLocation(new Location(WHITE_HOME_ROW, KING_START_COLUMN));

    assertTrue(board.applyMoveIfKingSafe(from, to));
    assertNull(board.getPiece(to.getX(), to.getY()));
  }

  @Test
  void applyMoveIfKingSafe_BlackKingMovesToSafeSquare_UpdatesBlackKingLocation() {
    final Piece blackKing = new King(PieceColor.BLACK);
    final Location from = new Location(CENTER_COORDINATE, CENTER_COORDINATE);
    final Location to = new Location(CENTER_COORDINATE, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(from.getX(), from.getY(), blackKing);
    board.updateBlackKingLocation(from);
    board.switchTurn();

    assertTrue(board.applyMoveIfKingSafe(from, to));
    assertEquals(to.getX(), board.getBlackKingLocation().getX());
    assertEquals(to.getY(), board.getBlackKingLocation().getY());
  }

  @Test
  void applyMoveIfKingSafe_QueenAttacksAlongRank_ReturnsFalse() {
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, new King(PieceColor.WHITE));
    board.setPiece(WHITE_HOME_ROW, 0, new Queen(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(WHITE_HOME_ROW, KING_START_COLUMN));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_QueenAttacksAlongDiagonal_ReturnsFalse() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(2, 2, new Queen(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_QueenDoesNotAttackKing_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(1, 2, new Queen(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_KnightAttacksKing_ReturnsFalse() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(2, 3, new Knight(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_KnightAttacksKingWithAlternateShape_ReturnsFalse() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(3, 2, new Knight(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_KnightDoesNotAttackKing_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(2, 2, new Knight(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_KnightSameFileDoesNotAttackKing_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(2, CENTER_COORDINATE, new Knight(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_KnightAdjacentDiagonalDoesNotAttackKing_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(3, 3, new Knight(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_BlackPawnAttacksWhiteKing_ReturnsFalse() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(3, 3, new Pawn(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_BlackPawnSameFileDoesNotAttackWhiteKing_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(3, CENTER_COORDINATE, new Pawn(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_WhitePawnAttacksBlackKing_ReturnsFalse() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.BLACK));
    board.setPiece(5, 3, new Pawn(PieceColor.WHITE));
    board.updateBlackKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));
    board.switchTurn();

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_PawnDoesNotAttackKing_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(2, CENTER_COORDINATE, new Pawn(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_OpposingKingAttacksKing_ReturnsFalse() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(3, 3, new King(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_OpposingKingDoesNotAttackKing_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(2, 2, new King(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_OpposingKingTwoFilesAwayDoesNotAttackKing_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(CENTER_COORDINATE, 2, new King(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_OpposingKingOnSameSquareDoesNotAttackKing_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_RookAttackBlocked_ReturnsTrue() {
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, new King(PieceColor.WHITE));
    board.setPiece(WHITE_HOME_ROW, 0, new Rook(PieceColor.BLACK));
    board.setPiece(WHITE_HOME_ROW, 2, new Bishop(PieceColor.WHITE));
    board.updateWhiteKingLocation(new Location(WHITE_HOME_ROW, KING_START_COLUMN));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_BishopNotOnDiagonal_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(1, CENTER_COORDINATE, new Bishop(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_BishopOnSameSquareDoesNotAttackKing_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new Bishop(PieceColor.BLACK));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  @Test
  void applyMoveIfKingSafe_BishopAttackBlocked_ReturnsTrue() {
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, new King(PieceColor.WHITE));
    board.setPiece(1, 1, new Bishop(PieceColor.BLACK));
    board.setPiece(2, 2, new Pawn(PieceColor.WHITE));
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
  }

  // castle

  @Test
  void castle_MissingRookLocation_ThrowsIllegalArgumentException() {
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    assertThrows(IllegalArgumentException.class,
        () -> board.castle(kingFrom, kingTo, rookFrom));
  }

  @Test
  void castle_NullKingFrom_ThrowsIllegalArgumentException() {
    final Location to = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    assertThrows(IllegalArgumentException.class,
        () -> board.castle(null, to, rookFrom, rookTo));
  }

  @Test
  void castle_NullKingTo_ThrowsIllegalArgumentException() {
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    assertThrows(IllegalArgumentException.class,
        () -> board.castle(kingFrom, null, rookFrom, rookTo));
  }

  @Test
  void castle_NullRookFrom_ThrowsIllegalArgumentException() {
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    assertThrows(IllegalArgumentException.class,
        () -> board.castle(kingFrom, kingTo, null, rookTo));
  }

  @Test
  void castle_NullRookTo_ThrowsIllegalArgumentException() {
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    assertThrows(IllegalArgumentException.class,
        () -> board.castle(kingFrom, kingTo, rookFrom, null));
  }

  @Test
  void castle_NoKingAtStart_ReturnsFalse() {
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);

    assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
  }

  @Test
  void castle_NoRookAtStart_ReturnsFalse() {
    final Piece mockKing = EasyMock.createMock(King.class);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.replay(mockKing);

    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, mockKing);

    assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    EasyMock.verify(mockKing);
  }


  @Test
  void castle_KingHasMoved_ReturnsFalseAndBoardUnchanged() {
    final Piece mockKing = EasyMock.createMock(King.class);
    final Piece mockRook = EasyMock.createMock(Rook.class);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(true);
    EasyMock.replay(mockKing, mockRook);

    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, mockKing);
    board.setPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN, mockRook);
    board.updateWhiteKingLocation(kingFrom);

    assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    assertEquals(mockKing, board.getPiece(WHITE_HOME_ROW, KING_START_COLUMN));
    assertEquals(mockRook, board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN));
    EasyMock.verify(mockKing, mockRook);
  }

  @Test
  void castle_RookHasMoved_ReturnsFalseAndBoardUnchanged() {
    final Piece mockKing = EasyMock.createMock(King.class);
    final Piece mockRook = EasyMock.createMock(Rook.class);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockRook.hasMoved()).andStubReturn(true);
    EasyMock.replay(mockKing, mockRook);

    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, mockKing);
    board.setPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN, mockRook);
    board.updateWhiteKingLocation(kingFrom);

    assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    assertEquals(mockKing, board.getPiece(WHITE_HOME_ROW, KING_START_COLUMN));
    assertEquals(mockRook, board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN));
    EasyMock.verify(mockKing, mockRook);
  }

  @Test
  void castle_PieceBetweenKingAndRook_ReturnsFalseAndBoardUnchanged() {
    final Piece mockKing = EasyMock.createMock(King.class);
    final Piece mockRook = EasyMock.createMock(Rook.class);
    final Piece blocker = new Bishop(PieceColor.WHITE);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
    EasyMock.replay(mockKing, mockRook);

    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, mockKing);
    board.setPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN, blocker);
    board.setPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN, mockRook);
    board.updateWhiteKingLocation(kingFrom);

    assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    assertEquals(mockKing, board.getPiece(WHITE_HOME_ROW, KING_START_COLUMN));
    assertEquals(blocker, board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN));
    assertEquals(mockRook, board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN));
    EasyMock.verify(mockKing, mockRook);
  }

  @Test
  void castle_KingCurrentlyInCheck_ReturnsFalseAndBoardUnchanged() {
    final Piece mockKing = EasyMock.createMock(King.class);
    final Piece mockRook = EasyMock.createMock(Rook.class);
    final Piece blackRook = new Rook(PieceColor.BLACK);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.replay(mockKing, mockRook);

    // The black rook attacks the king along the starting column.
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, mockKing);
    board.setPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN, mockRook);
    board.setPiece(0, KING_START_COLUMN, blackRook);
    board.updateWhiteKingLocation(kingFrom);

    assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    assertEquals(mockKing, board.getPiece(WHITE_HOME_ROW, KING_START_COLUMN));
    assertEquals(mockRook, board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN));
    EasyMock.verify(mockKing, mockRook);
  }

  @Test
  void castle_KingTransitSquareAttacked_ReturnsFalseAndBoardUnchanged() {
    final Piece mockKing = EasyMock.createMock(King.class);
    final Piece mockRook = EasyMock.createMock(Rook.class);
    final Piece blackRook = new Rook(PieceColor.BLACK);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.replay(mockKing, mockRook);

    // The black rook attacks the king's transit square.
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, mockKing);
    board.setPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN, mockRook);
    board.setPiece(0, KINGSIDE_ROOK_TARGET_COLUMN, blackRook);
    board.updateWhiteKingLocation(kingFrom);

    assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    assertEquals(mockKing, board.getPiece(WHITE_HOME_ROW, KING_START_COLUMN));
    assertEquals(mockRook, board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN));
    EasyMock.verify(mockKing, mockRook);
  }

  @Test
  void castle_KingLandingSquareAttacked_ReturnsFalseAndBoardUnchanged() {
    final Piece mockKing = EasyMock.createMock(King.class);
    final Piece mockRook = EasyMock.createMock(Rook.class);
    final Piece blackRook = new Rook(PieceColor.BLACK);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.replay(mockKing, mockRook);

    // The black rook attacks the king's landing square.
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, mockKing);
    board.setPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN, mockRook);
    board.setPiece(0, KINGSIDE_KING_TARGET_COLUMN, blackRook);
    board.updateWhiteKingLocation(kingFrom);

    assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    assertEquals(mockKing, board.getPiece(WHITE_HOME_ROW, KING_START_COLUMN));
    assertEquals(mockRook, board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN));
    EasyMock.verify(mockKing, mockRook);
  }

  @Test
  void castle_AllPreconditionsMet_SucceedsAndRepositionesPiecesAndUpdatesKingLocation() {
    final Piece mockKing = EasyMock.createMock(King.class);
    final Piece mockRook = EasyMock.createMock(Rook.class);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.replay(mockKing, mockRook);

    // The king and rook castle kingside with a clear path and no checks.
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, mockKing);
    board.setPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN, mockRook);
    board.updateWhiteKingLocation(kingFrom);

    assertTrue(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    assertEquals(mockKing, board.getPiece(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN));
    assertEquals(mockRook, board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN));
    assertNull(board.getPiece(WHITE_HOME_ROW, KING_START_COLUMN));
    assertNull(board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN));
    assertEquals(WHITE_HOME_ROW, board.getWhiteKingLocation().getX());
    assertEquals(KINGSIDE_KING_TARGET_COLUMN, board.getWhiteKingLocation().getY());
    EasyMock.verify(mockKing, mockRook);
  }

  @Test
  void castle_BlackAllPreconditionsMet_SucceedsAndUpdatesBlackKingLocation() {
    final Piece mockKing = EasyMock.createMock(King.class);
    final Piece mockRook = EasyMock.createMock(Rook.class);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.BLACK);
    EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.BLACK);
    EasyMock.replay(mockKing, mockRook);

    final int blackHomeRow = 0;
    final Location kingFrom = new Location(blackHomeRow, KING_START_COLUMN);
    final Location kingTo = new Location(blackHomeRow, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(blackHomeRow, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(blackHomeRow, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(blackHomeRow, KING_START_COLUMN, mockKing);
    board.setPiece(blackHomeRow, KINGSIDE_ROOK_START_COLUMN, mockRook);
    board.updateBlackKingLocation(kingFrom);

    assertTrue(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    assertEquals(mockKing, board.getPiece(blackHomeRow, KINGSIDE_KING_TARGET_COLUMN));
    assertEquals(mockRook, board.getPiece(blackHomeRow, KINGSIDE_ROOK_TARGET_COLUMN));
    assertEquals(blackHomeRow, board.getBlackKingLocation().getX());
    assertEquals(KINGSIDE_KING_TARGET_COLUMN, board.getBlackKingLocation().getY());
    EasyMock.verify(mockKing, mockRook);
  }
}
