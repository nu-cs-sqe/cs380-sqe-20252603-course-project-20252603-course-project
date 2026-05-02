package domain;

import domain.piece.Pawn;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(IllegalArgumentException.class, () -> new Pawn(null));
    }
}
