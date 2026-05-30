package integration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import domain.Board;
import domain.GameState;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

/*
* Coordinate convention:
    * - board[row][column]
    * - row 0 = black back rank
 * - row 7 = white back rank
 * - col 3 = D file (where queen starts)
 * - col 4 = E file (where king starts)
 */
public class GameSetupIntegrationTests {
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
  void BoardConstructor_NotNull_BoardExists() {
    Board board = new Board();
    assertNotNull(board);
  }

  @Test
  void InitialGameState_WHITE_TURN_GameStartsWithWhiteTurn() {
    Board board = new Board();
    assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
  }

  @Test
  void GetSnapshot_NotNull_ReturnsSnapshot() {
    Board board = new Board();
    assertNotNull(board.getSnapshot());
  }

  @Test
  void GetSnapshot_RowCount_8RowsReturned() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();
    assertEquals(8, snapshot.length);
  }

  @Test
  void GetSnapshot_RowStructure_EachRowHas8Columns() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    for (int i = 0; i < 8; i++) {
      assertNotNull(snapshot[i]);
      assertEquals(8, snapshot[i].length);
    }
  }

  @Test
  void SnapshotIsolation_MutateSnapshot_InternalBoardStateUnchanged() {
    Board board = new Board();

    Piece[][] snapshot = board.getSnapshot();
    snapshot[0][0] = null;

    Piece[][] fresh = board.getSnapshot();
    assertNotNull(fresh[0][0]);
  }

  @Test
  void SnapshotIsolation_MultipleCalls_IndependentCopiesReturned() {
    Board board = new Board();

    Piece[][] snapshot1 = board.getSnapshot();
    Piece[][] snapshot2 = board.getSnapshot();

    snapshot1[0][0] = null;

    assertNotNull(snapshot2[0][0]);
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
}
