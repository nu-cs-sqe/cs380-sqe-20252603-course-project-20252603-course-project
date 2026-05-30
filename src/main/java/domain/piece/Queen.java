package domain.piece;

import domain.Location;

public final class Queen extends Piece {

  public Queen(PieceColor color) {
    super(PieceType.QUEEN, color);
  }

  @Override
  public Piece makeCopy() {
    return new Queen(getColor());
  }

  @Override
  public boolean isValidMoveShape(Location from, Location to) {
    requireLocations(from, to);
    if (!isOnBoard(to)) {
      return false;
    }
    int dx = Math.abs(to.getX() - from.getX());
    int dy = Math.abs(to.getY() - from.getY());
    if (dx == 0 && dy == 0) {
      return false;
    }
    return dx == 0 || dy == 0 || dx == dy;
  }
}
