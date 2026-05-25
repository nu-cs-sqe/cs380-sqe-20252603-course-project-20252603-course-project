package domain;

import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Piece;
import domain.piece.Queen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QueenTests {

  @Test
  public void QueenConstructor_BlackColor_SetsTypeAndColor() {
    Queen queen = new Queen(PieceColor.BLACK);

    assertEquals(PieceType.QUEEN, queen.getType());
    assertEquals(PieceColor.BLACK, queen.getColor());
  }

  @Test
  public void QueenConstructor_WhiteColor_SetsTypeAndColor() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertEquals(PieceType.QUEEN, queen.getType());
    assertEquals(PieceColor.WHITE, queen.getColor());
  }

  @Test
  public void QueenConstructor_NullColor_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Queen(null));

    assertEquals("color must not be null", exception.getMessage());
  }

  @Test
  public void QueenMakeCopy_BlackQueen_ReturnsDistinctQueenWithSameTypeAndColor() {
    Queen queen = new Queen(PieceColor.BLACK);

    Piece copy = queen.makeCopy();

    assertNotSame(queen, copy);
    assertInstanceOf(Queen.class, copy);
    assertEquals(PieceType.QUEEN, copy.getType());
    assertEquals(PieceColor.BLACK, copy.getColor());
  }

  @Test
  public void QueenMakeCopy_WhiteQueen_ReturnsDistinctQueenWithSameTypeAndColor() {
    Queen queen = new Queen(PieceColor.WHITE);

    Piece copy = queen.makeCopy();

    assertNotSame(queen, copy);
    assertInstanceOf(Queen.class, copy);
    assertEquals(PieceType.QUEEN, copy.getType());
    assertEquals(PieceColor.WHITE, copy.getColor());
  }

  @Test
  public void QueenMakeCopy_NullColorQueen_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Queen(null));

    assertEquals("color must not be null", exception.getMessage());
  }
}
