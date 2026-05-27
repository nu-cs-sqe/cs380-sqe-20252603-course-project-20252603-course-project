package domain.piece;

import domain.Location;

public abstract class Piece {

  private final PieceType type;
  private final PieceColor color;

  public Piece(PieceType type, PieceColor color) {
    if (type == null) {
      throw new IllegalArgumentException("type must not be null");
    }
    if (color == null) {
      throw new IllegalArgumentException("color must not be null");
    }
    this.type = type;
    this.color = color;
  }

  public PieceType getType() {
    return type;
  }

  public PieceColor getColor() {
    return color;
  }

  @Override
  public String toString() {
    return color + " " + type;
  }

  public abstract Piece makeCopy();

  public abstract boolean isValidMoveShape(Location from, Location to);

  public boolean hasMoved() {
    throw new UnsupportedOperationException("not yet implemented");
  }

  public boolean canAttack(Location from, Location to, Piece[][] board) {
    throw new UnsupportedOperationException("not yet implemented");
  }

  public static boolean hasPieceBetween(Location from, Location to, Piece[][] board) {
    int rowStep = Integer.signum(to.getX() - from.getX());
    int colStep = Integer.signum(to.getY() - from.getY());
    int row = from.getX() + rowStep;
    int col = from.getY() + colStep;
    while (row != to.getX() || col != to.getY()) {
      if (board[row][col] != null) {
        return true;
      }
      row += rowStep;
      col += colStep;
    }
    return false;
  }

  protected static void requireLocations(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
  }
}