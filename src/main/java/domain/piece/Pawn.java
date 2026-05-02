package domain.piece;

public class Pawn extends Piece {
    public Pawn(PieceColor color) {
        super(PieceType.PAWN, color);
        if (color == null) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Piece makeCopy() {
        return new Pawn(getColor());
    }
}
