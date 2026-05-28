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

  /** Constructs a Pawn with the given color. */
  public Pawn(PieceColor color) {
    super(PieceType.PAWN, color);
  }

  @Override
  public Piece makeCopy() {
    return new Pawn(getColor());
  }

  @Override
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

    int forward;
    int startingRow;
    if (getColor() == PieceColor.WHITE) {
      forward = WHITE_FORWARD;
      startingRow = WHITE_STARTING_ROW;
    } else {
      forward = BLACK_FORWARD;
      startingRow = BLACK_STARTING_ROW;
    }

    boolean singleStep = dy == forward && Math.abs(dx) <= MAX_DIAGONAL_OFFSET;
    boolean doubleStep = from.getY() == startingRow && dy == DOUBLE_STEP * forward && dx == 0;

    return singleStep || doubleStep;
  }
}
