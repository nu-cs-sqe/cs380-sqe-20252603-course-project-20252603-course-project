package domain.piece;

public class Rook extends Piece {
    public Rook(PieceColor color) {
        super(PieceType.ROOK, requireColor(color));
    }

    private static PieceColor requireColor(PieceColor color) {
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
        return color;
    }

    @Override
    public Piece makeCopy() {
        return new Rook(getColor());
    }
}
