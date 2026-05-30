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
import ui.BoardController;
import ui.GameStatsView;

public class GameSetupIntegrationTests {

  private static final int BOARD_SIZE = 8;
  private static final int ROOK_COL_QUEEN_SIDE = 0;
  private static final int KNIGHT_COL_QUEEN_SIDE = 1;
  private static final int BISHOP_COL_QUEEN_SIDE = 2;
  private static final int QUEEN_COL = 3;
  private static final int KING_COL = 4;
  private static final int BISHOP_COL_KING_SIDE = 5;
  private static final int KNIGHT_COL_KING_SIDE = 6;
  private static final int ROOK_COL_KING_SIDE = 7;
  private static final int BLACK_BACK_RANK = 0;
  private static final int BLACK_PAWN_RANK = 1;
  private static final int WHITE_BACK_RANK = 7;
  private static final int MIDDLE_ROW_START = 2;
  private static final int MIDDLE_ROW_END = 5;
  private static final int[] PIECE_ROWS = {0, 1, 6, 7};
  private static final int BLACK_BACK_RANK_ROW = 0;
  private static final int BLACK_PAWN_ROW = 1;
  private static final int WHITE_PAWN_ROW = 6;
  private static final int WHITE_BACK_RANK_ROW = 7;
  private static final int ROOK_COL = 0;
  private static final int KNIGHT_COL = 1;
  private static final int BISHOP_COL = 2;

  // --- Black back rank (row 0) ---

  @Test
  public void BoardSnapshot_BlackBackRankCol0_IsBlackRook() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.ROOK, snapshot[BLACK_BACK_RANK][ROOK_COL_QUEEN_SIDE].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][ROOK_COL_QUEEN_SIDE].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol1_IsBlackKnight() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.KNIGHT, snapshot[BLACK_BACK_RANK][KNIGHT_COL_QUEEN_SIDE].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][KNIGHT_COL_QUEEN_SIDE].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol2_IsBlackBishop() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.BISHOP, snapshot[BLACK_BACK_RANK][BISHOP_COL_QUEEN_SIDE].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][BISHOP_COL_QUEEN_SIDE].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol3_IsBlackQueen() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.QUEEN, snapshot[BLACK_BACK_RANK][QUEEN_COL].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][QUEEN_COL].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol4_IsBlackKing() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.KING, snapshot[BLACK_BACK_RANK][KING_COL].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][KING_COL].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol5_IsBlackBishop() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.BISHOP, snapshot[BLACK_BACK_RANK][BISHOP_COL_KING_SIDE].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][BISHOP_COL_KING_SIDE].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol6_IsBlackKnight() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.KNIGHT, snapshot[BLACK_BACK_RANK][KNIGHT_COL_KING_SIDE].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][KNIGHT_COL_KING_SIDE].getColor());
  }

  @Test
  public void BoardSnapshot_BlackBackRankCol7_IsBlackRook() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.ROOK, snapshot[BLACK_BACK_RANK][ROOK_COL_KING_SIDE].getType());
    assertEquals(PieceColor.BLACK, snapshot[BLACK_BACK_RANK][ROOK_COL_KING_SIDE].getColor());
  }

  // --- Row 1 — black pawns ---

  @Test
  public void BoardSnapshot_Row1AllCols_AreBlackPawns() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    for (int col = 0; col < BOARD_SIZE; col++) {
      assertEquals(PieceType.PAWN, snapshot[BLACK_PAWN_RANK][col].getType(),
          "Expected PAWN at row 1 col " + col);
      assertEquals(PieceColor.BLACK, snapshot[BLACK_PAWN_RANK][col].getColor(),
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
    assertEquals(BOARD_SIZE, new BoardController().getBoardSnapshot().length);
  }

  @Test
  public void BoardController_GetBoardSnapshot_ReturnsEightColumns() {
    assertEquals(BOARD_SIZE, new BoardController().getBoardSnapshot()[0].length);
  }

  @Test
  public void BoardController_GetBoardSnapshotRow7Col4_IsWhiteKing() {
    Piece[][] snapshot = new BoardController().getBoardSnapshot();
    assertEquals(PieceType.KING, snapshot[WHITE_BACK_RANK][KING_COL].getType());
    assertEquals(PieceColor.WHITE, snapshot[WHITE_BACK_RANK][KING_COL].getColor());
  }

  // --- GameStatsView ---

  @Test
  public void GameStatsView_InitializedWithGameStateWhiteTurn_DoesNotThrow() {
    assertDoesNotThrow(() -> new GameStatsView("Alice", "Bob", GameState.WHITE_TURN));
  }

  // --- Board snapshot (domain) ---

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
  }

  @Test
  void boardSetup_InitialBoard_PieceRowsArePopulated() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

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

  @Test
  void boardSetup_InitialBoard_SnapshotRow7Col0IsWhiteRook() {
    assertBackRankPiece(WHITE_BACK_RANK_ROW, ROOK_COL, PieceType.ROOK, PieceColor.WHITE);
  }

  @Test
  void boardSetup_InitialBoard_SnapshotRow7Col1IsWhiteKnight() {
    assertBackRankPiece(WHITE_BACK_RANK_ROW, KNIGHT_COL, PieceType.KNIGHT, PieceColor.WHITE);
  }

  @Test
  void boardSetup_InitialBoard_SnapshotRow7Col2IsWhiteBishop() {
    assertBackRankPiece(WHITE_BACK_RANK_ROW, BISHOP_COL, PieceType.BISHOP, PieceColor.WHITE);
  }

  @Test
  void boardSetup_InitialBoard_SnapshotRow0Col0IsBlackRook() {
    assertBackRankPiece(BLACK_BACK_RANK_ROW, ROOK_COL, PieceType.ROOK, PieceColor.BLACK);
  }

  private void assertBackRankPiece(int row, int col, PieceType expectedType, PieceColor expectedColor) {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    Piece p = snapshot[row][col];
    assertEquals(expectedType, p.getType());
    assertEquals(expectedColor, p.getColor());
  }

  @Test
  void boardSetup_InitialBoard_WhitePawnRowIsAllWhitePawns() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    for (int col = 0; col < BOARD_SIZE; col++) {
      Piece p = snapshot[WHITE_PAWN_ROW][col];
      assertEquals(PieceType.PAWN, p.getType(), "Expected PAWN at [" + WHITE_PAWN_ROW + "][" + col + "]");
      assertEquals(PieceColor.WHITE, p.getColor(), "Expected WHITE at [" + WHITE_PAWN_ROW + "][" + col + "]");
    }
  }

  @Test
  void boardSetup_InitialBoard_BlackPawnRowIsAllBlackPawns() {
    Board board = new Board();
    Piece[][] snapshot = board.getSnapshot();

    for (int col = 0; col < BOARD_SIZE; col++) {
      Piece p = snapshot[BLACK_PAWN_ROW][col];
      assertEquals(PieceType.PAWN, p.getType(), "Expected PAWN at [" + BLACK_PAWN_ROW + "][" + col + "]");
      assertEquals(PieceColor.BLACK, p.getColor(), "Expected BLACK at [" + BLACK_PAWN_ROW + "][" + col + "]");
    }
  }

  @Test
  void pieceColor_EnumDefinition_HasExactlyTwoValues() {
    assertEquals(2, PieceColor.values().length);
  }

  @Test
  void pieceColor_EnumOrdinals_BlackPrecedesWhite() {
    PieceColor[] values = PieceColor.values();

    assertEquals(PieceColor.BLACK, values[0]);
    assertEquals(PieceColor.WHITE, values[1]);
  }
}
