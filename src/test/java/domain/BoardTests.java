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

    @Test
    void middleRowsAreEmpty() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        for (int row = 2; row <= 5; row++) {
            for (int col = 0; col < 8; col++) {
                assertNull(snapshot[row][col]);
            }
        }
    }

    // now testing snapshot vs initialization
    @Test
    void snapshotContainsCopiedPieces() {
        Board board = new Board();

        Piece[][] snapshot1 = board.getSnapshot();
        Piece[][] snapshot2 = board.getSnapshot();

        assertNotSame(snapshot1[0][0], snapshot2[0][0]);
    }

    @Test
    void snapshotDimensionsAre8x8() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();

        assertEquals(8, snapshot.length);
        assertEquals(8, snapshot[0].length);
    }

    @Test
    void rookAt00IsBlack() {
        Board board = new Board();

        Piece[][] s = board.getSnapshot();

        assertNotNull(s[0][0]);
        assertEquals(PieceType.ROOK, s[0][0].getType());
        assertEquals(PieceColor.BLACK, s[0][0].getColor());
    }

    @Test
    void kingAt74IsWhite() {
        Board board = new Board();

        Piece[][] s = board.getSnapshot();

        assertNotNull(s[7][4]);
        assertEquals(PieceType.KING, s[7][4].getType());
        assertEquals(PieceColor.WHITE, s[7][4].getColor());
    }

    @Test
    void middleSquareIsEmpty() {
        Board board = new Board();

        Piece[][] s = board.getSnapshot();

        assertNull(s[3][3]);
    }

    @Test
    void snapshotPiecesAreCopiedNotSameReference() {
        Board board = new Board();

        Piece[][] s = board.getSnapshot();

        Piece original = board.getSnapshot()[0][0];
        Piece copy = s[0][0];

        assertNotSame(original, copy);
    }

    @Test
    void knightCopyPreservesTypeAndColor() {
        Board board = new Board();

        Piece[][] s = board.getSnapshot();

        Piece knight = s[0][1];

        assertEquals(PieceType.KNIGHT, knight.getType());
        assertEquals(PieceColor.BLACK, knight.getColor());
    }

    @Test
    void modifyingSnapshotDoesNotChangeBoard() {
        Board board = new Board();

        Piece[][] s = board.getSnapshot();

        s[0][0] = null; // attempt to modify snapshot

        Piece[][] s2 = board.getSnapshot();

        assertNotNull(s2[0][0]); // board must still be intact
    }
}