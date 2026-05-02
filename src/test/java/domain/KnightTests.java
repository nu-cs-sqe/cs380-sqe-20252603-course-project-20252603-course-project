package domain;

import domain.piece.Knight;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KnightTests {
    @Test
    public void KnightConstructor_BlackColor_SetsTypeAndColor() {
        Knight knight = new Knight(PieceColor.BLACK);

        assertEquals(PieceType.KNIGHT, knight.getType());
        assertEquals(PieceColor.BLACK, knight.getColor());
    }

    @Test
    public void KnightConstructor_WhiteColor_SetsTypeAndColor() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertEquals(PieceType.KNIGHT, knight.getType());
        assertEquals(PieceColor.WHITE, knight.getColor());
    }

    @Test
    public void KnightConstructor_NullColor_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Knight(null));

        assertEquals("color must not be null", exception.getMessage());
    }

    @Test
    public void KnightMakeCopy_BlackKnight_ReturnsDistinctKnightWithSameTypeAndColor() {
        Knight knight = new Knight(PieceColor.BLACK);

        Piece copy = knight.makeCopy();

        assertNotSame(knight, copy);
        assertInstanceOf(Knight.class, copy);
        assertEquals(PieceType.KNIGHT, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }

    @Test
    public void KnightMakeCopy_WhiteKnight_ReturnsDistinctKnightWithSameTypeAndColor() {
        Knight knight = new Knight(PieceColor.WHITE);

        Piece copy = knight.makeCopy();

        assertNotSame(knight, copy);
        assertInstanceOf(Knight.class, copy);
        assertEquals(PieceType.KNIGHT, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }

    @Test
    public void KnightMakeCopy_NullColorKnight_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Knight(null));

        assertEquals("color must not be null", exception.getMessage());
    }
}
