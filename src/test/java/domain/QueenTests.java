package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Queen;
import org.junit.jupiter.api.Test;

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

  // --- isValidMoveShape ---

  @Test
  public void QueenIsValidMoveShape_HorizontalRight_ReturnsTrue() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertTrue(queen.isValidMoveShape(new Location(0, 4), new Location(7, 4)));
  }

  @Test
  public void QueenIsValidMoveShape_HorizontalLeft_ReturnsTrue() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertTrue(queen.isValidMoveShape(new Location(7, 4), new Location(0, 4)));
  }

  @Test
  public void QueenIsValidMoveShape_VerticalDown_ReturnsTrue() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertTrue(queen.isValidMoveShape(new Location(4, 0), new Location(4, 7)));
  }

  @Test
  public void QueenIsValidMoveShape_VerticalUp_ReturnsTrue() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertTrue(queen.isValidMoveShape(new Location(4, 7), new Location(4, 0)));
  }

  @Test
  public void QueenIsValidMoveShape_DiagonalDownRight_ReturnsTrue() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertTrue(queen.isValidMoveShape(new Location(0, 0), new Location(7, 7)));
  }

  @Test
  public void QueenIsValidMoveShape_DiagonalUpLeft_ReturnsTrue() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertTrue(queen.isValidMoveShape(new Location(7, 7), new Location(0, 0)));
  }

  @Test
  public void QueenIsValidMoveShape_DiagonalDownLeft_ReturnsTrue() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertTrue(queen.isValidMoveShape(new Location(7, 0), new Location(0, 7)));
  }

  @Test
  public void QueenIsValidMoveShape_SameSquare_ReturnsFalse() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertFalse(queen.isValidMoveShape(new Location(4, 4), new Location(4, 4)));
  }

  @Test
  public void QueenIsValidMoveShape_LshapeMove_ReturnsFalse() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertFalse(queen.isValidMoveShape(new Location(4, 4), new Location(6, 5)));
  }

  @Test
  public void QueenIsValidMoveShape_FromNull_ThrowsIllegalArgumentException() {
    Queen queen = new Queen(PieceColor.WHITE);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> queen.isValidMoveShape(null, new Location(4, 4)));

    assertEquals("from must not be null", exception.getMessage());
  }

  @Test
  public void QueenIsValidMoveShape_ToNull_ThrowsIllegalArgumentException() {
    Queen queen = new Queen(PieceColor.WHITE);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> queen.isValidMoveShape(new Location(4, 4), null));

    assertEquals("to must not be null", exception.getMessage());
  }

}