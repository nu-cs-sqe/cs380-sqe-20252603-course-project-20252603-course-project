package domain.piece;

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
}
