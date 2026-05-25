package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.piece.PieceColor;
import org.junit.jupiter.api.Test;

public class PieceColorTests {

  @Test
  public void pieceColorBlackConstantAccessReturnsBlackSingletonWithName() {
    PieceColor color = PieceColor.BLACK;

    assertEquals(PieceColor.BLACK, color);
    assertEquals("BLACK", color.name());
  }

  @Test
  public void pieceColorWhiteConstantAccessReturnsWhiteSingletonWithName() {
    PieceColor color = PieceColor.WHITE;

    assertEquals(PieceColor.WHITE, color);
    assertEquals("WHITE", color.name());
  }

  @Test
  public void pieceColorValuesNoInputReturnsBlackThenWhite() {
    PieceColor[] colors = PieceColor.values();

    assertEquals(2, colors.length);
    assertEquals(PieceColor.BLACK, colors[0]);
    assertEquals(PieceColor.WHITE, colors[1]);
  }

  @Test
  public void pieceColorValuesCalledTwiceReturnsDistinctArraysWithBlackThenWhite() {
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
  public void pieceColorValueOfBlackNameReturnsBlack() {
    PieceColor color = PieceColor.valueOf("BLACK");

    assertEquals(PieceColor.BLACK, color);
  }

  @Test
  public void pieceColorValueOfWhiteNameReturnsWhite() {
    PieceColor color = PieceColor.valueOf("WHITE");

    assertEquals(PieceColor.WHITE, color);
  }

  @Test
  public void pieceColorValueOfNullNameThrowsNullPointerExceptionWithNameIsNullMessage() {
    NullPointerException exception = assertThrows(NullPointerException.class,
        () -> PieceColor.valueOf(null));

    assertEquals("Name is null", exception.getMessage());
  }

  @Test
  public void pieceColorValueOfLowercaseBlackNameThrowsIllegalArgumentExceptionWithEnumConstantMessage() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> PieceColor.valueOf("black"));

    assertEquals("No enum constant domain.piece.PieceColor.black", exception.getMessage());
  }

  @Test
  public void pieceColorValueOfBlackNameWithWhitespaceThrowsIllegalArgumentExceptionWithEnumConstantMessage() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> PieceColor.valueOf(" BLACK "));

    assertEquals("No enum constant domain.piece.PieceColor. BLACK ", exception.getMessage());
  }

  @Test
  public void pieceColorValueOfRedNameThrowsIllegalArgumentExceptionWithEnumConstantMessage() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> PieceColor.valueOf("RED"));

    assertEquals("No enum constant domain.piece.PieceColor.RED", exception.getMessage());
  }
}
