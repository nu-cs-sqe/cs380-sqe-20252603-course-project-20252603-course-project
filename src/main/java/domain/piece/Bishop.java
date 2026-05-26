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

  public boolean isValidMoveShape(Location from, Location to) {
    requireLocations(from, to);
    int dx = Math.abs(to.getX() - from.getX());
    int dy = Math.abs(to.getY() - from.getY());
    return dx == dy && dx != 0;
  }
}
