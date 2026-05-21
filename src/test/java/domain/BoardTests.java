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
    void majorPiecesInitializedCorrectly() {
        Board board = new Board();
        Piece[][] s = board.getSnapshot();

        // Black back rank
        assertEquals(PieceType.ROOK, s[0][0].getType());
        assertEquals(PieceColor.BLACK, s[0][0].getColor());

        assertEquals(PieceType.KNIGHT, s[0][1].getType());
        assertEquals(PieceColor.BLACK, s[0][1].getColor());

        assertEquals(PieceType.BISHOP, s[0][2].getType());
        assertEquals(PieceColor.BLACK, s[0][2].getColor());

        assertEquals(PieceType.QUEEN, s[0][3].getType());
        assertEquals(PieceColor.BLACK, s[0][3].getColor());

        assertEquals(PieceType.KING, s[0][4].getType());
        assertEquals(PieceColor.BLACK, s[0][4].getColor());

        assertEquals(PieceType.BISHOP, s[0][5].getType());
        assertEquals(PieceColor.BLACK, s[0][5].getColor());

        assertEquals(PieceType.KNIGHT, s[0][6].getType());
        assertEquals(PieceColor.BLACK, s[0][6].getColor());

        assertEquals(PieceType.ROOK, s[0][7].getType());
        assertEquals(PieceColor.BLACK, s[0][7].getColor());

        // White back rank
        assertEquals(PieceType.ROOK, s[7][0].getType());
        assertEquals(PieceColor.WHITE, s[7][0].getColor());

        assertEquals(PieceType.KNIGHT, s[7][1].getType());
        assertEquals(PieceColor.WHITE, s[7][1].getColor());

        assertEquals(PieceType.BISHOP, s[7][2].getType());
        assertEquals(PieceColor.WHITE, s[7][2].getColor());

        assertEquals(PieceType.QUEEN, s[7][3].getType());
        assertEquals(PieceColor.WHITE, s[7][3].getColor());

        assertEquals(PieceType.KING, s[7][4].getType());
        assertEquals(PieceColor.WHITE, s[7][4].getColor());

        assertEquals(PieceType.BISHOP, s[7][5].getType());
        assertEquals(PieceColor.WHITE, s[7][5].getColor());

        assertEquals(PieceType.KNIGHT, s[7][6].getType());
        assertEquals(PieceColor.WHITE, s[7][6].getColor());

        assertEquals(PieceType.ROOK, s[7][7].getType());
        assertEquals(PieceColor.WHITE, s[7][7].getColor());
    }

    @Test
    void middleBoardIsEmpty() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        for (int row = 2; row <= 5; row++) {
            for (int col = 0; col < 8; col++) {
                assertNull(snapshot[row][col]);
            }
        }
    }

    @Test
    void snapshotIsDeepCopiedAndIndependent() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        Piece original = snapshot[0][0];

        snapshot[0][0] = null;

        Piece[][] fresh = board.getSnapshot();

        assertNotNull(fresh[0][0]);
        assertEquals(original.getType(), fresh[0][0].getType());
        assertEquals(original.getColor(), fresh[0][0].getColor());
    }

    @Test
    void modifyingSnapshotDoesNotAffectBoard() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        snapshot[0][0] = null;

        Piece[][] fresh = board.getSnapshot();

        assertNotNull(fresh[0][0]);
    }
}