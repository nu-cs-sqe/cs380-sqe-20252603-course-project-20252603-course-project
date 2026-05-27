package ui;

import domain.Board;
import domain.Location;
import domain.piece.Piece;

public class BoardController {

  private final Board board;
  private BoardView boardView;
  private Location selectedLocation;

  public BoardController() {
    this.board = new Board();
    this.selectedLocation = null;
  }

  void setBoardView(BoardView boardView) {
    this.boardView = boardView;
  }

  /**
   * Handles the workflow of selecting a source piece and a destination square.
   */
  public void handleSquareClick(Location location) {
    if (location == null) {
      return;
    }

    Piece[][] snapshot = board.getSnapshot();
    Piece clickedPiece = snapshot[location.getY()][location.getX()];

    if (selectedLocation == null) {
      selectSource(location, clickedPiece);
      return;
    }

    if (isSameSquare(location)) {
      cancelSelection();
      return;
    }

    if (isSwitchSource(location, clickedPiece, snapshot)) {
      switchSource(location);
      return;
    }

    attemptMove(location);
  }

  private void selectSource(Location location, Piece clickedPiece) {
    if (clickedPiece != null) {
      selectedLocation = location;
      System.out.println("Source selected: " + location.getX() + ", " + location.getY());
    }
  }

  private void cancelSelection() {
    selectedLocation = null;
    System.out.println("Selection canceled.");
  }

  private void switchSource(Location location) {
    selectedLocation = location;
    System.out.println("Source switched to: " + location.getX() + ", " + location.getY());
  }

  private void attemptMove(Location destination) {
    boolean success = board.movePiece(selectedLocation, destination);

    if (success) {
      System.out.println("Move successful from " + selectedLocation + " to " + destination);
      selectedLocation = null;
    } else {
      System.out.println("Invalid move attempted. Selection maintained or rejected.");
    }
  }

  private boolean isSameSquare(Location location) {
    return selectedLocation.getX() == location.getX()
        && selectedLocation.getY() == location.getY();
  }

  private boolean isSwitchSource(Location location, Piece clickedPiece, Piece[][] snapshot) {
    return clickedPiece != null
        && clickedPiece.getColor()
        == snapshot[selectedLocation.getY()][selectedLocation.getX()].getColor();
  }

  public Piece[][] getBoardSnapshot() {
    return this.board.getSnapshot();
  }
}