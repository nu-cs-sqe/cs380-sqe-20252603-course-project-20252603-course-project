package domain.piece;

import domain.Location;

/** Represents a Knight chess piece. */
public final class Knight extends Piece {
  private static final int MIN_BOARD_COORDINATE = 0;
  private static final int MAX_BOARD_COORDINATE = 7;

  /** Constructs a Knight with the given color. */
  public Knight(PieceColor color) {
    super(PieceType.KNIGHT, color);
  }

  @Override
  public Piece makeCopy() {
    return new Knight(getColor());
  }

  @Override
  public boolean hasMoved() {
    throw new UnsupportedOperationException("not yet implemented");
  }

  /** Returns true if the move shape from {@code from} to {@code to} is valid for a Knight. */
  public boolean isValidMoveShape(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    if (!isOnBoard(to)) {
      return false;
    }
    int dx = Math.abs(to.getX() - from.getX());
    int dy = Math.abs(to.getY() - from.getY());
    return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
  }

  private boolean isOnBoard(Location location) {
    return location.getX() >= MIN_BOARD_COORDINATE && location.getX() <= MAX_BOARD_COORDINATE
        && location.getY() >= MIN_BOARD_COORDINATE && location.getY() <= MAX_BOARD_COORDINATE;
  }
}
