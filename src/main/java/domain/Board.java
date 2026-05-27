package domain;

import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Queen;
import domain.piece.Rook;

/**
 * Represents an 8x8 chess board and initializes the starting position.
 */
public class Board {

  private static final int TOTAL_ROWS = 8;
  private static final int TOTAL_COLS = 8;

  private static final int BLACK_BACK_RANK = 0;
  private static final int BLACK_PAWN_ROW = 1;
  private static final int WHITE_PAWN_ROW = 6;
  private static final int WHITE_BACK_RANK = 7;

  private static final int ROOK_LEFT_COL = 0;
  private static final int KNIGHT_LEFT_COL = 1;
  private static final int BISHOP_LEFT_COL = 2;
  private static final int QUEEN_COL = 3;
  private static final int KING_COL = 4;
  private static final int BISHOP_RIGHT_COL = 5;
  private static final int KNIGHT_RIGHT_COL = 6;
  private static final int ROOK_RIGHT_COL = 7;

  private Piece[][] pieces;
  private GameState currentGameState;
  private Location whiteKingLocation;
  private Location blackKingLocation;

  public Board() {
    initializeBoard();
    currentGameState = GameState.WHITE_TURN;
  }

  private void initializeBoard() {
    pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];

    initializePawns();
    initializeRooks();
    initializeKnights();
    initializeBishops();
    initializeRoyalPieces();
  }

  private void initializePawns() {
    for (int col = 0; col < TOTAL_COLS; col++) {
      pieces[BLACK_PAWN_ROW][col] = new Pawn(PieceColor.BLACK);
      pieces[WHITE_PAWN_ROW][col] = new Pawn(PieceColor.WHITE);
    }
  }

  private void initializeRooks() {
    pieces[BLACK_BACK_RANK][ROOK_LEFT_COL] = new Rook(PieceColor.BLACK);
    pieces[BLACK_BACK_RANK][ROOK_RIGHT_COL] = new Rook(PieceColor.BLACK);

    pieces[WHITE_BACK_RANK][ROOK_LEFT_COL] = new Rook(PieceColor.WHITE);
    pieces[WHITE_BACK_RANK][ROOK_RIGHT_COL] = new Rook(PieceColor.WHITE);
  }

  private void initializeKnights() {
    pieces[BLACK_BACK_RANK][KNIGHT_LEFT_COL] = new Knight(PieceColor.BLACK);
    pieces[BLACK_BACK_RANK][KNIGHT_RIGHT_COL] = new Knight(PieceColor.BLACK);

    pieces[WHITE_BACK_RANK][KNIGHT_LEFT_COL] = new Knight(PieceColor.WHITE);
    pieces[WHITE_BACK_RANK][KNIGHT_RIGHT_COL] = new Knight(PieceColor.WHITE);
  }

  private void initializeBishops() {
    pieces[BLACK_BACK_RANK][BISHOP_LEFT_COL] = new Bishop(PieceColor.BLACK);
    pieces[BLACK_BACK_RANK][BISHOP_RIGHT_COL] = new Bishop(PieceColor.BLACK);

    pieces[WHITE_BACK_RANK][BISHOP_LEFT_COL] = new Bishop(PieceColor.WHITE);
    pieces[WHITE_BACK_RANK][BISHOP_RIGHT_COL] = new Bishop(PieceColor.WHITE);
  }

  private void initializeRoyalPieces() {
    pieces[BLACK_BACK_RANK][QUEEN_COL] = new Queen(PieceColor.BLACK);
    pieces[BLACK_BACK_RANK][KING_COL] = new King(PieceColor.BLACK);

    pieces[WHITE_BACK_RANK][QUEEN_COL] = new Queen(PieceColor.WHITE);
    pieces[WHITE_BACK_RANK][KING_COL] = new King(PieceColor.WHITE);
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

  /**
   * Returns a deep-copied snapshot of the board.
   */
  public Piece[][] getSnapshot() {
    Piece[][] snapshot = new Piece[TOTAL_ROWS][TOTAL_COLS];

    for (int row = 0; row < TOTAL_ROWS; row++) {
      for (int col = 0; col < TOTAL_COLS; col++) {
        if (pieces[row][col] != null) {
          snapshot[row][col] = pieces[row][col].makeCopy();
        }
      }
    }

    return snapshot;
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

  /**
   * Moves a piece from one location on the board to a new one.
   */
  public boolean movePiece(Location from, Location to) {
    requireLocations(from, to);
    if (isOutOfBounds(from) || isOutOfBounds(to)) {
      return false;
    }

    Piece piece = pieces[from.getY()][from.getX()];
    if (piece == null) {
      return false;
    }

    Piece destinationPiece = pieces[to.getY()][to.getX()];
    if (isFriendlyOccupied(piece, destinationPiece)) {
      return false;
    }

    if (!isLegalMoveForPiece(piece, from, to, destinationPiece)) {
      return false;
    }

    placePiece(piece, from, to);
    return true;
  }

  private void requireLocations(Location from, Location to) {
    if (from == null) {
      throw new IllegalArgumentException("from must not be null");
    }
    if (to == null) {
      throw new IllegalArgumentException("to must not be null");
    }
  }

  private boolean isOutOfBounds(Location location) {
    return location.getX() < 0 || location.getX() >= TOTAL_COLS
        || location.getY() < 0 || location.getY() >= TOTAL_ROWS;
  }

  private boolean isFriendlyOccupied(Piece moving, Piece destination) {
    return destination != null && destination.getColor() == moving.getColor();
  }

  private boolean isLegalMoveForPiece(Piece piece, Location from, Location to, Piece destinationPiece) {
    switch (piece.getType()) {
      case PAWN:
        return isLegalPawnMove(piece, from, to, destinationPiece);
      case ROOK:
      case BISHOP:
      case QUEEN:
        return piece.isValidMoveShape(from, to) && isPathClear(from, to);
      case KNIGHT:
      case KING:
        return piece.isValidMoveShape(from, to);
      default:
        throw new IllegalStateException("Unknown piece type: " + piece.getType());
    }
  }

  private boolean isLegalPawnMove(Piece piece, Location from, Location to, Piece destinationPiece) {
    if (!piece.isValidMoveShape(from, to)) {
      return false;
    }
    int dx = to.getX() - from.getX();
    int dy = to.getY() - from.getY();
    if (dx == 0) {
      return destinationPiece == null && (Math.abs(dy) != 2 || isPathClear(from, to));
    }
    return destinationPiece != null;
  }

  private void placePiece(Piece piece, Location from, Location to) {
    pieces[to.getY()][to.getX()] = piece;
    pieces[from.getY()][from.getX()] = null;
  }

  private boolean isPathClear(Location from, Location to) {
    return !Piece.hasPieceBetween(from, to, pieces);
  }

  public boolean applyMoveIfKingSafe(Location from, Location to) {
    requireLocations(from, to);

    Piece movingPiece = pieces[from.getY()][from.getX()];
    Piece capturedPiece = pieces[to.getY()][to.getX()];

    pieces[to.getY()][to.getX()] = movingPiece;
    pieces[from.getY()][from.getX()] = null;

    Location prevWhiteKingLoc = whiteKingLocation;
    Location prevBlackKingLoc = blackKingLocation;
    updateKingLocationForMove(movingPiece, to);

    boolean inCheck = isCurrentKingInCheck();

    if (inCheck) {
      pieces[from.getY()][from.getX()] = movingPiece;
      pieces[to.getY()][to.getX()] = capturedPiece;
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
    for (int row = 0; row < TOTAL_ROWS; row++) {
      for (int col = 0; col < TOTAL_COLS; col++) {
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

    Piece king = pieces[castleMove.kingFrom.getY()][castleMove.kingFrom.getX()];
    Piece rook = pieces[castleMove.rookFrom.getY()][castleMove.rookFrom.getX()];

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

    int colStep = Integer.signum(castleMove.kingTo.getX() - castleMove.kingFrom.getX());
    Location transitSquare = new Location(
        castleMove.kingFrom.getX() + colStep,
        castleMove.kingFrom.getY());
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
    pieces[castleMove.kingTo.getY()][castleMove.kingTo.getX()] = king;
    pieces[castleMove.kingFrom.getY()][castleMove.kingFrom.getX()] = null;
    pieces[castleMove.rookTo.getY()][castleMove.rookTo.getX()] = rook;
    pieces[castleMove.rookFrom.getY()][castleMove.rookFrom.getX()] = null;
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
