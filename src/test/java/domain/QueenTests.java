package domain;

import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Queen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueenTests {
    @Test
    public void QueenConstructor_BlackColor_SetsTypeAndColor() {
        Queen queen = new Queen(PieceColor.BLACK);

        assertEquals(PieceType.QUEEN, queen.getType());
        assertEquals(PieceColor.BLACK, queen.getColor());
    }
}
