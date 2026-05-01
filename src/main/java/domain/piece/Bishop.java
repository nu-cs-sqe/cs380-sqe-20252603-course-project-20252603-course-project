package domain.piece;

public class Bishop extends Piece {
    public Bishop(PieceColor color) {
        super(PieceType.BISHOP, color);
    }

    @Override
    public Piece makeCopy() {
        throw new UnsupportedOperationException("makeCopy is not implemented yet");
    }
}
