package domain;

import domain.piece.Bishop;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BishopTests {
    @Test
    public void BishopConstructor_BlackColor_SetsTypeAndColor() {
        Bishop bishop = new Bishop(PieceColor.BLACK);

        assertEquals(PieceType.BISHOP, bishop.getType());
        assertEquals(PieceColor.BLACK, bishop.getColor());
    }

    @Test
    public void BishopConstructor_WhiteColor_SetsTypeAndColor() {
        Bishop bishop = new Bishop(PieceColor.WHITE);

        assertEquals(PieceType.BISHOP, bishop.getType());
        assertEquals(PieceColor.WHITE, bishop.getColor());
    }

    @Test
    public void BishopConstructor_NullColor_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Bishop(null));
    }

    @Test
    public void BishopMakeCopy_BlackBishop_ReturnsDistinctBishopWithSameTypeAndColor() {
        Bishop bishop = new Bishop(PieceColor.BLACK);

        Piece copy = bishop.makeCopy();

        assertNotSame(bishop, copy);
        assertInstanceOf(Bishop.class, copy);
        assertEquals(PieceType.BISHOP, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }

    @Test
    public void BishopMakeCopy_WhiteBishop_ReturnsDistinctBishopWithSameTypeAndColor() {
        Bishop bishop = new Bishop(PieceColor.WHITE);

        Piece copy = bishop.makeCopy();

        assertNotSame(bishop, copy);
        assertInstanceOf(Bishop.class, copy);
        assertEquals(PieceType.BISHOP, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }

    @Test
    public void BishopMakeCopy_NullColorBishop_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Bishop(null));
    }
}
