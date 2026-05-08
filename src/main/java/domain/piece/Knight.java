package domain.piece;

import domain.Location;

public class Knight extends Piece {
    private static final int MIN_BOARD_COORDINATE = 0;
    private static final int MAX_BOARD_COORDINATE = 7;

    public Knight(PieceColor color) {
        super(PieceType.KNIGHT, color);
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
    }

    @Override
    public Piece makeCopy() {
        return new Knight(getColor());
    }

    public boolean isValidMoveShape(Location from, Location to) {
        if (to.getX() < MIN_BOARD_COORDINATE) {
            return false;
        }
        if (to.getY() < MIN_BOARD_COORDINATE) {
            return false;
        }
        if (to.getX() > MAX_BOARD_COORDINATE) {
            return false;
        }
        int dx = Math.abs(to.getX() - from.getX());
        int dy = Math.abs(to.getY() - from.getY());
        return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
    }
}
