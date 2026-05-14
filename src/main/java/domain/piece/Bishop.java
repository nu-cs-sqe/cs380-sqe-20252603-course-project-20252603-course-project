package domain.piece;

public class Bishop extends Piece {
    public Bishop(PieceColor color) {
        super(PieceType.BISHOP, color);
        if (color == null) {
            throw new IllegalArgumentException("color must not be null");
        }
    }

    @Override
    public Piece makeCopy() {
        return new Bishop(getColor());
    }

    @Override
    public boolean hasMoved() {
        return false;
    }
}
