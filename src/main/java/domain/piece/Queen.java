package domain.piece;

import domain.Location;

public class Queen extends Piece {
  public Queen(PieceColor color) {
    super(PieceType.QUEEN, color);
  }

  @Override
  public Piece makeCopy() {
    return new Queen(getColor());
  }

  @Override
  public boolean isValidMoveShape(Location from, Location to) {
    throw new UnsupportedOperationException("not yet implemented");
  }
}
