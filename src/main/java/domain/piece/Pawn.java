package domain.piece;

import domain.Location;

/** Represents a Pawn chess piece. */
public final class Pawn extends Piece {
  private static final int WHITE_STARTING_ROW = 6;
  private static final int BLACK_STARTING_ROW = 1;
  private static final int WHITE_FORWARD = -1;
  private static final int BLACK_FORWARD = 1;
  private static final int DOUBLE_STEP = 2;
  private static final int MAX_DIAGONAL_OFFSET = 1;
  private static final int MAX_BOARD_INDEX = 7;

  /** Constructs a Pawn with the given color. */
  public Pawn(PieceColor color) {
    super(PieceType.PAWN, color);
    if (color == null) {
      throw new IllegalArgumentException("color must not be null");
    }
  }

  @Override
  public Piece makeCopy() {
    return new Pawn(getColor());
  }

  /** Returns true if the move shape from {@code from} to {@code to} is valid for a Pawn. */
  public boolean isValidMoveShape(Location from, Location to) {
    if (from == null || to == null) {
      throw new IllegalArgumentException(
          from == null ? "from must not be null" : "to must not be null");
    }
    if (!isOnBoard(from) || !isOnBoard(to)) {
      return false;
    }

    int dx = to.getX() - from.getX();
    int dy = to.getY() - from.getY();
    int forward = getColor() == PieceColor.WHITE ? WHITE_FORWARD : BLACK_FORWARD;
    int startingRow = getColor() == PieceColor.WHITE ? WHITE_STARTING_ROW : BLACK_STARTING_ROW;

    boolean singleStep = dy == forward && Math.abs(dx) <= MAX_DIAGONAL_OFFSET;
    boolean doubleStep = from.getY() == startingRow && dy == DOUBLE_STEP * forward && dx == 0;

    return singleStep || doubleStep;
  }

  private boolean isOnBoard(Location location) {
    return location.getX() >= 0 && location.getX() <= MAX_BOARD_INDEX
        && location.getY() >= 0 && location.getY() <= MAX_BOARD_INDEX;
  }
}