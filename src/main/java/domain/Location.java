package domain;

import java.util.ArrayList;
import java.util.List;

public class Location {
  private static final int MIN_BOARD_COORDINATE = 0;
  private static final int MAX_BOARD_COORDINATE = 7;

  private final int x;
  private final int y;

  public Location(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  /** Returns true if this location is within the 8×8 board. */
  public boolean isOnBoard() {
    return false;
  }

  /** Returns squares strictly between this location and {@code to} along the same rank, file, or diagonal. */
  public List<Location> getIntermediateSquares(Location to) {
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    return new ArrayList<>();
  }
}
