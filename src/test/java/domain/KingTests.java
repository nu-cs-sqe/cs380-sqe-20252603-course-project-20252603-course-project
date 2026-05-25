package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

public class KingTests {

  private static final int MIN_COORD = 0;
  private static final int MAX_COORD = 7;
  private static final int OUT_OF_BOUNDS_POS = 8;
  private static final int OUT_OF_BOUNDS_NEG = -1;

  private static final int CENTER_X = 4;
  private static final int CENTER_Y = 4;

  private static final int ONE_STEP = 1;
  private static final int TWO_STEP = 2;

  @Test
  public void kingConstructorColorBlackCreatesBlackKingWithKingType() {
    King king = new King(PieceColor.BLACK);

    assertEquals(PieceType.KING, king.getType());
    assertEquals(PieceColor.BLACK, king.getColor());
  }

  @Test
  public void kingConstructorColorWhiteCreatesWhiteKingWithKingType() {
    King king = new King(PieceColor.WHITE);

    assertEquals(PieceType.KING, king.getType());
    assertEquals(PieceColor.WHITE, king.getColor());
  }

  @Test
  public void kingConstructorColorNullThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new King(null)
    );

    assertEquals("color must not be null", exception.getMessage());
  }

  @Test
  public void makeCopyBlackKingReturnsDistinctBlackKingCopy() {
    King king = new King(PieceColor.BLACK);

    Piece copy = king.makeCopy();

    assertNotSame(king, copy);
    assertInstanceOf(King.class, copy);
    assertEquals(PieceType.KING, copy.getType());
    assertEquals(PieceColor.BLACK, copy.getColor());
  }

  @Test
  public void makeCopyWhiteKingReturnsDistinctWhiteKingCopy() {
    King king = new King(PieceColor.WHITE);

    Piece copy = king.makeCopy();

    assertNotSame(king, copy);
    assertInstanceOf(King.class, copy);
    assertEquals(PieceType.KING, copy.getType());
    assertEquals(PieceColor.WHITE, copy.getColor());
  }

  @Test
  public void makeCopyNullColorKingThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new King(null)
    );

    assertEquals("color must not be null", exception.getMessage());
  }

  @Test
  public void isValidMoveShapeOneSquareVerticalDownReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X, CENTER_Y - ONE_STEP)
    ));
  }

  @Test
  public void isValidMoveShapeOneSquareVerticalUpReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X, CENTER_Y + ONE_STEP)
    ));
  }

  @Test
  public void isValidMoveShapeOneSquareHorizontalRightReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + ONE_STEP, CENTER_Y)
    ));
  }

  @Test
  public void isValidMoveShapeOneSquareHorizontalLeftReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X - ONE_STEP, CENTER_Y)
    ));
  }

  @Test
  public void isValidMoveShapeOneSquareDiagonalReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + ONE_STEP, CENTER_Y + ONE_STEP)
    ));
  }

  @Test
  public void isValidMoveShapeOneSquareDiagonalOppositeReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X - ONE_STEP, CENTER_Y - ONE_STEP)
    ));
  }

  @Test
  public void isValidMoveShapeSameSquareReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X, CENTER_Y)
    ));
  }

  @Test
  public void isValidMoveShapeHorizontalOverreachReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + TWO_STEP, CENTER_Y)
    ));
  }

  @Test
  public void isValidMoveShapeVerticalOverreachReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X, CENTER_Y + TWO_STEP)
    ));
  }

  @Test
  public void isValidMoveShapeDiagonalOverreachReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + TWO_STEP, CENTER_Y + TWO_STEP)
    ));
  }

  @Test
  public void isValidMoveShapeOneSquareFromMinCornerIntoBoardReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(MIN_COORD, MIN_COORD),
        new Location(MIN_COORD + ONE_STEP, MIN_COORD)
    ));
  }

  @Test
  public void isValidMoveShapeDestinationBelowMinXReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(MIN_COORD, MIN_COORD),
        new Location(OUT_OF_BOUNDS_NEG, MIN_COORD)
    ));
  }

  @Test
  public void isValidMoveShapeDestinationAboveMaxXReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(MAX_COORD, MAX_COORD),
        new Location(OUT_OF_BOUNDS_POS, MAX_COORD)
    ));
  }

  @Test
  public void isValidMoveShapeDestinationAboveMaxYReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(MAX_COORD, MAX_COORD),
        new Location(MAX_COORD, OUT_OF_BOUNDS_POS)
    ));
  }

  @Test
  public void isValidMoveShapeFromNullThrowsIllegalArgumentException() {
    King king = new King(PieceColor.WHITE);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> king.isValidMoveShape(null, new Location(CENTER_X, CENTER_Y + ONE_STEP))
    );

    assertEquals("from must not be null", exception.getMessage());
  }

  @Test
  public void isValidMoveShapeDestinationBelowMinYReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(MIN_COORD, MIN_COORD),
        new Location(MIN_COORD, OUT_OF_BOUNDS_NEG)
    ));
  }

  @Test
  public void isValidMoveShapeToNullThrowsIllegalArgumentException() {
    King king = new King(PieceColor.WHITE);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> king.isValidMoveShape(new Location(CENTER_X, CENTER_Y), null)
    );

    assertEquals("to must not be null", exception.getMessage());
  }

  @Test
  public void isValidMoveShapeNegativeHorizontalOverreachReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X - TWO_STEP, CENTER_Y)
    ));
  }
}