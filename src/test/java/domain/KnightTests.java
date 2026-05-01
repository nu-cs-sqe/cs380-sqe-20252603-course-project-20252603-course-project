package domain;

import domain.piece.Knight;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KnightTests {
    @Test
    public void KnightConstructor_BlackColor_SetsTypeAndColor() {
        Knight knight = new Knight(PieceColor.BLACK);

        assertEquals(PieceType.KNIGHT, knight.getType());
        assertEquals(PieceColor.BLACK, knight.getColor());
    }
}
