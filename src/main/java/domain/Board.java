package domain;

import domain.piece.Piece;

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
}
