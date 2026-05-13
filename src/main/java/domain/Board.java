package domain;

import domain.piece.*;

import static domain.piece.PieceColor.BLACK;
import static domain.piece.PieceColor.WHITE;

public class Board {

    private final int TOTAL_ROWS = 8;
    private final int TOTAL_COLS = 8;

    private Piece[][] pieces;

    public Board() {
        initializeBoard();
    }

    private void initializeBoard() {
        pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];

        for (int i = 0; i < TOTAL_COLS; i++) {
            pieces[1][i] = new Pawn(BLACK);
            pieces[6][i] = new Pawn(WHITE);
        }

        pieces[0][0] = new Rook(BLACK);
        pieces[0][7] = new Rook(BLACK);
    }

    public Piece[][] getSnapshot() {
        Piece[][] snapshot = new Piece[TOTAL_ROWS][TOTAL_COLS];

        for (int i = 0; i < TOTAL_ROWS; i++) {
            for (int j = 0; j < TOTAL_COLS; j++) {
                snapshot[i][j] = pieces[i][j];
            }
        }

        return snapshot;
    }
}