package domain;

import domain.piece.Bishop;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BishopTests {
    @Test
    public void BishopConstructor_BlackColor_SetsTypeAndColor() {
        Bishop bishop = new Bishop(PieceColor.BLACK);

        assertEquals(PieceType.BISHOP, bishop.getType());
        assertEquals(PieceColor.BLACK, bishop.getColor());
    }
}
