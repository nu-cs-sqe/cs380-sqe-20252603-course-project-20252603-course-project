package domain.piece;

import domain.Location;

/** Represents a King chess piece. */
public final class King extends Piece {

  /** Constructs a King with the given color. */
  public King(PieceColor color) {
    super(PieceType.KING, color);
  }

  @Override
  public Piece makeCopy() {
    return new King(getColor());
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
    int dx = to.getX() - from.getX();
    int dy = to.getY() - from.getY();
    if (dx == 0 && dy == 0) {
      return false;
    }
    if (Math.abs(dx) > 1) {
      return false;
    }
    if (Math.abs(dy) > 1) {
      return false;
    }
    return true;
  }
}
