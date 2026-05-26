package domain.piece;

import domain.Location;

public final class Rook extends Piece {

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

  public boolean isValidMoveShape(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    int dx = to.getX() - from.getX();
    int dy = to.getY() - from.getY();
    if (dx == 0 && dy == 0) {
      return false;
    }
    return dx == 0 || dy == 0;
  }
}
