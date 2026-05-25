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

  /** Returns true if the move from {@code from} to {@code to} follows a rook's movement pattern. */
  public boolean isValidMoveShape(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    return false;
  }

  /** Returns true if the move is legal given the board state. */
  public boolean isLegalMove(Location from, Location to, Piece[][] board) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    if (board == null) {
      throw new IllegalArgumentException("board must not be null");
    }
    return false;
  }

  private boolean isOnBoard(Location location) {
    return location.getX() >= MIN_BOARD_COORDINATE && location.getX() <= MAX_BOARD_COORDINATE
        && location.getY() >= MIN_BOARD_COORDINATE && location.getY() <= MAX_BOARD_COORDINATE;
  }
}
