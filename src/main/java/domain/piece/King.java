package domain.piece;

import domain.Location;

public class King extends Piece {
    public King(PieceColor color) {
        super(PieceType.KING, color);
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
    }

    @Override
    public Piece makeCopy() {
        return new King(getColor());
    }

    public boolean isValidMoveShape(Location from, Location to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        if (dx == 0 && dy == 0) {
            return false;
        }
        if (Math.abs(dx) > 1) {
            return false;
        }
        return true;
    }
}
