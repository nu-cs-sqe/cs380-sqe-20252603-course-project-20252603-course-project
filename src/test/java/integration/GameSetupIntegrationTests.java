package integration;

import domain.GameState;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Piece;
import org.junit.jupiter.api.Test;
import ui.BoardController;
import ui.GameStatsView;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameSetupIntegrationTests {

  // --- Black back rank (row 0) ---

  @Test
  public void BoardSnapshot_BlackBackRankCol0_IsBlackRook() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.ROOK, snapshot[0][0].getType());
    assertEquals(PieceColor.BLACK, snapshot[0][0].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol1_IsBlackKnight() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.KNIGHT, snapshot[0][1].getType());
    assertEquals(PieceColor.BLACK, snapshot[0][1].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol2_IsBlackBishop() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.BISHOP, snapshot[0][2].getType());
    assertEquals(PieceColor.BLACK, snapshot[0][2].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol3_IsBlackQueen() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.QUEEN, snapshot[0][3].getType());
    assertEquals(PieceColor.BLACK, snapshot[0][3].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol4_IsBlackKing() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.KING, snapshot[0][4].getType());
    assertEquals(PieceColor.BLACK, snapshot[0][4].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol5_IsBlackBishop() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.BISHOP, snapshot[0][5].getType());
    assertEquals(PieceColor.BLACK, snapshot[0][5].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol6_IsBlackKnight() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.KNIGHT, snapshot[0][6].getType());
    assertEquals(PieceColor.BLACK, snapshot[0][6].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol7_IsBlackRook() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.ROOK, snapshot[0][7].getType());
    assertEquals(PieceColor.BLACK, snapshot[0][7].getColor());
  }

  // --- Row 1 — black pawns ---

  @Test
  public void BoardSnapshot_Row1AllCols_AreBlackPawns() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    for (int col = 0; col < 8; col++) {
      assertEquals(PieceType.PAWN, snapshot[1][col].getType(),
          "Expected PAWN at row 1 col " + col);
      assertEquals(PieceColor.BLACK, snapshot[1][col].getColor(),
          "Expected BLACK at row 1 col " + col);
    }
  }

  // --- Controller snapshot shape ---

  @Test
  public void BoardController_GetBoardSnapshot_ReturnsNonNull() {
    assertNotNull(new BoardController().getBoardSnapshot());
  }

  @Test
  public void BoardController_GetBoardSnapshot_ReturnsEightRows() {
    assertEquals(8, new BoardController().getBoardSnapshot().length);
  }

  @Test
  public void BoardController_GetBoardSnapshot_ReturnsEightColumns() {
    assertEquals(8, new BoardController().getBoardSnapshot()[0].length);
  }

  @Test
  public void BoardController_GetBoardSnapshotRow7Col4_IsWhiteKing() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.KING, snapshot[7][4].getType());
    assertEquals(PieceColor.WHITE, snapshot[7][4].getColor());
  }

  // --- GameStatsView ---

  @Test
  public void GameStatsView_InitializedWithGameStateWhiteTurn_DoesNotThrow() {
    assertDoesNotThrow(() -> new GameStatsView("Alice", "Bob", GameState.WHITE_TURN));
  }
}
