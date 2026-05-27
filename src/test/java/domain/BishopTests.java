package domain;

import domain.piece.Bishop;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BishopTests {
  @Test
  public void BishopConstructor_BlackColor_SetsTypeAndColor() {
    Bishop bishop = new Bishop(PieceColor.BLACK);

    assertEquals(PieceType.BISHOP, bishop.getType());
    assertEquals(PieceColor.BLACK, bishop.getColor());
  }

  @Test
  public void BishopConstructor_WhiteColor_SetsTypeAndColor() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    assertEquals(PieceType.BISHOP, bishop.getType());
    assertEquals(PieceColor.WHITE, bishop.getColor());
  }

  @Test
  public void BishopConstructor_NullColor_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Bishop(null));

    assertEquals("color must not be null", exception.getMessage());
  }

  @Test
  public void BishopMakeCopy_BlackBishop_ReturnsDistinctBishopWithSameTypeAndColor() {
    Bishop bishop = new Bishop(PieceColor.BLACK);

    Piece copy = bishop.makeCopy();

    assertNotSame(bishop, copy);
    assertInstanceOf(Bishop.class, copy);
    assertEquals(PieceType.BISHOP, copy.getType());
    assertEquals(PieceColor.BLACK, copy.getColor());
  }

  @Test
  public void BishopMakeCopy_WhiteBishop_ReturnsDistinctBishopWithSameTypeAndColor() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    Piece copy = bishop.makeCopy();

    assertNotSame(bishop, copy);
    assertInstanceOf(Bishop.class, copy);
    assertEquals(PieceType.BISHOP, copy.getType());
    assertEquals(PieceColor.WHITE, copy.getColor());
  }

  @Test
  public void BishopMakeCopy_NullColorBishop_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Bishop(null));

    assertEquals("color must not be null", exception.getMessage());
  }

  // --- isValidMoveShape ---

  @Test
  public void BishopIsValidMoveShape_DiagonalDownRight_ReturnsTrue() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    assertTrue(bishop.isValidMoveShape(new Location(4, 4), new Location(6, 6)));
  }

  @Test
  public void BishopIsValidMoveShape_DiagonalUpLeft_ReturnsTrue() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    assertTrue(bishop.isValidMoveShape(new Location(4, 4), new Location(2, 2)));
  }

  @Test
  public void BishopIsValidMoveShape_DiagonalDownLeft_ReturnsTrue() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    assertTrue(bishop.isValidMoveShape(new Location(4, 4), new Location(2, 6)));
  }

  @Test
  public void BishopIsValidMoveShape_DiagonalUpRight_ReturnsTrue() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    assertTrue(bishop.isValidMoveShape(new Location(4, 4), new Location(6, 2)));
  }

  @Test
  public void BishopIsValidMoveShape_MaxDiagonal_ReturnsTrue() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    assertTrue(bishop.isValidMoveShape(new Location(0, 0), new Location(7, 7)));
  }

  @Test
  public void BishopIsValidMoveShape_MinDiagonal_ReturnsTrue() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    assertTrue(bishop.isValidMoveShape(new Location(3, 3), new Location(4, 4)));
  }

  @Test
  public void BishopIsValidMoveShape_StraightVertical_ReturnsFalse() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    assertFalse(bishop.isValidMoveShape(new Location(4, 4), new Location(4, 6)));
  }

  @Test
  public void BishopIsValidMoveShape_StraightHorizontal_ReturnsFalse() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    assertFalse(bishop.isValidMoveShape(new Location(4, 4), new Location(6, 4)));
  }

  @Test
  public void BishopIsValidMoveShape_SameSquare_ReturnsFalse() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    assertFalse(bishop.isValidMoveShape(new Location(4, 4), new Location(4, 4)));
  }

  @Test
  public void BishopIsValidMoveShape_LShapeMove_ReturnsFalse() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    assertFalse(bishop.isValidMoveShape(new Location(4, 4), new Location(6, 5)));
  }

  @Test
  public void BishopIsValidMoveShape_FromNull_ThrowsIllegalArgumentException() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> bishop.isValidMoveShape(null, new Location(5, 5)));

    assertEquals("from must not be null", exception.getMessage());
  }

  @Test
  public void BishopIsValidMoveShape_ToNull_ThrowsIllegalArgumentException() {
    Bishop bishop = new Bishop(PieceColor.WHITE);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> bishop.isValidMoveShape(new Location(4, 4), null));

    assertEquals("to must not be null", exception.getMessage());
  }

}
