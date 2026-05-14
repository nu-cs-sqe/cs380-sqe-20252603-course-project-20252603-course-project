package domain.piece;

import domain.Location;

/** Represents a Pawn chess piece. */
public final class Pawn extends Piece {
  private static final int MIN_BOARD_COORDINATE = 0;
  private static final int MAX_BOARD_COORDINATE = 7;
  private static final int WHITE_STARTING_ROW = 6;
  private static final int BLACK_STARTING_ROW = 1;
  private static final int WHITE_FORWARD = -1;
  private static final int BLACK_FORWARD = 1;
  private static final int DOUBLE_STEP = 2;
  private static final int MAX_DIAGONAL_OFFSET = 1;

  /** Constructs a Pawn with the given color. */
  public Pawn(PieceColor color) {
    super(PieceType.PAWN, color);
  }

  @Override
  public Piece makeCopy() {
    return new Pawn(getColor());
  }

  @Override
  public boolean hasMoved() {
    throw new UnsupportedOperationException("not yet implemented");
  }

  /** Returns true if the move shape from {@code from} to {@code to} is valid for a Pawn. */
  public boolean isValidMoveShape(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    if (!isOnBoard(from) || !isOnBoard(to)) {
      return false;
    }
    int dx = to.getX() - from.getX();
    int dy = to.getY() - from.getY();

    int forward = getForwardDirection();
    int startingRow = getStartingRow();

    if (from.getY() != startingRow) {
      return false;
    }

    boolean singleStep = dy == forward && Math.abs(dx) <= MAX_DIAGONAL_OFFSET;
    boolean doubleStep = dy == DOUBLE_STEP * forward && dx == 0;

    return singleStep || doubleStep;
  }

  private int getForwardDirection() {
    if (getColor() == PieceColor.WHITE) {
      return WHITE_FORWARD;
    }
    return BLACK_FORWARD;
  }

  private int getStartingRow() {
    if (getColor() == PieceColor.WHITE) {
      return WHITE_STARTING_ROW;
    }
    return BLACK_STARTING_ROW;
  }

  private boolean isOnBoard(Location location) {
    return location.getX() >= MIN_BOARD_COORDINATE && location.getX() <= MAX_BOARD_COORDINATE
        && location.getY() >= MIN_BOARD_COORDINATE && location.getY() <= MAX_BOARD_COORDINATE;
  }
}
