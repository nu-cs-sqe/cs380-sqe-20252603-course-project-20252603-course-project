package domain.piece;

public class Knight extends Piece {
    public Knight(PieceColor color) {
        super(PieceType.KNIGHT, requireColor(color));
    }

    @Override
    public Piece makeCopy() {
        return new Knight(getColor());
    }

    private static PieceColor requireColor(PieceColor color) {
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
        return color;
    }
}
