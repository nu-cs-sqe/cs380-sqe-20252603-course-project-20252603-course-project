package domain;

import domain.Location;
import domain.piece.Knight;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void IsValidMoveShape_LShapeDx1Dy2_ReturnsTrue() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertTrue(knight.isValidMoveShape(new Location(4, 4), new Location(5, 6)));
    }

    @Test
    public void IsValidMoveShape_LShapeDx1Dy2OppositeHorizontal_ReturnsTrue() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertTrue(knight.isValidMoveShape(new Location(4, 4), new Location(3, 6)));
    }

    @Test
    public void IsValidMoveShape_LShapeDx1Dy2OppositeVertical_ReturnsTrue() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertTrue(knight.isValidMoveShape(new Location(4, 4), new Location(5, 2)));
    }

    @Test
    public void IsValidMoveShape_LShapeDx2Dy1_ReturnsTrue() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertTrue(knight.isValidMoveShape(new Location(4, 4), new Location(6, 5)));
    }

    @Test
    public void IsValidMoveShape_LShapeDx2Dy1OppositeHorizontal_ReturnsTrue() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertTrue(knight.isValidMoveShape(new Location(4, 4), new Location(2, 5)));
    }
}
