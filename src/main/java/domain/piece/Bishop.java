package domain.piece;

import domain.Location;

public class Bishop extends Piece {
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

  public boolean isValidMoveShape(Location from, Location to) {
      return true;
  }
}
