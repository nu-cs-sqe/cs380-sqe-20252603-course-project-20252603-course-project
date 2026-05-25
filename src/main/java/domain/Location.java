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
    return x >= MIN_BOARD_COORDINATE && x <= MAX_BOARD_COORDINATE
        && y >= MIN_BOARD_COORDINATE && y <= MAX_BOARD_COORDINATE;
  }

  /** Returns squares strictly between this location and {@code to} along the same rank, file, or diagonal. */
  public List<Location> getIntermediateSquares(Location to) {
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
    List<Location> squares = new ArrayList<>();
    int stepX = Integer.signum(to.getX() - x);
    int stepY = Integer.signum(to.getY() - y);
    if (stepX == 0 && stepY == 0) {
      return squares;
    }
    int cx = x + stepX;
    int cy = y + stepY;
    while (cx != to.getX() || cy != to.getY()) {
      squares.add(new Location(cx, cy));
      cx += stepX;
      cy += stepY;
    }
    return squares;
  }
}
