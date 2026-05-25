package domain.piece;

import domain.Location;

/** Represents a Bishop chess piece. */
public final class Bishop extends Piece {

  /** Constructs a Bishop with the given color. */
  public Bishop(PieceColor color) {
    super(PieceType.BISHOP, color);
    if (color == null) {
      throw new IllegalArgumentException("color must not be null");
    }
  }

  @Override
  public Piece makeCopy() {
    return new Bishop(getColor());
  }

  /** Returns true if the move from {@code from} to {@code to} follows a bishop's movement pattern. */
  public boolean isValidMoveShape(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    return false;
  }

  /** Returns true if the move is legal given the board state. */
  public boolean isLegalMove(Location from, Location to, Piece[][] board) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    if (board == null) {
      throw new IllegalArgumentException("board must not be null");
    }
    return false;
  }
}
