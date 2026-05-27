package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import domain.Board;
import domain.GameState;
import domain.piece.Piece;
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
  @Test
  void BoardConstructor_NotNull_BoardExists() {
    Board board = new Board();
    assertNotNull(board);
  }

  @Test
  void InitialGameState_WHITE_TURN_GameStartsWithWhiteTurn() {
    Board board = new Board();
    assertEquals(GameState.WHITE_TURN, board.getGameState());
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
  void BoardInitialization_GameState_WHITE_TURN_InitialTurnIsWhite() {
    Board board = new Board();
    assertEquals(GameState.WHITE_TURN, board.getGameState());
  }

  @Test
  void BoardInitialization_Valid8x8Configuration_BoardIsProperlyInitialized() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    assertEquals(8, snapshot.length);

    for (int row = 0; row < 8; row++) {
      assertNotNull(snapshot[row]);
      assertEquals(8, snapshot[row].length);
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
}
