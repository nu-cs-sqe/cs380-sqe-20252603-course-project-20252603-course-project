package domain.piece;

public class Knight extends Piece {
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
}
