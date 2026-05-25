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

  /** Returns true if the move shape from {@code from} to {@code to} is a valid bishop shape. */
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
    return dx == dy && dx != 0;
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
