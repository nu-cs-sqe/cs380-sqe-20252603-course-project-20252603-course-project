package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.piece.Bishop;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

public class BishopTests {

  private static final PieceColor BLACK = PieceColor.BLACK;
  private static final PieceColor WHITE = PieceColor.WHITE;

  private static final String NULL_COLOR_MESSAGE = "color must not be null";

  @Test
  public void bishopConstructorBlackColorSetsTypeAndColor() {
    Bishop bishop = new Bishop(BLACK);

    assertEquals(PieceType.BISHOP, bishop.getType());
    assertEquals(BLACK, bishop.getColor());
  }

  @Test
  public void bishopConstructorWhiteColorSetsTypeAndColor() {
    Bishop bishop = new Bishop(WHITE);

    assertEquals(PieceType.BISHOP, bishop.getType());
    assertEquals(WHITE, bishop.getColor());
  }

  @Test
  public void bishopConstructorNullColorThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Bishop(null)
    );

    assertEquals(NULL_COLOR_MESSAGE, exception.getMessage());
  }

  @Test
  public void bishopMakeCopyBlackBishopReturnsDistinctBishopWithSameTypeAndColor() {
    Bishop bishop = new Bishop(BLACK);

    Piece copy = bishop.makeCopy();

    assertNotSame(bishop, copy);
    assertInstanceOf(Bishop.class, copy);
    assertEquals(PieceType.BISHOP, copy.getType());
    assertEquals(BLACK, copy.getColor());
  }

  @Test
  public void bishopMakeCopyWhiteBishopReturnsDistinctBishopWithSameTypeAndColor() {
    Bishop bishop = new Bishop(WHITE);

    Piece copy = bishop.makeCopy();

    assertNotSame(bishop, copy);
    assertInstanceOf(Bishop.class, copy);
    assertEquals(PieceType.BISHOP, copy.getType());
    assertEquals(WHITE, copy.getColor());
  }

  @Test
  public void bishopMakeCopyNullColorBishopThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Bishop(null)
    );

    assertEquals(NULL_COLOR_MESSAGE, exception.getMessage());
  }
}