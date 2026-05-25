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

    // First click: Selecting the piece to move
    if (selectedLocation == null) {
      if (clickedPiece != null) {
        selectedLocation = location;
        // Optional: boardView.highlightSquare(location);
        System.out.println("Source selected: " + location.getX() + ", " + location.getY());
      }
      return;
    }

    // Second click: Same square clicked again cancels the selection
    if (selectedLocation.getX() == location.getX() && selectedLocation.getY() == location.getY()) {
      selectedLocation = null;
      // Optional: boardView.clearHighlights();
      System.out.println("Selection canceled.");
      return;
    }

    // Second click: Clicking another piece of the same color switches the source selection
    if (clickedPiece != null && clickedPiece.getColor() == snapshot[selectedLocation.getY()][selectedLocation.getX()].getColor()) {
      selectedLocation = location;
      System.out.println("Source switched to: " + location.getX() + ", " + location.getY());
      return;
    }

    // Second click: Attempting a move execution
    boolean success = board.movePiece(selectedLocation, location);

    if (success) {
      System.out.println("Move successful from " + selectedLocation + " to " + location);
      selectedLocation = null; // Reset for the next move sequence
    } else {
      System.out.println("Invalid move attempted. Selection maintained or rejected.");
    }
  }

  public Piece[][] getBoardSnapshot() {
    return this.board.getSnapshot();
  }
}