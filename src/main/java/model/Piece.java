package model;

public class Piece {

    private final PieceType type;
    private final Color color;

    public Piece(PieceType type, Color color) {
        if (type == null) throw new IllegalArgumentException("PieceType must not be null");
        if (color == null) throw new IllegalArgumentException("Color must not be null");
        this.type = type;
        this.color = color;
    }

    public PieceType getType() { return type; }
    public Color getColor() { return color; }
}