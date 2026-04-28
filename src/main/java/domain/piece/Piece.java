package domain.piece;

public abstract class Piece {

    private final PieceType type;
    private final PieceColor color;
    protected final boolean canJump;
    private boolean hasMoved;

    public Piece(PieceType type, PieceColor color, boolean canJump) {
        this.type = type;
        this.color = color;
        this.canJump = canJump;
        this.hasMoved = false;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    public boolean isSameColor(Piece piece) {
        return this.color == piece.color;
    }

    public boolean canJump() {
        return canJump;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void changeToMoved() {
        this.hasMoved = true;
    }

    public void resetHasMoved() {
        this.hasMoved = false;
    }

    public abstract Piece makeCopy();

    @Override
    public String toString() {
        return color + " " + type;
    }
}
