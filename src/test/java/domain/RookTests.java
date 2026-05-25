package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RookTests {
    @Test
    public void RookConstructor_BlackColor_SetsTypeAndColor() {
        Rook rook = new Rook(PieceColor.BLACK);

        assertEquals(PieceType.ROOK, rook.getType());
        assertEquals(PieceColor.BLACK, rook.getColor());
    }

    @Test
    public void RookConstructor_WhiteColor_SetsTypeAndColor() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertEquals(PieceType.ROOK, rook.getType());
        assertEquals(PieceColor.WHITE, rook.getColor());
    }

    @Test
    public void RookConstructor_NullColor_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Rook(null));

        assertEquals("color must not be null", exception.getMessage());
    }

    @Test
    public void RookMakeCopy_BlackRook_ReturnsDistinctRookWithSameColor() {
        Rook rook = new Rook(PieceColor.BLACK);

        Piece copy = rook.makeCopy();

        assertNotSame(rook, copy);
        assertInstanceOf(Rook.class, copy);
        assertEquals(PieceType.ROOK, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }

    @Test
    public void RookMakeCopy_WhiteRook_ReturnsDistinctRookWithSameColor() {
        Rook rook = new Rook(PieceColor.WHITE);

        Piece copy = rook.makeCopy();

        assertNotSame(rook, copy);
        assertInstanceOf(Rook.class, copy);
        assertEquals(PieceType.ROOK, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }

    @Test
    public void RookIsValidMoveShape_StraightHorizontalRight_ReturnsTrue() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertTrue(rook.isValidMoveShape(new Location(0, 4), new Location(7, 4)));
    }

    @Test
    public void RookIsValidMoveShape_StraightHorizontalLeft_ReturnsTrue() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertTrue(rook.isValidMoveShape(new Location(7, 4), new Location(0, 4)));
    }

    @Test
    public void RookIsValidMoveShape_StraightVerticalUp_ReturnsTrue() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertTrue(rook.isValidMoveShape(new Location(4, 0), new Location(4, 7)));
    }

    @Test
    public void RookIsValidMoveShape_StraightVerticalDown_ReturnsTrue() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertTrue(rook.isValidMoveShape(new Location(4, 7), new Location(4, 0)));
    }

    @Test
    public void RookIsValidMoveShape_OneSquareHorizontal_ReturnsTrue() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertTrue(rook.isValidMoveShape(new Location(4, 4), new Location(5, 4)));
    }

    @Test
    public void RookIsValidMoveShape_OneSquareVertical_ReturnsTrue() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertTrue(rook.isValidMoveShape(new Location(4, 4), new Location(4, 5)));
    }

    @Test
    public void RookIsValidMoveShape_DiagonalMove_ReturnsFalse() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertFalse(rook.isValidMoveShape(new Location(0, 0), new Location(3, 3)));
    }

    @Test
    public void RookIsValidMoveShape_SameSquare_ReturnsFalse() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertFalse(rook.isValidMoveShape(new Location(4, 4), new Location(4, 4)));
    }

    @Test
    public void RookIsValidMoveShape_LShapeMove_ReturnsFalse() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertFalse(rook.isValidMoveShape(new Location(4, 4), new Location(5, 6)));
    }

    @Test
    public void RookIsValidMoveShape_DestinationXAboveMaximum_ReturnsFalse() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertFalse(rook.isValidMoveShape(new Location(7, 4), new Location(8, 4)));
    }

    @Test
    public void RookIsValidMoveShape_DestinationYAboveMaximum_ReturnsFalse() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertFalse(rook.isValidMoveShape(new Location(4, 7), new Location(4, 8)));
    }

    @Test
    public void RookIsValidMoveShape_DestinationXBelowMinimum_ReturnsFalse() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertFalse(rook.isValidMoveShape(new Location(0, 4), new Location(-1, 4)));
    }

    @Test
    public void RookIsValidMoveShape_DestinationYBelowMinimum_ReturnsFalse() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertFalse(rook.isValidMoveShape(new Location(4, 0), new Location(4, -1)));
    }

    @Test
    public void RookIsValidMoveShape_FromMinimumCornerHorizontal_ReturnsTrue() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertTrue(rook.isValidMoveShape(new Location(0, 0), new Location(7, 0)));
    }

    @Test
    public void RookIsValidMoveShape_FromMinimumCornerVertical_ReturnsTrue() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertTrue(rook.isValidMoveShape(new Location(0, 0), new Location(0, 7)));
    }

    @Test
    public void RookIsValidMoveShape_NullFrom_ThrowsIllegalArgumentException() {
        Rook rook = new Rook(PieceColor.WHITE);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> rook.isValidMoveShape(null, new Location(4, 4))
        );

        assertEquals("from must not be null", exception.getMessage());
    }

    @Test
    public void RookIsValidMoveShape_NullTo_ThrowsIllegalArgumentException() {
        Rook rook = new Rook(PieceColor.WHITE);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> rook.isValidMoveShape(new Location(4, 4), null)
        );

        assertEquals("to must not be null", exception.getMessage());
    }

    @Test
    public void RookCanJump_ReturnsFalse() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertFalse(rook.canJump());
    }
}
