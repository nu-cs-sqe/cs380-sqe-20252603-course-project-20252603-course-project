package domain.piece;

import domain.Location;

public class Rook extends Piece {
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
      return true;
  }
}
