package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    @Test
    void constructorCreatesBoard() {
        Board board = new Board();

        assertNotNull(board);
    }

    @Test
    void snapshotHasCorrectDimensions() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        assertEquals(8, snapshot.length);
        assertEquals(8, snapshot[0].length);
    }

    @Test
    void blackPawnsInitializedCorrectly() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        for (int i = 0; i < 8; i++) {
            assertNotNull(snapshot[1][i]);
            assertEquals(PieceType.PAWN, snapshot[1][i].getType());
            assertEquals(PieceColor.BLACK, snapshot[1][i].getColor());
        }
    }

    @Test
    void whitePawnsInitializedCorrectly() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        for (int i = 0; i < 8; i++) {
            assertNotNull(snapshot[6][i]);
            assertEquals(PieceType.PAWN, snapshot[6][i].getType());
            assertEquals(PieceColor.WHITE, snapshot[6][i].getColor());
        }
    }

    @Test
    void blackRooksInitializedCorrectly() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        assertEquals(PieceType.ROOK, snapshot[0][0].getType());
        assertEquals(PieceColor.BLACK, snapshot[0][0].getColor());

        assertEquals(PieceType.ROOK, snapshot[0][7].getType());
        assertEquals(PieceColor.BLACK, snapshot[0][7].getColor());
    }

    @Test
    void whiteRooksInitializedCorrectly() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        assertEquals(PieceType.ROOK, snapshot[7][0].getType());
        assertEquals(PieceColor.WHITE, snapshot[7][0].getColor());

        assertEquals(PieceType.ROOK, snapshot[7][7].getType());
        assertEquals(PieceColor.WHITE, snapshot[7][7].getColor());
    }

    @Test
    void knightsInitializedCorrectly() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        assertEquals(PieceType.KNIGHT, snapshot[0][1].getType());
        assertEquals(PieceColor.BLACK, snapshot[0][1].getColor());

        assertEquals(PieceType.KNIGHT, snapshot[0][6].getType());
        assertEquals(PieceColor.BLACK, snapshot[0][6].getColor());

        assertEquals(PieceType.KNIGHT, snapshot[7][1].getType());
        assertEquals(PieceColor.WHITE, snapshot[7][1].getColor());

        assertEquals(PieceType.KNIGHT, snapshot[7][6].getType());
        assertEquals(PieceColor.WHITE, snapshot[7][6].getColor());
    }

    @Test
    void bishopsInitializedCorrectly() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        assertEquals(PieceType.BISHOP, snapshot[0][2].getType());
        assertEquals(PieceColor.BLACK, snapshot[0][2].getColor());

        assertEquals(PieceType.BISHOP, snapshot[0][5].getType());
        assertEquals(PieceColor.BLACK, snapshot[0][5].getColor());

        assertEquals(PieceType.BISHOP, snapshot[7][2].getType());
        assertEquals(PieceColor.WHITE, snapshot[7][2].getColor());

        assertEquals(PieceType.BISHOP, snapshot[7][5].getType());
        assertEquals(PieceColor.WHITE, snapshot[7][5].getColor());
    }

    @Test
    void queensAndKingsInitializedCorrectly() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        assertEquals(PieceType.QUEEN, snapshot[0][3].getType());
        assertEquals(PieceColor.BLACK, snapshot[0][3].getColor());

        assertEquals(PieceType.KING, snapshot[0][4].getType());
        assertEquals(PieceColor.BLACK, snapshot[0][4].getColor());

        assertEquals(PieceType.QUEEN, snapshot[7][3].getType());
        assertEquals(PieceColor.WHITE, snapshot[7][3].getColor());

        assertEquals(PieceType.KING, snapshot[7][4].getType());
        assertEquals(PieceColor.WHITE, snapshot[7][4].getColor());
    }
}