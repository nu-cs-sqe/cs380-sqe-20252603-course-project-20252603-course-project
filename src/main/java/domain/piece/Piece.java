package domain.piece;

import domain.Location;

public abstract class Piece {
    private final PieceType type;
    private final PieceColor color;

    public Piece(PieceType type, PieceColor color) {
        if (type == null) {
            throw new IllegalArgumentException("type must not be null");
        }
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color + " " + type;
    }

    public abstract Piece makeCopy();

    protected static void requireLocations(Location from, Location to) {
        if (from == null) {
            throw new IllegalArgumentException("from must not be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("to must not be null");
        }
    }
}
