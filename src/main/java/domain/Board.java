package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

public class Board {

  private static final int BOARD_SIZE = 8;

  private Piece[][] pieces;
  private GameState currentGameState;
  private Location whiteKingLocation;
  private Location blackKingLocation;

  public Board() {
    pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
    currentGameState = GameState.WHITE_TURN;
  }

  public Piece getPiece(int row, int col) {
    return pieces[row][col];
  }

  public void setPiece(int row, int col, Piece piece) {
    pieces[row][col] = piece;
  }

  public Location getWhiteKingLocation() {
    return whiteKingLocation;
  }

  public Location getBlackKingLocation() {
    return blackKingLocation;
  }

  public Piece[][] getSnapshot() {
    throw new UnsupportedOperationException("not yet implemented");
  }

  public GameState getCurrentGameState() {
    return currentGameState;
  }

  public void switchTurn() {
    currentGameState = (currentGameState == GameState.WHITE_TURN)
        ? GameState.BLACK_TURN
        : GameState.WHITE_TURN;
  }

  public void updateWhiteKingLocation(Location location) {
    updateKingLocation(location, PieceColor.WHITE);
  }

  public void updateBlackKingLocation(Location location) {
    updateKingLocation(location, PieceColor.BLACK);
  }

  private void updateKingLocation(Location location, PieceColor color) {
    if (location == null) {
      throw new IllegalArgumentException("location must not be null");
    }
    if (color == PieceColor.WHITE) {
      whiteKingLocation = location;
    } else {
      blackKingLocation = location;
    }
  }

  public boolean applyMoveIfKingSafe(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }

    Piece movingPiece = pieces[from.getX()][from.getY()];
    Piece capturedPiece = pieces[to.getX()][to.getY()];

    pieces[to.getX()][to.getY()] = movingPiece;
    pieces[from.getX()][from.getY()] = null;

    Location prevWhiteKingLoc = whiteKingLocation;
    Location prevBlackKingLoc = blackKingLocation;
    updateKingLocationForMove(movingPiece, to);

    boolean inCheck = isCurrentKingInCheck();

    if (inCheck) {
      pieces[from.getX()][from.getY()] = movingPiece;
      pieces[to.getX()][to.getY()] = capturedPiece;
      whiteKingLocation = prevWhiteKingLoc;
      blackKingLocation = prevBlackKingLoc;
      return false;
    }
    return true;
  }

  private void updateKingLocationForMove(Piece movingPiece, Location to) {
    if (movingPiece != null && movingPiece.getType() == PieceType.KING) {
      if (movingPiece.getColor() == PieceColor.WHITE) {
        whiteKingLocation = to;
      } else {
        blackKingLocation = to;
      }
    }
  }

  private boolean isCurrentKingInCheck() {
    PieceColor currentColor = (currentGameState == GameState.WHITE_TURN)
        ? PieceColor.WHITE : PieceColor.BLACK;
    Location kingLoc = (currentColor == PieceColor.WHITE)
        ? whiteKingLocation : blackKingLocation;
    return isKingInCheck(currentColor, kingLoc);
  }

  private boolean isKingInCheck(PieceColor kingColor, Location kingLoc) {
    for (int row = 0; row < BOARD_SIZE; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        Piece attacker = pieces[row][col];
        if (attacker != null && attacker.getColor() != kingColor) {
          if (attacker.canAttack(new Location(row, col), kingLoc, pieces)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean castle(Location kingFrom, Location kingTo, Location rookFrom, Location rookTo) {
    CastleMove castleMove = new CastleMove(kingFrom, kingTo, rookFrom, rookTo);

    Piece king = pieces[castleMove.kingFrom.getX()][castleMove.kingFrom.getY()];
    Piece rook = pieces[castleMove.rookFrom.getX()][castleMove.rookFrom.getY()];

    if (king == null || king.hasMoved()) {
      return false;
    }
    if (rook == null || rook.hasMoved()) {
      return false;
    }
    if (Piece.hasPieceBetween(castleMove.kingFrom, castleMove.rookFrom, pieces)) {
      return false;
    }

    return performCastleIfSafe(castleMove, king, rook);
  }

  private void validateCastleLocations(Location kingFrom, Location kingTo,
      Location rookFrom, Location rookTo) {
    if (kingFrom == null) {
      throw new IllegalArgumentException("kingFrom must not be null");
    }
    if (kingTo == null) {
      throw new IllegalArgumentException("kingTo must not be null");
    }
    if (rookFrom == null) {
      throw new IllegalArgumentException("rookFrom must not be null");
    }
    if (rookTo == null) {
      throw new IllegalArgumentException("rookTo must not be null");
    }
  }

  private boolean performCastleIfSafe(CastleMove castleMove, Piece king, Piece rook) {
    PieceColor kingColor = king.getColor();
    Location kingCheckLoc = (kingColor == PieceColor.WHITE)
        ? whiteKingLocation : blackKingLocation;
    if (isKingInCheck(kingColor, kingCheckLoc)) {
      return false;
    }

    int colStep = Integer.signum(castleMove.kingTo.getY() - castleMove.kingFrom.getY());
    Location transitSquare = new Location(
        castleMove.kingFrom.getX(),
        castleMove.kingFrom.getY() + colStep);
    if (isKingInCheck(kingColor, transitSquare)) {
      return false;
    }
    if (isKingInCheck(kingColor, castleMove.kingTo)) {
      return false;
    }

    moveCastlePieces(castleMove, king, rook);

    if (kingColor == PieceColor.WHITE) {
      whiteKingLocation = castleMove.kingTo;
    } else {
      blackKingLocation = castleMove.kingTo;
    }
    return true;
  }

  private void moveCastlePieces(CastleMove castleMove, Piece king, Piece rook) {
    pieces[castleMove.kingTo.getX()][castleMove.kingTo.getY()] = king;
    pieces[castleMove.kingFrom.getX()][castleMove.kingFrom.getY()] = null;
    pieces[castleMove.rookTo.getX()][castleMove.rookTo.getY()] = rook;
    pieces[castleMove.rookFrom.getX()][castleMove.rookFrom.getY()] = null;
  }

  private final class CastleMove {
    private final Location kingFrom;
    private final Location kingTo;
    private final Location rookFrom;
    private final Location rookTo;

    private CastleMove(Location kingFrom, Location kingTo, Location rookFrom, Location rookTo) {
      validateCastleLocations(kingFrom, kingTo, rookFrom, rookTo);

      this.kingFrom = kingFrom;
      this.kingTo = kingTo;
      this.rookFrom = rookFrom;
      this.rookTo = rookTo;
    }
  }
}
