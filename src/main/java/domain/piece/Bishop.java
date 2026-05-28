package domain.piece;

import domain.Location;

public final class Bishop extends Piece {

  public Bishop(PieceColor color) {
    super(PieceType.BISHOP, color);
  }

  @Override
  public Piece makeCopy() {
    return new Bishop(getColor());
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
    return dx == dy && dx != 0;
  }
}
