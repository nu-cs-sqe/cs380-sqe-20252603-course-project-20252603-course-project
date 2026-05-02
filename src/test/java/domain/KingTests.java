package domain;

import domain.piece.King;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertThrows(IllegalArgumentException.class, () -> new King(null));
    }
}
