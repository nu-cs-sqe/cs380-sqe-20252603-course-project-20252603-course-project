package domain.piece;

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

    public abstract Piece makeCopy();
}
