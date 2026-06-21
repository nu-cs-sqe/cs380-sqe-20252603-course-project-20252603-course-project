package domain.piece;

public class Piece {
    private final PieceType pieceType;
    private final Color color;

    public Piece(PieceType pieceType, Color color) {
        if (pieceType == null) {
            throw new IllegalArgumentException("Piece type cannot be null");
        }
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }

        this.pieceType = pieceType;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
