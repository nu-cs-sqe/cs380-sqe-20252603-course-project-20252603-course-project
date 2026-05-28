package domain.piece;

import domain.Location;

public final class Rook extends Piece {

  public Rook(PieceColor color) {
    super(PieceType.ROOK, color);
  }

  @Override
  public Piece makeCopy() {
    return new Rook(getColor());
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
    return dx == 0 || dy == 0;
  }
}
