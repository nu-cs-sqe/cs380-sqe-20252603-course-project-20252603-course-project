package domain.piece;

import domain.Location;

public class Pawn extends Piece {
    public Pawn(PieceColor color) {
        super(PieceType.PAWN, color);
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
    }

    @Override
    public Piece makeCopy() {
        return new Pawn(getColor());
    }

    public boolean isValidMoveShape(Location from, Location to) {
        if (from == null) {
            throw new IllegalArgumentException("from must not be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("to must not be null");
        }
        if (!isOnBoard(from) || !isOnBoard(to)) {
            return false;
        }
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        if (getColor() == PieceColor.WHITE && dx == 0 && from.getY() == 6 && dy == -1) {
            return true;
        }
        if (getColor() == PieceColor.WHITE && dx == -1 && from.getY() == 6 && dy == -1) {
            return true;
        }
        if (getColor() == PieceColor.WHITE && dx == 1 && from.getY() == 6 && dy == -1) {
            return true;
        }
        if (getColor() == PieceColor.BLACK && dx == 0 && from.getY() == 1 && dy == 1) {
            return true;
        }
        if (getColor() == PieceColor.BLACK && dx == -1 && from.getY() == 1 && dy == 1) {
            return true;
        }
        if (getColor() == PieceColor.BLACK && dx == 1 && from.getY() == 1 && dy == 1) {
            return true;
        }
        return (getColor() == PieceColor.WHITE && dx == 0 && from.getY() == 6 && dy == -2)
                || (getColor() == PieceColor.BLACK && dx == 0 && from.getY() == 1 && dy == 2);
    }

    private boolean isOnBoard(Location location) {
        return location.getX() >= 0 && location.getX() <= 7
                && location.getY() >= 0 && location.getY() <= 7;
    }
}
