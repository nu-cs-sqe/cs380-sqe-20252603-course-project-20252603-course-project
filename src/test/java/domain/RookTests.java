package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    public void RookConstructor_NullColor_SetsTypeAndNullColor() {
        Rook rook = new Rook(null);

        assertEquals(PieceType.ROOK, rook.getType());
        assertNull(rook.getColor());
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
    public void RookMakeCopy_NullColorRook_ReturnsDistinctRookWithNullColor() {
        Rook rook = new Rook(null);

        Piece copy = rook.makeCopy();

        assertNotSame(rook, copy);
        assertInstanceOf(Rook.class, copy);
        assertEquals(PieceType.ROOK, copy.getType());
        assertNull(copy.getColor());
    }
}
