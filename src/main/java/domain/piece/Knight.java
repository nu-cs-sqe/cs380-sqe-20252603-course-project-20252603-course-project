package domain.piece;

import domain.Location;

/** Represents a Knight chess piece. */
public final class Knight extends Piece {

  /** Constructs a Knight with the given color. */
  public Knight(PieceColor color) {
    super(PieceType.KNIGHT, color);
  }

  @Override
  public Piece makeCopy() {
    return new Knight(getColor());
  }

  @Override
  public boolean canJump() {
    return true;
  }

  @Override
  public boolean isValidMoveShape(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    if (!isOnBoard(to)) {
      return false;
    }
    int dx = Math.abs(to.getX() - from.getX());
    int dy = Math.abs(to.getY() - from.getY());
    return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
  }
}
