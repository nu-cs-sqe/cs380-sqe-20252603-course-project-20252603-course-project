package domain.piece;

import domain.Location;

/** Represents a Rook chess piece. */
public final class Rook extends Piece {

  /** Constructs a Rook with the given color. */
  public Rook(PieceColor color) {
    super(PieceType.ROOK, color);
    if (color == null) {
      throw new IllegalArgumentException("color must not be null");
    }
  }

  @Override
  public Piece makeCopy() {
    return new Rook(getColor());
  }

  /** Returns true if the move from {@code from} to {@code to} follows a rook's movement pattern. */
  public boolean isValidMoveShape(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    if (!to.isOnBoard()) {
      return false;
    }
    int dx = to.getX() - from.getX();
    int dy = to.getY() - from.getY();
    if (dx == 0 && dy == 0) {
      return false;
    }
    return dx == 0 || dy == 0;
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
    if (!isValidMoveShape(from, to)) {
      return false;
    }
    for (Location square : from.getIntermediateSquares(to)) {
      if (board[square.getY()][square.getX()] != null) {
        return false;
      }
    }
    Piece target = board[to.getY()][to.getX()];
    return target == null || target.getColor() != getColor();
  }
}
