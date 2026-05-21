package domain;

import static domain.piece.PieceColor.BLACK;
import static domain.piece.PieceColor.WHITE;

import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
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

    private Piece[][] pieces;

    /**
     * Constructs a board and initializes pieces.
     */
    public Board() {
        initializeBoard();
    }

    /**
     * Initializes all chess pieces in starting positions.
     */
    private void initializeBoard() {
        pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];

        // Pawns
        for (int i = 0; i < TOTAL_COLS; i++) {
            pieces[BLACK_PAWN_ROW][i] = new Pawn(BLACK);
            pieces[WHITE_PAWN_ROW][i] = new Pawn(WHITE);
        }

        // Rooks
        pieces[BLACK_BACK_RANK][0] = new Rook(BLACK);
        pieces[BLACK_BACK_RANK][7] = new Rook(BLACK);
        pieces[WHITE_BACK_RANK][0] = new Rook(WHITE);
        pieces[WHITE_BACK_RANK][7] = new Rook(WHITE);

        // Knights
        pieces[BLACK_BACK_RANK][1] = new Knight(BLACK);
        pieces[BLACK_BACK_RANK][6] = new Knight(BLACK);
        pieces[WHITE_BACK_RANK][1] = new Knight(WHITE);
        pieces[WHITE_BACK_RANK][6] = new Knight(WHITE);

        // Bishops
        pieces[BLACK_BACK_RANK][2] = new Bishop(BLACK);
        pieces[BLACK_BACK_RANK][5] = new Bishop(BLACK);
        pieces[WHITE_BACK_RANK][2] = new Bishop(WHITE);
        pieces[WHITE_BACK_RANK][5] = new Bishop(WHITE);

        // Queens and Kings
        pieces[BLACK_BACK_RANK][3] = new Queen(BLACK);
        pieces[BLACK_BACK_RANK][4] = new King(BLACK);
        pieces[WHITE_BACK_RANK][3] = new Queen(WHITE);
        pieces[WHITE_BACK_RANK][4] = new King(WHITE);
    }

    /**
     * Returns a deep-copied snapshot of the board.
     */
    public Piece[][] getSnapshot() {
        Piece[][] snapshot = new Piece[TOTAL_ROWS][TOTAL_COLS];

        for (int i = 0; i < TOTAL_ROWS; i++) {
            for (int j = 0; j < TOTAL_COLS; j++) {
                if (pieces[i][j] != null) {
                    snapshot[i][j] = pieces[i][j].makeCopy();
                }
            }
        }

        return snapshot;
    }
}