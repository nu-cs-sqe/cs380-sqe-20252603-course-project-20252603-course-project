package domain;

public class Location {

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
}