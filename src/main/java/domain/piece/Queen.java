package domain.piece;

import domain.Location;

public class Queen extends Piece {
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
      return true;
  }
}
