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
}
