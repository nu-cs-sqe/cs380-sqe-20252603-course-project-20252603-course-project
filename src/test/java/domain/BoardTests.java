package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
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

  private Piece mockAttacker(PieceColor color, boolean attacks) {
    Piece mock = EasyMock.createMock(Piece.class);
    EasyMock.expect(mock.getColor()).andStubReturn(color);
    EasyMock.expect(mock.getType()).andStubReturn(PieceType.PAWN);
    EasyMock.expect(mock.canAttack(
        EasyMock.anyObject(Location.class),
        EasyMock.anyObject(Location.class),
        EasyMock.anyObject(Piece[][].class)
    )).andStubReturn(attacks);
    EasyMock.replay(mock);
    return mock;
  }

  private Piece mockKing(PieceColor color) {
    Piece mock = EasyMock.createMock(Piece.class);
    EasyMock.expect(mock.getColor()).andStubReturn(color);
    EasyMock.expect(mock.getType()).andStubReturn(PieceType.KING);
    EasyMock.expect(mock.canAttack(
        EasyMock.anyObject(Location.class),
        EasyMock.anyObject(Location.class),
        EasyMock.anyObject(Piece[][].class)
    )).andStubReturn(false);
    EasyMock.replay(mock);
    return mock;
  }

  // getCurrentGameState
  @Test
  void getCurrentGameState_NewBoard_ReturnsWhiteTurn() {
    assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
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

  // getSnapshot
  @Test
  void getSnapshot_NotImplemented_ThrowsUnsupportedOperationException() {
    assertThrows(UnsupportedOperationException.class, () -> board.getSnapshot());
  }

  // applyMoveIfKingSafe
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
    final Piece whiteRook = mockAttacker(PieceColor.WHITE, false);
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteRook);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, whiteKing);
    board.updateWhiteKingLocation(new Location(WHITE_HOME_ROW, KING_START_COLUMN));

    final boolean result = board.applyMoveIfKingSafe(
        new Location(CENTER_COORDINATE, CENTER_COORDINATE),
        new Location(CENTER_COORDINATE, KINGSIDE_ROOK_TARGET_COLUMN));

    assertTrue(result);
    assertNull(board.getPiece(CENTER_COORDINATE, CENTER_COORDINATE));
    assertEquals(whiteRook, board.getPiece(CENTER_COORDINATE, KINGSIDE_ROOK_TARGET_COLUMN));
    EasyMock.verify(whiteRook, whiteKing);
  }

  @Test
  void applyMoveIfKingSafe_MovePinnedPieceExposesKing_ReturnsFalseAndBoardUnchanged() {
    final Piece blackRook = mockAttacker(PieceColor.BLACK, true);
    final Piece whiteBlocker = mockAttacker(PieceColor.WHITE, false);
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
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
    EasyMock.verify(blackRook, whiteBlocker, whiteKing);
  }

  @Test
  void applyMoveIfKingSafe_KingMovesToSafeSquare_ReturnsTrueAndUpdatesKingLocation() {
    final Piece whiteKing = mockKing(PieceColor.WHITE);
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
    EasyMock.verify(whiteKing);
  }

  @Test
  void applyMoveIfKingSafe_KingMovesToAttackedSquare_ReturnsFalseAndLocationUnchanged() {
    final Piece whiteKing = mockKing(PieceColor.WHITE);
    final Piece blackBishop = mockAttacker(PieceColor.BLACK, true);
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
    EasyMock.verify(whiteKing, blackBishop);
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
    final Piece blackKing = mockKing(PieceColor.BLACK);
    final Location from = new Location(CENTER_COORDINATE, CENTER_COORDINATE);
    final Location to = new Location(CENTER_COORDINATE, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(from.getX(), from.getY(), blackKing);
    board.updateBlackKingLocation(from);
    board.switchTurn();

    assertTrue(board.applyMoveIfKingSafe(from, to));
    assertEquals(to.getX(), board.getBlackKingLocation().getX());
    assertEquals(to.getY(), board.getBlackKingLocation().getY());
    EasyMock.verify(blackKing);
  }

  @Test
  void applyMoveIfKingSafe_QueenAttacksAlongRank_ReturnsFalse() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackQueen = mockAttacker(PieceColor.BLACK, true);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, whiteKing);
    board.setPiece(WHITE_HOME_ROW, 0, blackQueen);
    board.updateWhiteKingLocation(new Location(WHITE_HOME_ROW, KING_START_COLUMN));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackQueen);
  }

  @Test
  void applyMoveIfKingSafe_QueenAttacksAlongDiagonal_ReturnsFalse() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackQueen = mockAttacker(PieceColor.BLACK, true);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(2, 2, blackQueen);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackQueen);
  }

  @Test
  void applyMoveIfKingSafe_QueenDoesNotAttackKing_ReturnsTrue() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackQueen = mockAttacker(PieceColor.BLACK, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(1, 2, blackQueen);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackQueen);
  }

  @Test
  void applyMoveIfKingSafe_KnightAttacksKing_ReturnsFalse() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackKnight = mockAttacker(PieceColor.BLACK, true);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(2, 3, blackKnight);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackKnight);
  }

  @Test
  void applyMoveIfKingSafe_KnightAttacksKingWithAlternateShape_ReturnsFalse() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackKnight = mockAttacker(PieceColor.BLACK, true);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(3, 2, blackKnight);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackKnight);
  }

  @Test
  void applyMoveIfKingSafe_KnightDoesNotAttackKing_ReturnsTrue() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackKnight = mockAttacker(PieceColor.BLACK, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(2, 2, blackKnight);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackKnight);
  }

  @Test
  void applyMoveIfKingSafe_KnightSameFileDoesNotAttackKing_ReturnsTrue() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackKnight = mockAttacker(PieceColor.BLACK, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(2, CENTER_COORDINATE, blackKnight);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackKnight);
  }

  @Test
  void applyMoveIfKingSafe_KnightAdjacentDiagonalDoesNotAttackKing_ReturnsTrue() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackKnight = mockAttacker(PieceColor.BLACK, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(3, 3, blackKnight);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackKnight);
  }

  @Test
  void applyMoveIfKingSafe_BlackPawnAttacksWhiteKing_ReturnsFalse() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackPawn = mockAttacker(PieceColor.BLACK, true);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(3, 3, blackPawn);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackPawn);
  }

  @Test
  void applyMoveIfKingSafe_BlackPawnSameFileDoesNotAttackWhiteKing_ReturnsTrue() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackPawn = mockAttacker(PieceColor.BLACK, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(3, CENTER_COORDINATE, blackPawn);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackPawn);
  }

  @Test
  void applyMoveIfKingSafe_WhitePawnAttacksBlackKing_ReturnsFalse() {
    final Piece blackKing = mockAttacker(PieceColor.BLACK, false);
    final Piece whitePawn = mockAttacker(PieceColor.WHITE, true);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, blackKing);
    board.setPiece(5, 3, whitePawn);
    board.updateBlackKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));
    board.switchTurn();

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(blackKing, whitePawn);
  }

  @Test
  void applyMoveIfKingSafe_PawnDoesNotAttackKing_ReturnsTrue() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackPawn = mockAttacker(PieceColor.BLACK, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(2, CENTER_COORDINATE, blackPawn);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackPawn);
  }

  @Test
  void applyMoveIfKingSafe_OpposingKingAttacksKing_ReturnsFalse() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackKing = mockAttacker(PieceColor.BLACK, true);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(3, 3, blackKing);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertFalse(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackKing);
  }

  @Test
  void applyMoveIfKingSafe_OpposingKingDoesNotAttackKing_ReturnsTrue() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackKing = mockAttacker(PieceColor.BLACK, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(2, 2, blackKing);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackKing);
  }

  @Test
  void applyMoveIfKingSafe_OpposingKingTwoFilesAwayDoesNotAttackKing_ReturnsTrue() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackKing = mockAttacker(PieceColor.BLACK, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(CENTER_COORDINATE, 2, blackKing);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackKing);
  }

  @Test
  void applyMoveIfKingSafe_OpposingKingOnSameSquareDoesNotAttackKing_ReturnsTrue() {
    final Piece blackKing = mockAttacker(PieceColor.BLACK, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, blackKing);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(blackKing);
  }

  @Test
  void applyMoveIfKingSafe_RookAttackBlocked_ReturnsTrue() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackRook = mockAttacker(PieceColor.BLACK, false);
    final Piece whiteBishop = mockAttacker(PieceColor.WHITE, false);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, whiteKing);
    board.setPiece(WHITE_HOME_ROW, 0, blackRook);
    board.setPiece(WHITE_HOME_ROW, 2, whiteBishop);
    board.updateWhiteKingLocation(new Location(WHITE_HOME_ROW, KING_START_COLUMN));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackRook, whiteBishop);
  }

  @Test
  void applyMoveIfKingSafe_BishopNotOnDiagonal_ReturnsTrue() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackBishop = mockAttacker(PieceColor.BLACK, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(1, CENTER_COORDINATE, blackBishop);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackBishop);
  }

  @Test
  void applyMoveIfKingSafe_BishopOnSameSquareDoesNotAttackKing_ReturnsTrue() {
    final Piece blackBishop = mockAttacker(PieceColor.BLACK, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, blackBishop);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(blackBishop);
  }

  @Test
  void applyMoveIfKingSafe_BishopAttackBlocked_ReturnsTrue() {
    final Piece whiteKing = mockAttacker(PieceColor.WHITE, false);
    final Piece blackBishop = mockAttacker(PieceColor.BLACK, false);
    final Piece whitePawn = mockAttacker(PieceColor.WHITE, false);
    board.setPiece(CENTER_COORDINATE, CENTER_COORDINATE, whiteKing);
    board.setPiece(1, 1, blackBishop);
    board.setPiece(2, 2, whitePawn);
    board.updateWhiteKingLocation(new Location(CENTER_COORDINATE, CENTER_COORDINATE));

    assertTrue(board.applyMoveIfKingSafe(new Location(0, 0), new Location(0, 1)));
    EasyMock.verify(whiteKing, blackBishop, whitePawn);
  }

  // castle
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
    final Piece mockKing = EasyMock.createMock(Piece.class);
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
    final Piece mockKing = EasyMock.createMock(Piece.class);
    final Piece mockRook = EasyMock.createMock(Piece.class);
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
    final Piece mockKing = EasyMock.createMock(Piece.class);
    final Piece mockRook = EasyMock.createMock(Piece.class);
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
    final Piece mockKing = EasyMock.createMock(Piece.class);
    final Piece mockRook = EasyMock.createMock(Piece.class);
    final Piece blocker = mockAttacker(PieceColor.WHITE, false);
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
    final Piece mockKing = EasyMock.createMock(Piece.class);
    final Piece mockRook = EasyMock.createMock(Piece.class);
    final Piece blackAttacker = mockAttacker(PieceColor.BLACK, true);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.replay(mockKing, mockRook);

    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, mockKing);
    board.setPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN, mockRook);
    board.setPiece(0, KING_START_COLUMN, blackAttacker);
    board.updateWhiteKingLocation(kingFrom);

    assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    assertEquals(mockKing, board.getPiece(WHITE_HOME_ROW, KING_START_COLUMN));
    assertEquals(mockRook, board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN));
    EasyMock.verify(mockKing, mockRook, blackAttacker);
  }

  @Test
  void castle_KingTransitSquareAttacked_ReturnsFalseAndBoardUnchanged() {
    final Piece mockKing = EasyMock.createMock(Piece.class);
    final Piece mockRook = EasyMock.createMock(Piece.class);
    final Piece blackAttacker = EasyMock.createMock(Piece.class);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.expect(blackAttacker.getColor()).andStubReturn(PieceColor.BLACK);
    EasyMock.expect(blackAttacker.getType()).andStubReturn(PieceType.PAWN);
    EasyMock.expect(blackAttacker.canAttack(
        EasyMock.anyObject(Location.class),
        EasyMock.anyObject(Location.class),
        EasyMock.anyObject(Piece[][].class)
    )).andReturn(false)  // starting square not attacked
      .andReturn(true);  // transit square attacked
    EasyMock.replay(mockKing, mockRook, blackAttacker);

    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, mockKing);
    board.setPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN, mockRook);
    board.setPiece(0, KINGSIDE_ROOK_TARGET_COLUMN, blackAttacker);
    board.updateWhiteKingLocation(kingFrom);

    assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    assertEquals(mockKing, board.getPiece(WHITE_HOME_ROW, KING_START_COLUMN));
    assertEquals(mockRook, board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN));
    EasyMock.verify(mockKing, mockRook, blackAttacker);
  }

  @Test
  void castle_KingLandingSquareAttacked_ReturnsFalseAndBoardUnchanged() {
    final Piece mockKing = EasyMock.createMock(Piece.class);
    final Piece mockRook = EasyMock.createMock(Piece.class);
    final Piece blackAttacker = EasyMock.createMock(Piece.class);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.expect(blackAttacker.getColor()).andStubReturn(PieceColor.BLACK);
    EasyMock.expect(blackAttacker.getType()).andStubReturn(PieceType.PAWN);
    EasyMock.expect(blackAttacker.canAttack(
        EasyMock.anyObject(Location.class),
        EasyMock.anyObject(Location.class),
        EasyMock.anyObject(Piece[][].class)
    )).andReturn(false)  // starting square not attacked
      .andReturn(false)  // transit square not attacked
      .andReturn(true);  // landing square attacked
    EasyMock.replay(mockKing, mockRook, blackAttacker);

    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    board.setPiece(WHITE_HOME_ROW, KING_START_COLUMN, mockKing);
    board.setPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN, mockRook);
    board.setPiece(0, KINGSIDE_KING_TARGET_COLUMN, blackAttacker);
    board.updateWhiteKingLocation(kingFrom);

    assertFalse(board.castle(kingFrom, kingTo, rookFrom, rookTo));
    assertEquals(mockKing, board.getPiece(WHITE_HOME_ROW, KING_START_COLUMN));
    assertEquals(mockRook, board.getPiece(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN));
    EasyMock.verify(mockKing, mockRook, blackAttacker);
  }

  @Test
  void castle_AllPreconditionsMet_SucceedsAndRepositionesPiecesAndUpdatesKingLocation() {
    final Piece mockKing = EasyMock.createMock(Piece.class);
    final Piece mockRook = EasyMock.createMock(Piece.class);
    EasyMock.expect(mockKing.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockRook.hasMoved()).andStubReturn(false);
    EasyMock.expect(mockKing.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.expect(mockRook.getColor()).andStubReturn(PieceColor.WHITE);
    EasyMock.replay(mockKing, mockRook);

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
    final Piece mockKing = EasyMock.createMock(Piece.class);
    final Piece mockRook = EasyMock.createMock(Piece.class);
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
