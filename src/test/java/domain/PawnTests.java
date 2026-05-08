package domain;

import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PawnTests {
    @Test
    public void PawnConstructor_ColorBlack_CreatesBlackPawnWithPawnType() {
        Pawn pawn = new Pawn(PieceColor.BLACK);

        assertEquals(PieceType.PAWN, pawn.getType());
        assertEquals(PieceColor.BLACK, pawn.getColor());
    }

    @Test
    public void PawnConstructor_ColorWhite_CreatesWhitePawnWithPawnType() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        assertEquals(PieceType.PAWN, pawn.getType());
        assertEquals(PieceColor.WHITE, pawn.getColor());
    }

    @Test
    public void PawnConstructor_ColorNull_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Pawn(null));

        assertEquals("color must not be null", exception.getMessage());
    }

    @Test
    public void PawnConstructorAndIsValidMoveShape_ColorWhiteAtStartingRow_AllowsInitialTwoSquareMove() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        assertEquals(PieceType.PAWN, pawn.getType());
        assertEquals(PieceColor.WHITE, pawn.getColor());
        assertTrue(pawn.isValidMoveShape(new Location(4, 6), new Location(4, 4)));
    }

    @Test
    public void PawnIsValidMoveShape_WhitePawnOneSquareForward_ReturnsTrue() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        assertTrue(pawn.isValidMoveShape(new Location(4, 6), new Location(4, 5)));
    }

    @Test
    public void PawnIsValidMoveShape_BlackPawnOneSquareForward_ReturnsTrue() {
        Pawn pawn = new Pawn(PieceColor.BLACK);

        assertTrue(pawn.isValidMoveShape(new Location(4, 1), new Location(4, 2)));
    }

    @Test
    public void PawnConstructorAndIsValidMoveShape_ColorBlackAtStartingRow_AllowsInitialTwoSquareMove() {
        Pawn pawn = new Pawn(PieceColor.BLACK);

        assertEquals(PieceType.PAWN, pawn.getType());
        assertEquals(PieceColor.BLACK, pawn.getColor());
        assertTrue(pawn.isValidMoveShape(new Location(4, 1), new Location(4, 3)));
    }

    @Test
    public void PawnMakeCopy_BlackPawn_ReturnsDistinctBlackPawnWithPawnType() {
        Pawn pawn = new Pawn(PieceColor.BLACK);

        Piece copy = pawn.makeCopy();

        assertNotSame(pawn, copy);
        assertInstanceOf(Pawn.class, copy);
        assertEquals(PieceType.PAWN, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }

    @Test
    public void PawnMakeCopy_WhitePawn_ReturnsDistinctWhitePawnWithPawnType() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        Piece copy = pawn.makeCopy();

        assertNotSame(pawn, copy);
        assertInstanceOf(Pawn.class, copy);
        assertEquals(PieceType.PAWN, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }

    @Test
    public void PawnMakeCopy_NullColorPawn_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Pawn(null));

        assertEquals("color must not be null", exception.getMessage());
    }

    @Test
    public void PawnIsValidMoveShape_WhitePawnOffStartingRow_DoesNotAllowTwoSquaresForward() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        assertEquals(false, pawn.isValidMoveShape(new Location(4, 5), new Location(4, 3)));
    }

    @Test
    public void PawnIsValidMoveShape_BlackPawnOffStartingRow_DoesNotAllowTwoSquaresForward() {
        Pawn pawn = new Pawn(PieceColor.BLACK);

        assertEquals(false, pawn.isValidMoveShape(new Location(4, 2), new Location(4, 4)));
    }

    @Test
    public void PawnIsValidMoveShape_WhitePawnDiagonalForwardLeft_ReturnsTrue() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        assertTrue(pawn.isValidMoveShape(new Location(4, 6), new Location(3, 5)));
    }

    @Test
    public void PawnIsValidMoveShape_WhitePawnDiagonalForwardRight_ReturnsTrue() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        assertTrue(pawn.isValidMoveShape(new Location(4, 6), new Location(5, 5)));
    }
}
