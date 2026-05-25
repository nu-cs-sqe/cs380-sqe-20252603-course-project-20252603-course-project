package domain;

import static org.junit.jupiter.api.Assertions.*;

import domain.piece.Knight;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

public class KnightTests {

  private static final int MIN_COORD = 0;
  private static final int MAX_COORD = 7;
  private static final int OUT_OF_BOUNDS_POS = 8;
  private static final int OUT_OF_BOUNDS_NEG = -1;

  private static final int CENTER_X = 4;
  private static final int CENTER_Y = 4;

  private static final int CORNER_X = MIN_COORD;
  private static final int CORNER_Y = MIN_COORD;

  private static final int FAR_CORNER_X = MAX_COORD;
  private static final int FAR_CORNER_Y = MAX_COORD;

  private static final int STEP_1 = 1;
  private static final int STEP_2 = 2;
  private static final int STEP_3 = 3;

  @Test
  public void knightConstructorBlackColorSetsTypeAndColor() {
    Knight knight = new Knight(PieceColor.BLACK);

    assertEquals(PieceType.KNIGHT, knight.getType());
    assertEquals(PieceColor.BLACK, knight.getColor());
  }

  @Test
  public void knightConstructorWhiteColorSetsTypeAndColor() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertEquals(PieceType.KNIGHT, knight.getType());
    assertEquals(PieceColor.WHITE, knight.getColor());
  }

  @Test
  public void knightConstructorNullColorThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Knight(null)
    );

    assertEquals("color must not be null", exception.getMessage());
  }

  @Test
  public void knightMakeCopyBlackKnightReturnsDistinctKnightWithSameTypeAndColor() {
    Knight knight = new Knight(PieceColor.BLACK);

    Piece copy = knight.makeCopy();

    assertNotSame(knight, copy);
    assertInstanceOf(Knight.class, copy);
    assertEquals(PieceType.KNIGHT, copy.getType());
    assertEquals(PieceColor.BLACK, copy.getColor());
  }

  @Test
  public void knightMakeCopyWhiteKnightReturnsDistinctKnightWithSameTypeAndColor() {
    Knight knight = new Knight(PieceColor.WHITE);

    Piece copy = knight.makeCopy();

    assertNotSame(knight, copy);
    assertInstanceOf(Knight.class, copy);
    assertEquals(PieceType.KNIGHT, copy.getType());
    assertEquals(PieceColor.WHITE, copy.getColor());
  }

  @Test
  public void knightMakeCopyNullColorKnightThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Knight(null)
    );

    assertEquals("color must not be null", exception.getMessage());
  }

  @Test
  public void isValidMoveShapeDx1Dy2ReturnsTrue() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertTrue(knight.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + STEP_1, CENTER_Y + STEP_2)
    ));
  }

  @Test
  public void isValidMoveShapeDx1Dy2OppositeHorizontalReturnsTrue() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertTrue(knight.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X - STEP_1, CENTER_Y + STEP_2)
    ));
  }

  @Test
  public void isValidMoveShapeDx1Dy2OppositeVerticalReturnsTrue() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertTrue(knight.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + STEP_1, CENTER_Y - STEP_2)
    ));
  }

  @Test
  public void isValidMoveShapeDx2Dy1ReturnsTrue() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertTrue(knight.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + STEP_2, CENTER_Y + STEP_1)
    ));
  }

  @Test
  public void isValidMoveShapeDx2Dy1OppositeHorizontalReturnsTrue() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertTrue(knight.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X - STEP_2, CENTER_Y + STEP_1)
    ));
  }

  @Test
  public void isValidMoveShapeDx2Dy1OppositeVerticalReturnsTrue() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertTrue(knight.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + STEP_2, CENTER_Y - STEP_1)
    ));
  }

  @Test
  public void isValidMoveShapeSameSquareReturnsFalse() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertFalse(knight.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X, CENTER_Y)
    ));
  }

  @Test
  public void isValidMoveShapeStraightVerticalReturnsFalse() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertFalse(knight.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X, CENTER_Y + STEP_2)
    ));
  }

  @Test
  public void isValidMoveShapeOneSquareDiagonalReturnsFalse() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertFalse(knight.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + STEP_1, CENTER_Y + STEP_1)
    ));
  }

  @Test
  public void isValidMoveShapeTwoByTwoSquareReturnsFalse() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertFalse(knight.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + STEP_2, CENTER_Y + STEP_2)
    ));
  }

  @Test
  public void isValidMoveShapeDx3Dy1OverreachReturnsFalse() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertFalse(knight.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + STEP_3, CENTER_Y + STEP_1)
    ));
  }

  @Test
  public void isValidMoveShapeLShapeFromMinimumCornerReturnsTrue() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertTrue(knight.isValidMoveShape(
        new Location(CORNER_X, CORNER_Y),
        new Location(CORNER_X + STEP_1, CORNER_Y + STEP_2)
    ));
  }

  @Test
  public void isValidMoveShapeDestinationXBelowMinimumReturnsFalse() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertFalse(knight.isValidMoveShape(
        new Location(CORNER_X, CORNER_Y),
        new Location(OUT_OF_BOUNDS_NEG, CORNER_Y + STEP_2)
    ));
  }

  @Test
  public void isValidMoveShapeDestinationYBelowMinimumReturnsFalse() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertFalse(knight.isValidMoveShape(
        new Location(CORNER_X, CORNER_Y),
        new Location(CORNER_X + STEP_2, OUT_OF_BOUNDS_NEG)
    ));
  }

  @Test
  public void isValidMoveShapeDestinationXAboveMaximumReturnsFalse() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertFalse(knight.isValidMoveShape(
        new Location(FAR_CORNER_X, FAR_CORNER_Y),
        new Location(OUT_OF_BOUNDS_POS, FAR_CORNER_Y - STEP_2)
    ));
  }

  @Test
  public void isValidMoveShapeDestinationYAboveMaximumReturnsFalse() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertFalse(knight.isValidMoveShape(
        new Location(FAR_CORNER_X, FAR_CORNER_Y),
        new Location(FAR_CORNER_X - STEP_2, OUT_OF_BOUNDS_POS)
    ));
  }

  @Test
  public void isValidMoveShapeFromNullThrowsIllegalArgumentException() {
    Knight knight = new Knight(PieceColor.WHITE);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> knight.isValidMoveShape(null, new Location(CENTER_X + STEP_1, CENTER_Y + STEP_2))
    );

    assertEquals("from must not be null", exception.getMessage());
  }

  @Test
  public void isValidMoveShapeToNullThrowsIllegalArgumentException() {
    Knight knight = new Knight(PieceColor.WHITE);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> knight.isValidMoveShape(new Location(CENTER_X, CENTER_Y), null)
    );

    assertEquals("to must not be null", exception.getMessage());
  }
}