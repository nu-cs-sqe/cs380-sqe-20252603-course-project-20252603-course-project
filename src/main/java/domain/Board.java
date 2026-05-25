package domain;

import static domain.piece.PieceColor.BLACK;
import static domain.piece.PieceColor.WHITE;

import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Queen;
import domain.piece.Rook;

/**
 * Represents an 8x8 chess board and initializes the starting position.
 */
public class Board {

  private static final int TOTAL_ROWS = 8;
  private static final int TOTAL_COLS = 8;

  private static final int BLACK_BACK_RANK = 0;
  private static final int BLACK_PAWN_ROW = 1;
  private static final int WHITE_PAWN_ROW = 6;
  private static final int WHITE_BACK_RANK = 7;

  private Piece[][] pieces;

  public Board() {
    initializeBoard();
  }

  private void initializeBoard() {
    pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];

    initializePawns();
    initializeRooks();
    initializeKnights();
    initializeBishops();
    initializeRoyalPieces();
  }

  private void initializePawns() {
    for (int col = 0; col < TOTAL_COLS; col++) {
      pieces[BLACK_PAWN_ROW][col] = new Pawn(BLACK);
      pieces[WHITE_PAWN_ROW][col] = new Pawn(WHITE);
    }
  }

  private void initializeRooks() {
    pieces[BLACK_BACK_RANK][0] = new Rook(BLACK);
    pieces[BLACK_BACK_RANK][7] = new Rook(BLACK);

    pieces[WHITE_BACK_RANK][0] = new Rook(WHITE);
    pieces[WHITE_BACK_RANK][7] = new Rook(WHITE);
  }

  private void initializeKnights() {
    pieces[BLACK_BACK_RANK][1] = new Knight(BLACK);
    pieces[BLACK_BACK_RANK][6] = new Knight(BLACK);

    pieces[WHITE_BACK_RANK][1] = new Knight(WHITE);
    pieces[WHITE_BACK_RANK][6] = new Knight(WHITE);
  }

  private void initializeBishops() {
    pieces[BLACK_BACK_RANK][2] = new Bishop(BLACK);
    pieces[BLACK_BACK_RANK][5] = new Bishop(BLACK);

    pieces[WHITE_BACK_RANK][2] = new Bishop(WHITE);
    pieces[WHITE_BACK_RANK][5] = new Bishop(WHITE);
  }

  private void initializeRoyalPieces() {
    pieces[BLACK_BACK_RANK][3] = new Queen(BLACK);
    pieces[BLACK_BACK_RANK][4] = new King(BLACK);

    pieces[WHITE_BACK_RANK][3] = new Queen(WHITE);
    pieces[WHITE_BACK_RANK][4] = new King(WHITE);
  }

  /**
   * Returns a deep-copied snapshot of the board.
   */
  public Piece[][] getSnapshot() {
    Piece[][] snapshot = new Piece[TOTAL_ROWS][TOTAL_COLS];

    for (int row = 0; row < TOTAL_ROWS; row++) {
      for (int col = 0; col < TOTAL_COLS; col++) {
        if (pieces[row][col] != null) {
          snapshot[row][col] = pieces[row][col].makeCopy();
        }
      }
    }

    return snapshot;
  }
}