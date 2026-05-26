package domain.piece;

import domain.Location;

public final class Queen extends Piece {

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

  public boolean isValidMoveShape(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    int dx = Math.abs(to.getX() - from.getX());
    int dy = Math.abs(to.getY() - from.getY());
    if (dx == 0 && dy == 0) {
      return false;
    }
    return dx == 0 || dy == 0 || dx == dy;
  }
}
