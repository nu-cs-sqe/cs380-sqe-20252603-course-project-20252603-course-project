package integration;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.Board;
import domain.Location;
import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.Queen;
import domain.piece.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OneTurnIntegrationTests {
  private Board board;
  private Piece[][] state;

  // -----------------------------
  // Row constants (board indexing)
  // -----------------------------
  private static final int ROW_7 = 7;
  private static final int ROW_6 = 6;
  private static final int ROW_5 = 5;

  // -----------------------------
  // Column constants (board indexing)
  // -----------------------------
  private static final int COL_0 = 0;
  private static final int COL_1 = 1;
  private static final int COL_2 = 2;
  private static final int COL_3 = 3;
  private static final int COL_4 = 4;
  private static final int COL_5 = 5;
  private static final int COL_6 = 6;
  private static final int COL_7 = 7;

  @BeforeEach
  void setup() {
    board = new Board();
    state = board.getSnapshot();
  }

  @Test
  void RookBlockedPath_IsRejected_BoardUnchanged() {
    // rook at (7,0) blocked by pawn at (6,0)
    boolean result = board.movePiece(
        new Location(COL_0, ROW_7),
        new Location(COL_0, ROW_5)
    );

    Piece[][] state = board.getSnapshot();

    assertFalse(result);

    // rook should not have moved, destination empty
    assertTrue(state[ROW_7][COL_0] instanceof Rook);
    assertNull(state[ROW_5][COL_0]);
  }

  @Test
  void BishopBlockedDiagonal_IsRejected_BoardUnchanged() {

    // bishop at (7,2), pawn blocks at (6,1)
    boolean result = board.movePiece(
        new Location(COL_2, ROW_7),
        new Location(COL_0, ROW_5)
    );

    state = board.getSnapshot();

    assertFalse(result);

    assertTrue(state[ROW_7][COL_2] instanceof Bishop);
    assertNull(state[ROW_5][COL_0]);
  }

  @Test
  void KnightJump_SucceedsThroughPieces() {

    boolean result = board.movePiece(
        new Location(COL_1, ROW_7),
        new Location(COL_2, ROW_5)
    );

    state = board.getSnapshot();

    assertTrue(result);

    assertNull(state[ROW_7][COL_1]);
    assertTrue(state[ROW_5][COL_2] instanceof Knight);
  }

  @Test
  void PawnDiagonalMoveToEmptySquare_IsRejected() {

    boolean result = board.movePiece(
        new Location(COL_3, ROW_6),
        new Location(COL_4, ROW_5)
    );

    state = board.getSnapshot();

    assertFalse(result);

    assertTrue(state[ROW_6][COL_3] instanceof Pawn);
    assertNull(state[ROW_5][COL_4]);
  }

  @Test
  void QueenBlockedStraightPath_IsRejected() {

    // queen is blocked by pawn at (6,3)
    boolean result = board.movePiece(
        new Location(COL_3, ROW_7),
        new Location(COL_3, ROW_5)
    );

    state = board.getSnapshot();

    assertFalse(result);

    assertTrue(state[ROW_7][COL_3] instanceof Queen);
    assertNotNull(state[ROW_6][COL_3]);
  }

  @Test
  void QueenDiagonalBlocked_IsRejected() {

    boolean result = board.movePiece(
        new Location(COL_3, ROW_7),
        new Location(COL_5, ROW_5)
    );

    state = board.getSnapshot();

    assertFalse(result);

    assertTrue(state[ROW_7][COL_3] instanceof Queen);
  }

  @Test
  void KingCannotMoveIntoCheckViaMoveSequence_IsRejected() {
    boolean result;
    // e4
    result = board.movePiece(
        new Location(4, 6),
        new Location(4, 4)
    );
    assertTrue(result);
    // ..e5
    result = board.movePiece(
        new Location(4, 1),
        new Location(4, 3)
    );
    assertTrue(result);
    // Ke1 -> Ke2
    result = board.movePiece(
        new Location(4, 7),
        new Location(4, 6)
    );
    assertTrue(result);
    // ..Ke8 -> Ke7
    result = board.movePiece(
        new Location(4, 0),
        new Location(4, 1)
    );
    assertTrue(result);
    // Ke2 -> Kf3
    result = board.movePiece(
        new Location(4, 6),
        new Location(5, 5)
    );
    assertTrue(result);
    // ..Ke7 -> Kf6
    result = board.movePiece(
        new Location(4, 1),
        new Location(5, 2)
    );
    assertTrue(result);

    // Kf3 -> Kf4 (illegal, into black king's pawn check)
    result = board.movePiece(
        new Location(5, 5),
        new Location(5, 4)
    );
    assertFalse(result);
  }
}
