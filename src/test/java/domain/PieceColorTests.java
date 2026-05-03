package domain;

import domain.piece.PieceColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PieceColorTests {
    @Test
    public void PieceColorBlack_ConstantAccess_ReturnsBlackSingletonWithName() {
        PieceColor color = PieceColor.BLACK;

        assertEquals(PieceColor.BLACK, color);
        assertEquals("BLACK", color.name());
    }

    @Test
    public void PieceColorWhite_ConstantAccess_ReturnsWhiteSingletonWithName() {
        PieceColor color = PieceColor.WHITE;

        assertEquals(PieceColor.WHITE, color);
        assertEquals("WHITE", color.name());
    }

    @Test
    public void PieceColorValues_NoInput_ReturnsBlackThenWhite() {
        PieceColor[] colors = PieceColor.values();

        assertEquals(2, colors.length);
        assertEquals(PieceColor.BLACK, colors[0]);
        assertEquals(PieceColor.WHITE, colors[1]);
    }

    @Test
    public void PieceColorValues_CalledTwice_ReturnsDistinctArraysWithBlackThenWhite() {
        PieceColor[] first = PieceColor.values();
        PieceColor[] second = PieceColor.values();

        assertNotSame(first, second);
        assertEquals(2, first.length);
        assertEquals(PieceColor.BLACK, first[0]);
        assertEquals(PieceColor.WHITE, first[1]);
        assertEquals(2, second.length);
        assertEquals(PieceColor.BLACK, second[0]);
        assertEquals(PieceColor.WHITE, second[1]);
    }

    @Test
    public void PieceColorValueOf_BlackName_ReturnsBlack() {
        PieceColor color = PieceColor.valueOf("BLACK");

        assertEquals(PieceColor.BLACK, color);
    }

    @Test
    public void PieceColorValueOf_WhiteName_ReturnsWhite() {
        PieceColor color = PieceColor.valueOf("WHITE");

        assertEquals(PieceColor.WHITE, color);
    }

    @Test
    public void PieceColorValueOf_NullName_ThrowsNullPointerExceptionWithNameIsNullMessage() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> PieceColor.valueOf(null));

        assertEquals("Name is null", exception.getMessage());
    }

    @Test
    public void PieceColorValueOf_LowercaseBlackName_ThrowsIllegalArgumentExceptionWithEnumConstantMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> PieceColor.valueOf("black"));

        assertEquals("No enum constant domain.piece.PieceColor.black", exception.getMessage());
    }

    @Test
    public void PieceColorValueOf_BlackNameWithWhitespace_ThrowsIllegalArgumentExceptionWithEnumConstantMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> PieceColor.valueOf(" BLACK "));

        assertEquals("No enum constant domain.piece.PieceColor. BLACK ", exception.getMessage());
    }

    @Test
    public void PieceColorValueOf_RedName_ThrowsIllegalArgumentExceptionWithEnumConstantMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> PieceColor.valueOf("RED"));

        assertEquals("No enum constant domain.piece.PieceColor.RED", exception.getMessage());
    }
}
