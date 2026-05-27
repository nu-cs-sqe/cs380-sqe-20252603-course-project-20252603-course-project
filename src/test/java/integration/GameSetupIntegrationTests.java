package integration;

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

}
