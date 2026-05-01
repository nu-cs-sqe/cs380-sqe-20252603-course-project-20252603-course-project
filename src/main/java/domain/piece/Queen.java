package domain.piece;

public class Queen extends Piece {
    public Queen(PieceColor color) {
        super(PieceType.QUEEN, requireColor(color));
    }

    @Override
    public Piece makeCopy() {
        return new Queen(getColor());
    }

    private static PieceColor requireColor(PieceColor color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null");
        }
        return color;
    }
}
