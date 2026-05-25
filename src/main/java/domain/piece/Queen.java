package domain.piece;

import domain.Location;

/** Represents a Queen chess piece. */
public final class Queen extends Piece {

  /** Constructs a Queen with the given color. */
  public Queen(PieceColor color) {
    super(PieceType.QUEEN, color);
    if (color == null) {
      throw new IllegalArgumentException("color must not be null");
    }
  }

  @Override
  public Piece makeCopy() {
    return new Queen(getColor());
  }

  /** Returns true if the move from {@code from} to {@code to} follows a queen's movement pattern. */
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
    int dx = Math.abs(to.getX() - from.getX());
    int dy = Math.abs(to.getY() - from.getY());
    if (dx == 0 && dy == 0) {
      return false;
    }
    return dx == 0 || dy == 0 || dx == dy;
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
