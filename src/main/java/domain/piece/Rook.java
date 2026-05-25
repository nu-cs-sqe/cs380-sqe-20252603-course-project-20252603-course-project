package domain.piece;

import domain.Location;

/** Represents a Rook chess piece. */
public final class Rook extends Piece {
  private static final int MIN_BOARD_COORDINATE = 0;
  private static final int MAX_BOARD_COORDINATE = 7;

  /** Constructs a Rook with the given color. */
  public Rook(PieceColor color) {
    super(PieceType.ROOK, color);
    if (color == null) {
      throw new IllegalArgumentException("color must not be null");
    }
  }

  @Override
  public Piece makeCopy() {
    return new Rook(getColor());
  }

  /** Returns true if the move shape from {@code from} to {@code to} is valid for a Rook. */
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
    int dx = to.getX() - from.getX();
    int dy = to.getY() - from.getY();
    if (dx == 0 && dy == 0) {
      return false;
    }
    return dx == 0 || dy == 0;
  }

  private boolean isOnBoard(Location location) {
    return location.getX() >= MIN_BOARD_COORDINATE && location.getX() <= MAX_BOARD_COORDINATE
        && location.getY() >= MIN_BOARD_COORDINATE && location.getY() <= MAX_BOARD_COORDINATE;
  }
}
