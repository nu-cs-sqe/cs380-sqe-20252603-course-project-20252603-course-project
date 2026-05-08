package domain.piece;

import domain.Location;

public final class King extends Piece {
    private static final int MIN_BOARD_COORDINATE = 0;
    private static final int MAX_BOARD_COORDINATE = 7;

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
        if (from == null) {
            throw new IllegalArgumentException("from must not be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("to must not be null");
        }
        if (!isOnBoard(to)) {
            return false;
        }
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        if (dx == 0 && dy == 0) {
            return false;
        }
        if (Math.abs(dx) > 1) {
            return false;
        }
        if (Math.abs(dy) > 1) {
            return false;
        }
        return true;
    }

    private boolean isOnBoard(Location location) {
        return location.getX() >= MIN_BOARD_COORDINATE && location.getX() <= MAX_BOARD_COORDINATE
                && location.getY() >= MIN_BOARD_COORDINATE && location.getY() <= MAX_BOARD_COORDINATE;
    }
}
