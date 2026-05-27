package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Rook;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTests {

  // Board() / getSnapshot() / movePiece() constants
  private static final int BOARD_SIZE = 8;
  private static final int BLACK_BACK_RANK = 0;
  private static final int BLACK_PAWN_RANK = 1;
  private static final int WHITE_PAWN_RANK = 6;
  private static final int WHITE_BACK_RANK = 7;
  private static final int MIDDLE_START = 2;
  private static final int MIDDLE_END = 5;
  private static final int COL_A = 0;
  private static final int COL_B = 1;
  private static final int COL_C = 2;
  private static final int COL_D = 3;
  private static final int COL_E = 4;
  private static final int COL_F = 5;
  private static final int COL_G = 6;
  private static final int COL_H = 7;

  // applyMoveIfKingSafe / castle constants
  private static final int MAX_BOARD_INDEX = 7;
  private static final int CENTER_COORDINATE = 4;
  private static final int PINNED_ROOK_TARGET_ROW = 3;
  private static final int WHITE_HOME_ROW = 7;
  private static final int KING_START_COLUMN = 4;
  private static final int KINGSIDE_KING_TARGET_COLUMN = 6;
  private static final int KINGSIDE_ROOK_START_COLUMN = 7;
  private static final int KINGSIDE_ROOK_TARGET_COLUMN = 5;

  private Board board;

  @BeforeEach
  void setUp() {
    board = new Board();
    clearBoard();
  }

  private void clearBoard() {
    for (int row = 0; row < BOARD_SIZE; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        board.setPiece(row, col, null);
      }
    }
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

  // Board() constructor — TC 1–13

  @Test
  void Constructor_ValidInput_CreatesNonNullInstance() {
    Board b = new Board();
    assertNotNull(b);
  }

  @Test
  void GetSnapshot_InitialState_HasCorrectDimensions() {
    Board b = new Board();
    Piece[][] snapshot = b.getSnapshot();
    assertEquals(BOARD_SIZE, snapshot.length);
    assertEquals(BOARD_SIZE, snapshot[0].length);
  }

  @Test
  void Constructor_InitialState_BlackPawnsInitializedCorrectly() {
    Board b = new Board();
    Piece[][] snapshot = b.getSnapshot();
    for (int i = 0; i < BOARD_SIZE; i++) {
      assertNotNull(snapshot[BLACK_PAWN_RANK][i]);
      assertEquals(PieceType.PAWN, snapshot[BLACK_PAWN_RANK][i].getType());
      assertEquals(PieceColor.BLACK, snapshot[BLACK_PAWN_RANK][i].getColor());
    }
  }

  @Test
  void Constructor_InitialState_WhitePawnsInitializedCorrectly() {
    Board b = new Board();
    Piece[][] snapshot = b.getSnapshot();
    for (int i = 0; i < BOARD_SIZE; i++) {
      assertNotNull(snapshot[WHITE_PAWN_RANK][i]);
      assertEquals(PieceType.PAWN, snapshot[WHITE_PAWN_RANK][i].getType());
      assertEquals(PieceColor.WHITE, snapshot[WHITE_PAWN_RANK][i].getColor());
    }
  }

  @Test
  void Constructor_InitialState_MajorPiecesInitializedCorrectly() {
    Board b = new Board();
    Piece[][] snapshot = b.getSnapshot();

    assertEquals(PieceType.ROOK,   snapshot[BLACK_BACK_RANK][COL_A].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][COL_A].getColor());
    assertEquals(PieceType.KNIGHT, snapshot[BLACK_BACK_RANK][COL_B].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][COL_B].getColor());
    assertEquals(PieceType.BISHOP, snapshot[BLACK_BACK_RANK][COL_C].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][COL_C].getColor());
    assertEquals(PieceType.QUEEN,  snapshot[BLACK_BACK_RANK][COL_D].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][COL_D].getColor());
    assertEquals(PieceType.KING,   snapshot[BLACK_BACK_RANK][COL_E].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][COL_E].getColor());
    assertEquals(PieceType.BISHOP, snapshot[BLACK_BACK_RANK][COL_F].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][COL_F].getColor());
    assertEquals(PieceType.KNIGHT, snapshot[BLACK_BACK_RANK][COL_G].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][COL_G].getColor());
    assertEquals(PieceType.ROOK,   snapshot[BLACK_BACK_RANK][COL_H].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][COL_H].getColor());

    assertEquals(PieceType.ROOK,   snapshot[WHITE_BACK_RANK][COL_A].getType());
    assertEquals(PieceColor.WHITE, snapshot[WHITE_BACK_RANK][COL_A].getColor());
    assertEquals(PieceType.KNIGHT, snapshot[WHITE_BACK_RANK][COL_B].getType());
    assertEquals(PieceColor.WHITE, snapshot[WHITE_BACK_RANK][COL_B].getColor());
    assertEquals(PieceType.BISHOP, snapshot[WHITE_BACK_RANK][COL_C].getType());
    assertEquals(PieceColor.WHITE, snapshot[WHITE_BACK_RANK][COL_C].getColor());
    assertEquals(PieceType.QUEEN,  snapshot[WHITE_BACK_RANK][COL_D].getType());
    assertEquals(PieceColor.WHITE, snapshot[WHITE_BACK_RANK][COL_D].getColor());
    assertEquals(PieceType.KING,   snapshot[WHITE_BACK_RANK][COL_E].getType());
    assertEquals(PieceColor.WHITE, snapshot[WHITE_BACK_RANK][COL_E].getColor());
    assertEquals(PieceType.BISHOP, snapshot[WHITE_BACK_RANK][COL_F].getType());
    assertEquals(PieceColor.WHITE, snapshot[WHITE_BACK_RANK][COL_F].getColor());
    assertEquals(PieceType.KNIGHT, snapshot[WHITE_BACK_RANK][COL_G].getType());
    assertEquals(PieceColor.WHITE, snapshot[WHITE_BACK_RANK][COL_G].getColor());
    assertEquals(PieceType.ROOK,   snapshot[WHITE_BACK_RANK][COL_H].getType());
    assertEquals(PieceColor.WHITE, snapshot[WHITE_BACK_RANK][COL_H].getColor());
  }

  @Test
  void Constructor_InitialState_MiddleBoardIsEmpty() {
    Board b = new Board();
    Piece[][] snapshot = b.getSnapshot();
    for (int row = MIDDLE_START; row <= MIDDLE_END; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        assertNull(snapshot[row][col]);
      }
    }
  }

  // getSnapshot() — TC 14–22

  @Test
  void GetSnapshot_InitialState_IsDeepCopiedAndIndependent() {
    Board b = new Board();
    Piece[][] snapshot = b.getSnapshot();
    Piece original = snapshot[BLACK_BACK_RANK][COL_A];
    snapshot[BLACK_BACK_RANK][COL_A] = null;
    Piece[][] fresh = b.getSnapshot();
    assertNotNull(fresh[BLACK_BACK_RANK][COL_A]);
    assertEquals(original.getType(), fresh[BLACK_BACK_RANK][COL_A].getType());
    assertEquals(original.getColor(), fresh[BLACK_BACK_RANK][COL_A].getColor());
  }

  @Test
  void GetSnapshot_ModifiedSnapshot_DoesNotAffectBoard() {
    Board b = new Board();
    Piece[][] snapshot = b.getSnapshot();
    snapshot[BLACK_BACK_RANK][COL_A] = null;
    Piece[][] fresh = b.getSnapshot();
    assertNotNull(fresh[BLACK_BACK_RANK][COL_A]);
  }

  // movePiece() — TC 23–34

  @Test
  void movePieceValidMoveUpdatesSourceAndDestination() {
    Board b = new Board();
    Location from = new Location(COL_E, BLACK_PAWN_RANK);
    Location to = new Location(COL_E, BLACK_PAWN_RANK + 1);
    boolean result = b.movePiece(from, to);
    Piece[][] snapshot = b.getSnapshot();
    assertTrue(result);
    assertNull(snapshot[BLACK_PAWN_RANK][COL_E]);
    assertNotNull(snapshot[BLACK_PAWN_RANK + 1][COL_E]);
  }

  @Test
  void movePieceInvalidMoveShapeDoesNotModifyBoard() {
    Board b = new Board();
    Location from = new Location(COL_E, BLACK_PAWN_RANK);
    Location to = new Location(COL_E, BLACK_PAWN_RANK + 3);
    boolean result = b.movePiece(from, to);
    Piece[][] snapshot = b.getSnapshot();
    assertFalse(result);
    assertNotNull(snapshot[BLACK_PAWN_RANK][COL_E]);
    assertEquals(PieceType.PAWN, snapshot[BLACK_PAWN_RANK][COL_E].getType());
  }

  @Test
  void movePieceBlockedPathReturnsFalseAndNoMutation() {
    Board b = new Board();
    Location from = new Location(COL_A, BLACK_BACK_RANK);
    Location to = new Location(COL_A, BLACK_PAWN_RANK + 2);
    Piece[][] before = b.getSnapshot();
    boolean result = b.movePiece(from, to);
    Piece[][] after = b.getSnapshot();
    assertFalse(result);
    assertEquals(before[BLACK_BACK_RANK][COL_A].getType(), after[BLACK_BACK_RANK][COL_A].getType());
    assertNotNull(after[BLACK_PAWN_RANK][COL_A]);
    assertEquals(before[BLACK_PAWN_RANK][COL_A].getType(), after[BLACK_PAWN_RANK][COL_A].getType());
    assertNull(after[BLACK_PAWN_RANK + 2][COL_A]);
  }

  @Test
  void movePieceCaptureRemovesOpponentPiece() {
    Board b = new Board();
    Location whiteFrom = new Location(COL_F, WHITE_PAWN_RANK);
    Location whiteTo = new Location(COL_F, WHITE_PAWN_RANK - 2);
    assertTrue(b.movePiece(whiteFrom, whiteTo));
    Location blackFrom = new Location(COL_E, BLACK_PAWN_RANK);
    Location blackTo = new Location(COL_E, BLACK_PAWN_RANK + 2);
    assertTrue(b.movePiece(blackFrom, blackTo));
    boolean result = b.movePiece(blackTo, whiteTo);
    Piece[][] snapshot = b.getSnapshot();
    assertTrue(result);
    assertNull(snapshot[BLACK_PAWN_RANK + 2][COL_E]);
    assertNotNull(snapshot[WHITE_PAWN_RANK - 2][COL_F]);
    assertEquals(PieceColor.BLACK, snapshot[WHITE_PAWN_RANK - 2][COL_F].getColor());
  }

  @Test
  void movePieceSameSquareReturnsFalse() {
    Board b = new Board();
    Location from = new Location(COL_E, BLACK_PAWN_RANK);
    Location to = new Location(COL_E, BLACK_PAWN_RANK);
    boolean result = b.movePiece(from, to);
    Piece[][] snapshot = b.getSnapshot();
    assertFalse(result);
    assertNotNull(snapshot[BLACK_PAWN_RANK][COL_E]);
  }

  @Test
  void movePieceNullFromThrowsException() {
    Board b = new Board();
    assertThrows(IllegalArgumentException.class,
        () -> b.movePiece(null, new Location(COL_E, BLACK_PAWN_RANK)));
  }

  @Test
  void movePieceNullToThrowsException() {
    Board b = new Board();
    assertThrows(IllegalArgumentException.class,
        () -> b.movePiece(new Location(COL_E, BLACK_PAWN_RANK), null));
  }

  @Test
  void movePieceFromOutOfBoundsReturnsFalse() {
    Board b = new Board();
    assertFalse(b.movePiece(new Location(-1, BLACK_PAWN_RANK), new Location(COL_E, BLACK_PAWN_RANK + 1)));
  }

  @Test
  void movePieceToOutOfBoundsReturnsFalse() {
    Board b = new Board();
    assertFalse(b.movePiece(new Location(COL_E, BLACK_PAWN_RANK), new Location(COL_E, BOARD_SIZE)));
  }

  @Test
  void movePieceSameColorDestinationReturnsFalse() {
    Board b = new Board();
    Location from = new Location(COL_D, BLACK_BACK_RANK);
    Location to = new Location(COL_E, BLACK_BACK_RANK);
    boolean result = b.movePiece(from, to);
    Piece[][] snapshot = b.getSnapshot();
    assertFalse(result);
    assertNotNull(snapshot[BLACK_BACK_RANK][COL_D]);
    assertNotNull(snapshot[BLACK_BACK_RANK][COL_E]);
  }

  @Test
  void movePieceValidMoveOnlyUpdatesTwoSquares() {
    Board b = new Board();
    Location from = new Location(COL_E, BLACK_PAWN_RANK);
    Location to = new Location(COL_E, BLACK_PAWN_RANK + 1);
    boolean result = b.movePiece(from, to);
    Piece[][] snapshot = b.getSnapshot();
    assertTrue(result);
    assertNull(snapshot[BLACK_PAWN_RANK][COL_E]);
    assertNotNull(snapshot[BLACK_PAWN_RANK + 1][COL_E]);
  }

  @Test
  void movePieceInvalidMoveDoesNotMutateBoard() {
    Board b = new Board();
    Location from = new Location(COL_E, BLACK_PAWN_RANK);
    Location to = new Location(COL_E, BLACK_PAWN_RANK + 3);
    boolean result = b.movePiece(from, to);
    Piece[][] snapshot = b.getSnapshot();
    assertFalse(result);
    assertNotNull(snapshot[BLACK_PAWN_RANK][COL_E]);
    assertEquals(PieceType.PAWN, snapshot[BLACK_PAWN_RANK][COL_E].getType());
  }

  // getCurrentGameState() — TC 35–36

  @Test
  void getCurrentGameState_NewBoard_ReturnsWhiteTurn() {
    assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
  }

  // switchTurn() — TC 37–38

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

  // updateWhiteKingLocation() — TC 39–42

  @Test
  void updateWhiteKingLocation_NullLocation_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> board.updateWhiteKingLocation(null));
    assertEquals("location must not be null", exception.getMessage());
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

  // updateBlackKingLocation() — TC 43–46

  @Test
  void updateBlackKingLocation_NullLocation_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> board.updateBlackKingLocation(null));
    assertEquals("location must not be null", exception.getMessage());
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

  // applyMoveIfKingSafe() — TC 47–59

  @Test
  void applyMoveIfKingSafe_NullFrom_ThrowsIllegalArgumentException() {
    final Location to = new Location(CENTER_COORDINATE, CENTER_COORDINATE);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> board.applyMoveIfKingSafe(null, to));
    assertEquals("from must not be null", exception.getMessage());
  }

  @Test
  void applyMoveIfKingSafe_NullTo_ThrowsIllegalArgumentException() {
    final Location from = new Location(CENTER_COORDINATE, CENTER_COORDINATE);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> board.applyMoveIfKingSafe(from, null));
    assertEquals("to must not be null", exception.getMessage());
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
        new Location(CENTER_COORDINATE, KINGSIDE_ROOK_TARGET_COLUMN));

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

  // castle() — TC 60–73

  @Test
  void castle_NullKingFrom_ThrowsIllegalArgumentException() {
    final Location to = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> board.castle(null, to, rookFrom, rookTo));
    assertEquals("kingFrom must not be null", exception.getMessage());
  }

  @Test
  void castle_NullKingTo_ThrowsIllegalArgumentException() {
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> board.castle(kingFrom, null, rookFrom, rookTo));
    assertEquals("kingTo must not be null", exception.getMessage());
  }

  @Test
  void castle_NullRookFrom_ThrowsIllegalArgumentException() {
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookTo = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_TARGET_COLUMN);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> board.castle(kingFrom, kingTo, null, rookTo));
    assertEquals("rookFrom must not be null", exception.getMessage());
  }

  @Test
  void castle_NullRookTo_ThrowsIllegalArgumentException() {
    final Location kingFrom = new Location(WHITE_HOME_ROW, KING_START_COLUMN);
    final Location kingTo = new Location(WHITE_HOME_ROW, KINGSIDE_KING_TARGET_COLUMN);
    final Location rookFrom = new Location(WHITE_HOME_ROW, KINGSIDE_ROOK_START_COLUMN);
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> board.castle(kingFrom, kingTo, rookFrom, null));
    assertEquals("rookTo must not be null", exception.getMessage());
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
    )).andReturn(false)
      .andReturn(true);
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
    )).andReturn(false)
      .andReturn(false)
      .andReturn(true);
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