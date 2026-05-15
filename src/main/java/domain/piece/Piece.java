package domain.piece;

import domain.Location;

public abstract class Piece {
  private static final int MIN_BOARD_COORDINATE = 0;
  private static final int MAX_BOARD_COORDINATE = 7;

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
}