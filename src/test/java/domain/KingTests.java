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
  public void Constructor_ColorBlack_CreatesBlackKingWithKingType() {
    King king = new King(PieceColor.BLACK);

    assertEquals(PieceType.KING, king.getType());
    assertEquals(PieceColor.BLACK, king.getColor());
  }

  @Test
  public void Constructor_ColorWhite_CreatesWhiteKingWithKingType() {
    King king = new King(PieceColor.WHITE);

    assertEquals(PieceType.KING, king.getType());
    assertEquals(PieceColor.WHITE, king.getColor());
  }

  @Test
  public void Constructor_ColorNull_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new King(null)
    );

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
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new King(null)
    );

    assertEquals("color must not be null", exception.getMessage());
  }

  @Test
  public void IsValidMoveShape_OneSquareVerticalDown_ReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X, CENTER_Y - ONE_STEP)
    ));
  }

  @Test
  public void IsValidMoveShape_OneSquareVerticalUp_ReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X, CENTER_Y + ONE_STEP)
    ));
  }

  @Test
  public void IsValidMoveShape_OneSquareHorizontalRight_ReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + ONE_STEP, CENTER_Y)
    ));
  }

  @Test
  public void IsValidMoveShape_OneSquareHorizontalLeft_ReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X - ONE_STEP, CENTER_Y)
    ));
  }

  @Test
  public void IsValidMoveShape_OneSquareDiagonal_ReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + ONE_STEP, CENTER_Y + ONE_STEP)
    ));
  }

  @Test
  public void IsValidMoveShape_OneSquareDiagonalOpposite_ReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X - ONE_STEP, CENTER_Y - ONE_STEP)
    ));
  }

  @Test
  public void IsValidMoveShape_SameSquare_ReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X, CENTER_Y)
    ));
  }

  @Test
  public void IsValidMoveShape_HorizontalOverreach_ReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + TWO_STEP, CENTER_Y)
    ));
  }

  @Test
  public void IsValidMoveShape_VerticalOverreach_ReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X, CENTER_Y + TWO_STEP)
    ));
  }

  @Test
  public void IsValidMoveShape_DiagonalOverreach_ReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X + TWO_STEP, CENTER_Y + TWO_STEP)
    ));
  }

  @Test
  public void IsValidMoveShape_OneSquareFromMinCornerIntoBoard_ReturnsTrue() {
    King king = new King(PieceColor.WHITE);

    assertTrue(king.isValidMoveShape(
        new Location(MIN_COORD, MIN_COORD),
        new Location(MIN_COORD + ONE_STEP, MIN_COORD)
    ));
  }

  @Test
  public void IsValidMoveShape_DestinationBelowMinX_ReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(MIN_COORD, MIN_COORD),
        new Location(OUT_OF_BOUNDS_NEG, MIN_COORD)
    ));
  }

  @Test
  public void IsValidMoveShape_DestinationAboveMaxX_ReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(MAX_COORD, MAX_COORD),
        new Location(OUT_OF_BOUNDS_POS, MAX_COORD)
    ));
  }

  @Test
  public void IsValidMoveShape_DestinationAboveMaxY_ReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(MAX_COORD, MAX_COORD),
        new Location(MAX_COORD, OUT_OF_BOUNDS_POS)
    ));
  }

  @Test
  public void IsValidMoveShape_FromNull_ThrowsIllegalArgumentException() {
    King king = new King(PieceColor.WHITE);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> king.isValidMoveShape(null, new Location(CENTER_X, CENTER_Y + ONE_STEP))
    );

    assertEquals("from must not be null", exception.getMessage());
  }

  @Test
  public void IsValidMoveShape_DestinationBelowMinY_ReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(MIN_COORD, MIN_COORD),
        new Location(MIN_COORD, OUT_OF_BOUNDS_NEG)
    ));
  }

  @Test
  public void IsValidMoveShape_ToNull_ThrowsIllegalArgumentException() {
    King king = new King(PieceColor.WHITE);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> king.isValidMoveShape(new Location(CENTER_X, CENTER_Y), null)
    );

    assertEquals("to must not be null", exception.getMessage());
  }

  @Test
  public void IsValidMoveShape_NegativeHorizontalOverreach_ReturnsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.isValidMoveShape(
        new Location(CENTER_X, CENTER_Y),
        new Location(CENTER_X - TWO_STEP, CENTER_Y)
    ));
  }
}