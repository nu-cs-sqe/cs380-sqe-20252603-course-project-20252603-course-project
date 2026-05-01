package domain.piece;

public class Bishop extends Piece {
    public Bishop(PieceColor color) {
        super(PieceType.BISHOP, requireColor(color));
    }

    @Override
    public Piece makeCopy() {
        return new Bishop(getColor());
    }

    private static PieceColor requireColor(PieceColor color) {
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
        return color;
    }
}
