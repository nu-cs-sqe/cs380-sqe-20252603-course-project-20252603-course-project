package domain;

import domain.piece.Piece;
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
}