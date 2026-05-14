package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

public class Board {

    Piece[][] pieces;
    GameState currentGameState;
    Location whiteKingLocation;
    Location blackKingLocation;

    public Board() {
        pieces = new Piece[8][8];
        currentGameState = GameState.WHITE_TURN;
    }

    public Piece[][] getSnapshot() {
        return null;
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
        if (location == null) {
            throw new IllegalArgumentException("location must not be null");
        }
        whiteKingLocation = location;
    }

    public void updateBlackKingLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("location must not be null");
        }
        blackKingLocation = location;
    }

    public boolean checkNotMoveIntoCheck(Location from, Location to) {
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
        if (movingPiece != null && movingPiece.getType() == PieceType.KING) {
            if (movingPiece.getColor() == PieceColor.WHITE) {
                whiteKingLocation = to;
            } else {
                blackKingLocation = to;
            }
        }

        PieceColor currentColor = (currentGameState == GameState.WHITE_TURN)
                ? PieceColor.WHITE : PieceColor.BLACK;
        Location kingLoc = (currentColor == PieceColor.WHITE)
                ? whiteKingLocation : blackKingLocation;
        boolean inCheck = isKingInCheck(currentColor, kingLoc);

        if (inCheck) {
            pieces[from.getX()][from.getY()] = movingPiece;
            pieces[to.getX()][to.getY()] = capturedPiece;
            whiteKingLocation = prevWhiteKingLoc;
            blackKingLocation = prevBlackKingLoc;
            return false;
        }
        return true;
    }

    private boolean isKingInCheck(PieceColor kingColor, Location kingLoc) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = pieces[row][col];
                if (p != null && p.getColor() != kingColor) {
                    if (canAttack(p, new Location(row, col), kingLoc)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canAttack(Piece p, Location from, Location to) {
        switch (p.getType()) {
            case ROOK: return canRookAttack(from, to);
            case BISHOP: return canBishopAttack(from, to);
            case QUEEN: return canRookAttack(from, to) || canBishopAttack(from, to);
            case KNIGHT: return canKnightAttack(from, to);
            case PAWN: return canPawnAttack(p.getColor(), from, to);
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
}
