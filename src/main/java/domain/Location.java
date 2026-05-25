package domain;

import java.util.ArrayList;
import java.util.List;

public class Location {
  private static final int MIN_BOARD_COORDINATE = 0;
  private static final int MAX_BOARD_COORDINATE = 7;

  private final int col;
  private final int row;

  public Location(int x, int y) {
    this.col = x;
    this.row = y;
  }

  public int getX() {
    return col;
  }

  public int getY() {
    return row;
  }

  /** Returns true if this location is within the 8×8 board. */
  public boolean isOnBoard() {
    return col >= MIN_BOARD_COORDINATE && col <= MAX_BOARD_COORDINATE
        && row >= MIN_BOARD_COORDINATE && row <= MAX_BOARD_COORDINATE;
  }

  /** Returns squares strictly between this location and {@code to} along the move path. */
  public List<Location> getIntermediateSquares(Location to) {
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    List<Location> squares = new ArrayList<>();
    int stepX = Integer.signum(to.getX() - col);
    int stepY = Integer.signum(to.getY() - row);
    if (stepX == 0 && stepY == 0) {
      return squares;
    }
    int cx = col + stepX;
    int cy = row + stepY;
    while (cx != to.getX() || cy != to.getY()) {
      squares.add(new Location(cx, cy));
      cx += stepX;
      cy += stepY;
    }
    return squares;
  }
}
