package domain.piece;

import domain.Location;

public abstract class Piece {
  protected static final int MIN_BOARD_COORDINATE = 0;
  protected static final int MAX_BOARD_COORDINATE = 7;

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

  public boolean canJump() {
    return false;
  }

  public boolean isSameColor(Piece other) {
    if (other == null) {
      throw new IllegalArgumentException("other must not be null");
    }
    return this.color == other.color;
  }

  @Override
  public String toString() {
    return color + " " + type;
  }

  public abstract Piece makeCopy();

  public abstract boolean isValidMoveShape(Location from, Location to);

  protected boolean isOnBoard(Location location) {
    return location.getX() >= MIN_BOARD_COORDINATE && location.getX() <= MAX_BOARD_COORDINATE
        && location.getY() >= MIN_BOARD_COORDINATE && location.getY() <= MAX_BOARD_COORDINATE;
  }
}
