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
}