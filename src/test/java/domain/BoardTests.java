package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    @Test
    void constructorCreatesBoard() {
        Board board = new Board();

        assertNotNull(board);
    }
}