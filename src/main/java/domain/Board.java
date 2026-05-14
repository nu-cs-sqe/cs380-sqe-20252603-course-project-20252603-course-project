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

  public Piece getPiece(int xx, int yy) {
    return pieces[xx][yy];
  }

  public void setPiece(int xx, int yy, Piece piece) {
    pieces[xx][yy] = piece;
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
        Piece pp = pieces[row][col];
        if (pp != null && pp.getColor() != kingColor) {
          if (canAttack(pp, new Location(row, col), kingLoc)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean canAttack(Piece pp, Location from, Location to) {
    switch (pp.getType()) {
      case ROOK: return canRookAttack(from, to);
      case BISHOP: return canBishopAttack(from, to);
      case QUEEN: return canRookAttack(from, to) || canBishopAttack(from, to);
      case KNIGHT: return canKnightAttack(from, to);
      case PAWN: return canPawnAttack(pp.getColor(), from, to);
      case KING: return canKingAttack(from, to);
      default: return false;
    }
  }

  private boolean canRookAttack(Location from, Location to) {
    if (from.getX() != to.getX() && from.getY() != to.getY()) {
      return false;
    }
    return !hasPieceBetween(from, to);
  }

  private boolean canBishopAttack(Location from, Location to) {
    int dx = Math.abs(to.getX() - from.getX());
    int dy = Math.abs(to.getY() - from.getY());
    if (dx != dy || dx == 0) {
      return false;
    }
    return !hasPieceBetween(from, to);
  }

  private boolean canKnightAttack(Location from, Location to) {
    int dx = Math.abs(to.getX() - from.getX());
    int dy = Math.abs(to.getY() - from.getY());
    return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
  }

  private boolean canPawnAttack(PieceColor color, Location from, Location to) {
    int direction = (color == PieceColor.BLACK) ? 1 : -1;
    int dx = to.getX() - from.getX();
    int dy = Math.abs(to.getY() - from.getY());
    return dx == direction && dy == 1;
  }

  private boolean canKingAttack(Location from, Location to) {
    int dx = Math.abs(to.getX() - from.getX());
    int dy = Math.abs(to.getY() - from.getY());
    return dx <= 1 && dy <= 1 && (dx + dy > 0);
  }

  public boolean castle(Location kingFrom, Location kingTo, Location... rookLocations) {
    CastleMove castleMove = new CastleMove(kingFrom, kingTo, rookLocations);

    Piece king = pieces[castleMove.kingFrom.getX()][castleMove.kingFrom.getY()];
    Piece rook = pieces[castleMove.rookFrom.getX()][castleMove.rookFrom.getY()];

    if (king == null || king.hasMoved()) {
      return false;
    }
    if (rook == null || rook.hasMoved()) {
      return false;
    }
    if (hasPieceBetween(castleMove.kingFrom, castleMove.rookFrom)) {
      return false;
    }

    return performCastleIfSafe(castleMove, king, rook);
  }

  private void validateCastleLocations(Location kingFrom, Location kingTo,
      Location[] rookLocations) {
    if (kingFrom == null) {
      throw new IllegalArgumentException("kingFrom must not be null");
    }
    if (kingTo == null) {
      throw new IllegalArgumentException("kingTo must not be null");
    }
    if (rookLocations[0] == null) {
      throw new IllegalArgumentException("rookFrom must not be null");
    }
    if (rookLocations[1] == null) {
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

  private boolean hasPieceBetween(Location from, Location to) {
    int rowStep = Integer.signum(to.getX() - from.getX());
    int colStep = Integer.signum(to.getY() - from.getY());
    int row = from.getX() + rowStep;
    int col = from.getY() + colStep;
    while (row != to.getX() || col != to.getY()) {
      if (pieces[row][col] != null) {
        return true;
      }
      row += rowStep;
      col += colStep;
    }
    return false;
  }

  private final class CastleMove {
    private final Location kingFrom;
    private final Location kingTo;
    private final Location rookFrom;
    private final Location rookTo;

    private CastleMove(Location kingFrom, Location kingTo, Location[] rookLocations) {
      validateRookLocationsCount(rookLocations);
      validateCastleLocations(kingFrom, kingTo, rookLocations);

      this.kingFrom = kingFrom;
      this.kingTo = kingTo;
      this.rookFrom = rookLocations[0];
      this.rookTo = rookLocations[1];
    }

    private void validateRookLocationsCount(Location[] rookLocations) {
      if (rookLocations.length != 2) {
        throw new IllegalArgumentException("rookFrom and rookTo must be provided");
      }
    }
  }
}
