package domain;

import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KingTests {
    @Test
    public void KingConstructor_ColorBlack_CreatesBlackKingWithKingType() {
        King king = new King(PieceColor.BLACK);

        assertEquals(PieceType.KING, king.getType());
        assertEquals(PieceColor.BLACK, king.getColor());
    }

    @Test
    public void KingConstructor_ColorWhite_CreatesWhiteKingWithKingType() {
        King king = new King(PieceColor.WHITE);

        assertEquals(PieceType.KING, king.getType());
        assertEquals(PieceColor.WHITE, king.getColor());
    }

    @Test
    public void KingConstructor_ColorNull_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new King(null));

        assertEquals("color must not be null", exception.getMessage());
    }

    @Test
    public void MakeCopy_BlackKing_ReturnsDistinctBlackKingCopy() {
        King king = new King(PieceColor.BLACK);

        Piece copy = king.makeCopy();

        assertNotSame(king, copy);
        assertInstanceOf(King.class, copy);
        assertEquals(PieceType.KING, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }

    @Test
    public void MakeCopy_WhiteKing_ReturnsDistinctWhiteKingCopy() {
        King king = new King(PieceColor.WHITE);

        Piece copy = king.makeCopy();

        assertNotSame(king, copy);
        assertInstanceOf(King.class, copy);
        assertEquals(PieceType.KING, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }

    @Test
    public void MakeCopy_NullColorKing_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new King(null));

        assertEquals("color must not be null", exception.getMessage());
    }
}
