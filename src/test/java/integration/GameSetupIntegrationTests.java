package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import domain.Board;
import domain.GameState;
import domain.piece.Piece;
import org.junit.jupiter.api.Test;

public class GameSetupIntegrationTests {

  private static final int BOARD_SIZE = 8;
  private static final int MIDDLE_ROW_START = 2;
  private static final int MIDDLE_ROW_END = 5;
  private static final int[] PIECE_ROWS = {0, 1, 6, 7};

  @Test
  void boardSetup_InitialBoard_SnapshotIsNonNull() {
    Board board = new Board();

    Piece[][] snapshot = board.getSnapshot();

    assertNotNull(snapshot);
    assertEquals(BOARD_SIZE, snapshot.length);
    assertEquals(BOARD_SIZE, snapshot[0].length);
  }

  @Test
  void gameSetup_InitialGameState_IsWhiteTurn() {
    Board board = new Board();

    assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
  }

  @Test
  void boardSetup_MutatedSnapshot_DoesNotChangeInternalState() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    snapshot[0][0] = null;

    Piece[][] fresh = board.getSnapshot();

    assertNotNull(fresh[0][0]);
  }

  @Test
  void boardSetup_InitialBoard_MiddleRanksAreEmpty() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    for (int row = MIDDLE_ROW_START; row <= MIDDLE_ROW_END; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        assertNull(snapshot[row][col]);
      }
    }

    for (int row : PIECE_ROWS) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        assertNotNull(snapshot[row][col]);
      }
    }
  }

  @Test
  void boardSetup_TwoSnapshotCalls_ReturnDistinctArrayObjects() {
    Board board = new Board();

    Piece[][] first = board.getSnapshot();
    Piece[][] second = board.getSnapshot();

    assertNotSame(first, second);
  }
}
