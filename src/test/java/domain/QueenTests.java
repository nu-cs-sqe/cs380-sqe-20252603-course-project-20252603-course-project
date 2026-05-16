package domain;

import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Piece;
import domain.piece.Queen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Queen(null));

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
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Queen(null));

		assertEquals("color must not be null", exception.getMessage());
	}

	@Test
	public void QueenIsValidMoveShape_StraightLineHorizontal_ReturnsTrue() {
		Queen queen = new Queen(PieceColor.WHITE);

		assertTrue(queen.isValidMoveShape(new Location(0, 3), new Location(5, 3)));
	}

	@Test
	public void QueenIsValidMoveShape_StraightLineVertical_ReturnsTrue() {
		Queen queen = new Queen(PieceColor.WHITE);

		assertTrue(queen.isValidMoveShape(new Location(4, 0), new Location(4, 7)));
	}

	@Test
	public void QueenIsValidMoveShape_DiagonalMove_ReturnsTrue() {
		Queen queen = new Queen(PieceColor.WHITE);

		assertTrue(queen.isValidMoveShape(new Location(0, 0), new Location(3, 3)));
	}

	@Test
	public void QueenIsValidMoveShape_SameSquare_ReturnsFalse() {
		Queen queen = new Queen(PieceColor.WHITE);

		assertFalse(queen.isValidMoveShape(new Location(3, 3), new Location(3, 3)));
	}

	@Test
	public void QueenIsValidMoveShape_KnightShapeMove_ReturnsFalse() {
		Queen queen = new Queen(PieceColor.WHITE);

		assertFalse(queen.isValidMoveShape(new Location(0, 0), new Location(1, 2)));
	}

	@Test
	public void QueenIsValidMoveShape_OutOfBoundsDestination_ReturnsFalse() {
		Queen queen = new Queen(PieceColor.WHITE);

		assertFalse(queen.isValidMoveShape(new Location(0, 0), new Location(8, 0)));
	}

	@Test
	public void QueenIsValidMoveShape_NullFrom_ThrowsIllegalArgumentException() {
		Queen queen = new Queen(PieceColor.WHITE);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> queen.isValidMoveShape(null, new Location(3, 3)));

		assertEquals("from must not be null", exception.getMessage());
	}

	@Test
	public void QueenIsValidMoveShape_NullTo_ThrowsIllegalArgumentException() {
		Queen queen = new Queen(PieceColor.WHITE);

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> queen.isValidMoveShape(new Location(0, 0), null));

		assertEquals("to must not be null", exception.getMessage());
	}
}
