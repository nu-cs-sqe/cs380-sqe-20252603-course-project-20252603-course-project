package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.Board;
import domain.Location;
import domain.piece.Knight;
import domain.piece.Piece;
import domain.piece.PieceColor;
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
}
