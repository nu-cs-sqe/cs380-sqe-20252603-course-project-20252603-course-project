package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Piece;
import domain.piece.Queen;
import org.junit.jupiter.api.Test;

public class QueenTests {

  @Test
  public void queenConstructorBlackColorSetsTypeAndColor() {
    Queen queen = new Queen(PieceColor.BLACK);

    assertEquals(PieceType.QUEEN, queen.getType());
    assertEquals(PieceColor.BLACK, queen.getColor());
  }

  @Test
  public void queenConstructorWhiteColorSetsTypeAndColor() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertEquals(PieceType.QUEEN, queen.getType());
    assertEquals(PieceColor.WHITE, queen.getColor());
  }

  @Test
  public void queenConstructorNullColorThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Queen(null));

    assertEquals("color must not be null", exception.getMessage());
  }

  @Test
  public void queenMakeCopyBlackQueenReturnsDistinctQueenWithSameTypeAndColor() {
    Queen queen = new Queen(PieceColor.BLACK);

    Piece copy = queen.makeCopy();

    assertNotSame(queen, copy);
    assertInstanceOf(Queen.class, copy);
    assertEquals(PieceType.QUEEN, copy.getType());
    assertEquals(PieceColor.BLACK, copy.getColor());
  }

  @Test
  public void queenMakeCopyWhiteQueenReturnsDistinctQueenWithSameTypeAndColor() {
    Queen queen = new Queen(PieceColor.WHITE);

    Piece copy = queen.makeCopy();

    assertNotSame(queen, copy);
    assertInstanceOf(Queen.class, copy);
    assertEquals(PieceType.QUEEN, copy.getType());
    assertEquals(PieceColor.WHITE, copy.getColor());
  }

  @Test
  public void queenMakeCopyNullColorQueenThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Queen(null));

    assertEquals("color must not be null", exception.getMessage());
  }
}
