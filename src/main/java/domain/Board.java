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

  /**
   * Constructs a board and initializes pieces.
   */
  public Board() {
    initializeBoard();
  }

  /**
   * Initializes all chess pieces in starting positions.
   */
  private void initializeBoard() {
    pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];

    initializePawns();
    initializeRooks();
    initializeKnights();
    initializeBishops();
    initializeRoyalPieces();
  }

  /**
   * Initializes pawns.
   */
  private void initializePawns() {
    for (int col = 0; col < TOTAL_COLS; col++) {
      pieces[BLACK_PAWN_ROW][col] = new Pawn(BLACK);
      pieces[WHITE_PAWN_ROW][col] = new Pawn(WHITE);
    }
  }

  /**
   * Initializes rooks.
   */
  private void initializeRooks() {
    pieces[BLACK_BACK_RANK][0] = new Rook(BLACK);
    pieces[BLACK_BACK_RANK][7] = new Rook(BLACK);

    pieces[WHITE_BACK_RANK][0] = new Rook(WHITE);
    pieces[WHITE_BACK_RANK][7] = new Rook(WHITE);
  }

  /**
   * Initializes knights.
   */
  private void initializeKnights() {
    pieces[BLACK_BACK_RANK][1] = new Knight(BLACK);
    pieces[BLACK_BACK_RANK][6] = new Knight(BLACK);

    pieces[WHITE_BACK_RANK][1] = new Knight(WHITE);
    pieces[WHITE_BACK_RANK][6] = new Knight(WHITE);
  }

  /**
   * Initializes bishops.
   */
  private void initializeBishops() {
    pieces[BLACK_BACK_RANK][2] = new Bishop(BLACK);
    pieces[BLACK_BACK_RANK][5] = new Bishop(BLACK);

    pieces[WHITE_BACK_RANK][2] = new Bishop(WHITE);
    pieces[WHITE_BACK_RANK][5] = new Bishop(WHITE);
  }

  /**
   * Initializes queens and kings.
   */
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

  /**
   * Moves a piece from one location on the board to a new one.
   */
  public boolean movePiece(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }

    Piece piece = pieces[from.getY()][from.getX()];
    if (piece == null) {
      return false;
    }

    Piece destinationPiece = pieces[to.getY()][to.getX()];

    // reject same-color capture
    if (destinationPiece != null && destinationPiece.getColor() == piece.getColor()) {
      return false;
    }

    boolean valid;

    switch (piece.getType()) {
      case PAWN:
        valid = ((Pawn) piece).isValidMoveShape(from, to);
        if (valid) {
          int dx = to.getX() - from.getX();
          int dy = to.getY() - from.getY();

          if (dx == 0) {
            // Straight moves (1 or 2 steps) require the destination to be empty
            if (destinationPiece != null) return false;

            // 2-step move requires the skipped square to be empty as well
            if (Math.abs(dy) == 2 && !isPathClear(from, to)) return false;
          } else {
            // Diagonal moves require an enemy piece to be present
            if (destinationPiece == null) return false;
          }
        }
        break;
      case ROOK:
        valid = ((Rook) piece).isValidMoveShape(from, to);
        if (valid && !isPathClear(from, to)) return false;
        break;
      case KNIGHT:
        valid = ((Knight) piece).isValidMoveShape(from, to);
        break;
      case BISHOP:
        valid = ((Bishop) piece).isValidMoveShape(from, to);
        if (valid && !isPathClear(from, to)) return false;
        break;
      case QUEEN:
        valid = ((Queen) piece).isValidMoveShape(from, to);
        if (valid && !isPathClear(from, to)) return false;
        break;
      case KING:
        valid = ((King) piece).isValidMoveShape(from, to);
        break;
      default:
        throw new IllegalStateException("Unknown piece type: " + piece.getType());
    }

    if (!valid) {
      return false;
    }

    pieces[to.getY()][to.getX()] = piece;
    pieces[from.getY()][from.getX()] = null;

    return true;
  }

  private boolean isPathClear(Location from, Location to) {
    int dx = Integer.compare(to.getX(), from.getX());
    int dy = Integer.compare(to.getY(), from.getY());

    int x = from.getX() + dx;
    int y = from.getY() + dy;

    while (x != to.getX() || y != to.getY()) {
      if (pieces[y][x] != null) {
        return false;
      }
      x += dx;
      y += dy;
    }

    return true;
  }
}