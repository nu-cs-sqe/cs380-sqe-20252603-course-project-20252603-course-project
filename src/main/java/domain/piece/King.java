package domain.piece;

public class King extends Piece {
    public King(PieceColor color) {
        super(PieceType.KING, color);
        if (color == null) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Piece makeCopy() {
        throw new UnsupportedOperationException();
    }
}
