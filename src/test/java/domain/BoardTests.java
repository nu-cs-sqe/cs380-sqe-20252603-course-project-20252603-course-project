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
import org.junit.jupiter.api.Test;

public class BoardTests {

  private static final int BOARD_SIZE = 8;

  // Rows
  private static final int BLACK_BACK_RANK = 0;
  private static final int BLACK_PAWN_RANK = 1;

  private static final int WHITE_PAWN_RANK = 6;
  private static final int WHITE_BACK_RANK = 7;

  private static final int MIDDLE_START = 2;
  private static final int MIDDLE_END = 5;

  // Columns
  private static final int COL_A = 0;
  private static final int COL_B = 1;
  private static final int COL_C = 2;
  private static final int COL_D = 3;
  private static final int COL_E = 4;
  private static final int COL_F = 5;
  private static final int COL_G = 6;
  private static final int COL_H = 7;

  @Test
  void constructorCreatesBoard() {
    Board board = new Board();
    assertNotNull(board);
  }

  @Test
  void snapshotHasCorrectDimensions() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    assertEquals(BOARD_SIZE, snapshot.length);
    assertEquals(BOARD_SIZE, snapshot[0].length);
  }

  @Test
  void blackPawnsInitializedCorrectly() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    for (int i = 0; i < BOARD_SIZE; i++) {
      assertNotNull(snapshot[BLACK_PAWN_RANK][i]);
      assertEquals(PieceType.PAWN, snapshot[BLACK_PAWN_RANK][i].getType());
      assertEquals(PieceColor.BLACK, snapshot[BLACK_PAWN_RANK][i].getColor());
    }
  }

  @Test
  void whitePawnsInitializedCorrectly() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    for (int i = 0; i < BOARD_SIZE; i++) {
      assertNotNull(snapshot[WHITE_PAWN_RANK][i]);
      assertEquals(PieceType.PAWN, snapshot[WHITE_PAWN_RANK][i].getType());
      assertEquals(PieceColor.WHITE, snapshot[WHITE_PAWN_RANK][i].getColor());
    }
  }

  @Test
  void majorPiecesInitializedCorrectly() {
    Board board = new Board();
    Piece[][] s = board.getSnapshot();

    // Black back rank
    assertEquals(PieceType.ROOK, s[BLACK_BACK_RANK][COL_A].getType());
    assertEquals(PieceColor.BLACK, s[BLACK_BACK_RANK][COL_A].getColor());

    assertEquals(PieceType.KNIGHT, s[BLACK_BACK_RANK][COL_B].getType());
    assertEquals(PieceColor.BLACK, s[BLACK_BACK_RANK][COL_B].getColor());

    assertEquals(PieceType.BISHOP, s[BLACK_BACK_RANK][COL_C].getType());
    assertEquals(PieceColor.BLACK, s[BLACK_BACK_RANK][COL_C].getColor());

    assertEquals(PieceType.QUEEN, s[BLACK_BACK_RANK][COL_D].getType());
    assertEquals(PieceColor.BLACK, s[BLACK_BACK_RANK][COL_D].getColor());

    assertEquals(PieceType.KING, s[BLACK_BACK_RANK][COL_E].getType());
    assertEquals(PieceColor.BLACK, s[BLACK_BACK_RANK][COL_E].getColor());

    assertEquals(PieceType.BISHOP, s[BLACK_BACK_RANK][COL_F].getType());
    assertEquals(PieceColor.BLACK, s[BLACK_BACK_RANK][COL_F].getColor());

    assertEquals(PieceType.KNIGHT, s[BLACK_BACK_RANK][COL_G].getType());
    assertEquals(PieceColor.BLACK, s[BLACK_BACK_RANK][COL_G].getColor());

    assertEquals(PieceType.ROOK, s[BLACK_BACK_RANK][COL_H].getType());
    assertEquals(PieceColor.BLACK, s[BLACK_BACK_RANK][COL_H].getColor());

    // White back rank
    assertEquals(PieceType.ROOK, s[WHITE_BACK_RANK][COL_A].getType());
    assertEquals(PieceColor.WHITE, s[WHITE_BACK_RANK][COL_A].getColor());

    assertEquals(PieceType.KNIGHT, s[WHITE_BACK_RANK][COL_B].getType());
    assertEquals(PieceColor.WHITE, s[WHITE_BACK_RANK][COL_B].getColor());

    assertEquals(PieceType.BISHOP, s[WHITE_BACK_RANK][COL_C].getType());
    assertEquals(PieceColor.WHITE, s[WHITE_BACK_RANK][COL_C].getColor());

    assertEquals(PieceType.QUEEN, s[WHITE_BACK_RANK][COL_D].getType());
    assertEquals(PieceColor.WHITE, s[WHITE_BACK_RANK][COL_D].getColor());

    assertEquals(PieceType.KING, s[WHITE_BACK_RANK][COL_E].getType());
    assertEquals(PieceColor.WHITE, s[WHITE_BACK_RANK][COL_E].getColor());

    assertEquals(PieceType.BISHOP, s[WHITE_BACK_RANK][COL_F].getType());
    assertEquals(PieceColor.WHITE, s[WHITE_BACK_RANK][COL_F].getColor());

    assertEquals(PieceType.KNIGHT, s[WHITE_BACK_RANK][COL_G].getType());
    assertEquals(PieceColor.WHITE, s[WHITE_BACK_RANK][COL_G].getColor());

    assertEquals(PieceType.ROOK, s[WHITE_BACK_RANK][COL_H].getType());
    assertEquals(PieceColor.WHITE, s[WHITE_BACK_RANK][COL_H].getColor());
  }

  @Test
  void middleBoardIsEmpty() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    for (int row = MIDDLE_START; row <= MIDDLE_END; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        assertNull(snapshot[row][col]);
      }
    }
  }

  @Test
  void snapshotIsDeepCopiedAndIndependent() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    Piece original = snapshot[BLACK_BACK_RANK][COL_A];

    snapshot[BLACK_BACK_RANK][COL_A] = null;

    Piece[][] fresh = board.getSnapshot();

    assertNotNull(fresh[BLACK_BACK_RANK][COL_A]);
    assertEquals(original.getType(), fresh[BLACK_BACK_RANK][COL_A].getType());
    assertEquals(original.getColor(), fresh[BLACK_BACK_RANK][COL_A].getColor());
  }

  @Test
  void modifyingSnapshotDoesNotAffectBoard() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    snapshot[BLACK_BACK_RANK][COL_A] = null;

    Piece[][] fresh = board.getSnapshot();

    assertNotNull(fresh[BLACK_BACK_RANK][COL_A]);
  }

  @Test
  void movePieceValidMoveUpdatesSourceAndDestination() {
    Board board = new Board();

    Location from = new Location(COL_E, BLACK_PAWN_RANK);
    Location to = new Location(COL_E, BLACK_PAWN_RANK + 1);

    boolean result = board.movePiece(from, to);

    Piece[][] snapshot = board.getSnapshot();

    assertTrue(result);
    assertNull(snapshot[BLACK_PAWN_RANK][COL_E]);
    assertNotNull(snapshot[BLACK_PAWN_RANK + 1][COL_E]);
  }

  @Test
  void movePieceInvalidMoveShapeDoesNotModifyBoard() {
    Board board = new Board();

    Location from = new Location(COL_E, BLACK_PAWN_RANK);
    Location to = new Location(COL_E, BLACK_PAWN_RANK + 3);

    boolean result = board.movePiece(from, to);

    Piece[][] snapshot = board.getSnapshot();

    assertFalse(result);
    assertNotNull(snapshot[BLACK_PAWN_RANK][COL_E]);
    assertEquals(PieceType.PAWN, snapshot[BLACK_PAWN_RANK][COL_E].getType());
  }

  @Test
  void movePieceBlockedPathReturnsFalseAndNoMutation() {
    Board board = new Board();

    Location from = new Location(COL_A, BLACK_BACK_RANK);
    Location to = new Location(COL_A, BLACK_PAWN_RANK + 2);

    Piece[][] before = board.getSnapshot();

    boolean result = board.movePiece(from, to);

    Piece[][] after = board.getSnapshot();

    assertFalse(result);

    assertEquals(
        before[BLACK_BACK_RANK][COL_A].getType(),
        after[BLACK_BACK_RANK][COL_A].getType()
    );

    assertNotNull(after[BLACK_PAWN_RANK][COL_A]); // blocker still exists
    assertEquals(
        before[BLACK_PAWN_RANK][COL_A].getType(),
        after[BLACK_PAWN_RANK][COL_A].getType()
    );

    assertNull(after[BLACK_PAWN_RANK + 2][COL_A]); // destination unchanged
  }

  @Test
  void movePieceCaptureRemovesOpponentPiece() {
    Board board = new Board();

    Location whiteFrom = new Location(COL_F, WHITE_PAWN_RANK);
    Location whiteTo = new Location(COL_F, WHITE_PAWN_RANK - 2);
    assertTrue(board.movePiece(whiteFrom, whiteTo));

    Location blackFrom = new Location(COL_E, BLACK_PAWN_RANK);
    Location blackTo = new Location(COL_E, BLACK_PAWN_RANK + 2);
    assertTrue(board.movePiece(blackFrom, blackTo));

    Location from = blackTo;
    Location to = whiteTo;

    boolean result = board.movePiece(from, to);

    Piece[][] snapshot = board.getSnapshot();

    assertTrue(result);

    assertNull(snapshot[BLACK_PAWN_RANK + 2][COL_E]);

    assertNotNull(snapshot[WHITE_PAWN_RANK - 2][COL_F]);
    assertEquals(PieceColor.BLACK, snapshot[WHITE_PAWN_RANK - 2][COL_F].getColor());
  }

  @Test
  void movePieceSameSquareReturnsFalse() {
    Board board = new Board();

    Location from = new Location(COL_E, BLACK_PAWN_RANK);
    Location to = new Location(COL_E, BLACK_PAWN_RANK);

    boolean result = board.movePiece(from, to);

    Piece[][] snapshot = board.getSnapshot();

    assertFalse(result);
    assertNotNull(snapshot[BLACK_PAWN_RANK][COL_E]);
  }

  @Test
  void movePieceNullFromThrowsException() {
    Board board = new Board();

    assertThrows(
        IllegalArgumentException.class,
        () -> board.movePiece(null, new Location(COL_E, BLACK_PAWN_RANK))
    );
  }

  @Test
  void movePieceNullToThrowsException() {
    Board board = new Board();

    assertThrows(
        IllegalArgumentException.class,
        () -> board.movePiece(new Location(COL_E, BLACK_PAWN_RANK), null)
    );
  }

  @Test
  void movePieceFromOutOfBoundsReturnsFalse() {
    Board board = new Board();

    Location from = new Location(-1, BLACK_PAWN_RANK);
    Location to = new Location(COL_E, BLACK_PAWN_RANK + 1);

    boolean result = board.movePiece(from, to);

    assertFalse(result);
  }

  @Test
  void movePieceToOutOfBoundsReturnsFalse() {
    Board board = new Board();

    Location from = new Location(COL_E, BLACK_PAWN_RANK);
    Location to = new Location(COL_E, BOARD_SIZE);

    boolean result = board.movePiece(from, to);

    assertFalse(result);
  }

  @Test
  void movePieceSameColorDestinationReturnsFalse() {
    Board board = new Board();

    Location from = new Location(COL_D, BLACK_BACK_RANK); // queen
    Location to = new Location(COL_E, BLACK_BACK_RANK);   // king

    boolean result = board.movePiece(from, to);

    Piece[][] snapshot = board.getSnapshot();

    assertFalse(result);
    assertNotNull(snapshot[BLACK_BACK_RANK][COL_D]);
    assertNotNull(snapshot[BLACK_BACK_RANK][COL_E]);
  }
}