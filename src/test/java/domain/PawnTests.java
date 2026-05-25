package domain;

import static org.junit.jupiter.api.Assertions.*;

import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

public class PawnTests {

  // =========================
  // Board bounds
  // =========================
  private static final int MIN_COORD = 0;
  private static final int MAX_COORD = 7;
  private static final int OUT_OF_BOUNDS_POS = 8;
  private static final int OUT_OF_BOUNDS_NEG = -1;

  // =========================
  // Common ranks
  // =========================
  private static final int WHITE_START_RANK = 6;
  private static final int BLACK_START_RANK = 1;

  // =========================
  // Test files / positions
  // =========================
  private static final int CENTER_FILE = 4;
  private static final int EDGE_FILE_LEFT = 0;
  private static final int EDGE_FILE_RIGHT = 7;

  // =========================
  // Movement constants
  // =========================
  private static final int STEP_1 = 1;
  private static final int STEP_2 = 2;
  private static final int STEP_3 = 3;

  // =========================
  // Constructor tests
  // =========================

  @Test
  public void pawnConstructorColorBlackCreatesBlackPawnWithPawnType() {
    Pawn pawn = new Pawn(PieceColor.BLACK);

    assertEquals(PieceType.PAWN, pawn.getType());
    assertEquals(PieceColor.BLACK, pawn.getColor());
  }

  @Test
  public void pawnConstructorColorWhiteCreatesWhitePawnWithPawnType() {
    Pawn pawn = new Pawn(PieceColor.WHITE);

    assertEquals(PieceType.PAWN, pawn.getType());
    assertEquals(PieceColor.WHITE, pawn.getColor());
  }

  @Test
  public void pawnConstructorColorNullThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Pawn(null)
    );

    assertEquals("color must not be null", exception.getMessage());
  }

  // =========================
  // Copy tests
  // =========================

  @Test
  public void pawnMakeCopyBlackPawnReturnsDistinctBlackPawnWithPawnType() {
    Pawn pawn = new Pawn(PieceColor.BLACK);

    Piece copy = pawn.makeCopy();

    assertNotSame(pawn, copy);
    assertInstanceOf(Pawn.class, copy);
    assertEquals(PieceType.PAWN, copy.getType());
    assertEquals(PieceColor.BLACK, copy.getColor());
  }

  @Test
  public void pawnMakeCopyWhitePawnReturnsDistinctWhitePawnWithPawnType() {
    Pawn pawn = new Pawn(PieceColor.WHITE);

    Piece copy = pawn.makeCopy();

    assertNotSame(pawn, copy);
    assertInstanceOf(Pawn.class, copy);
    assertEquals(PieceType.PAWN, copy.getType());
    assertEquals(PieceColor.WHITE, copy.getColor());
  }

  @Test
  public void pawnMakeCopyNullColorPawnThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new Pawn(null)
    );

    assertEquals("color must not be null", exception.getMessage());
  }

  // =========================
  // Move shape tests
  // =========================

  @Test
  public void pawnIsValidMoveShapeWhiteAtStartingRowAllowsInitialTwoSquareMove() {
    Pawn pawn = new Pawn(PieceColor.WHITE);

    assertTrue(pawn.isValidMoveShape(
        new Location(CENTER_FILE, WHITE_START_RANK),
        new Location(CENTER_FILE, WHITE_START_RANK - STEP_2)
    ));
  }

  @Test
  public void pawnIsValidMoveShapeWhitePawnOneSquareForwardReturnsTrue() {
    Pawn pawn = new Pawn(PieceColor.WHITE);

    assertTrue(pawn.isValidMoveShape(
        new Location(CENTER_FILE, WHITE_START_RANK),
        new Location(CENTER_FILE, WHITE_START_RANK - STEP_1)
    ));
  }

  @Test
  public void pawnIsValidMoveShapeBlackPawnOneSquareForwardReturnsTrue() {
    Pawn pawn = new Pawn(PieceColor.BLACK);

    assertTrue(pawn.isValidMoveShape(
        new Location(CENTER_FILE, BLACK_START_RANK),
        new Location(CENTER_FILE, BLACK_START_RANK + STEP_1)
    ));
  }

  @Test
  public void pawnIsValidMoveShapeBlackAtStartingRowAllowsInitialTwoSquareMove() {
    Pawn pawn = new Pawn(PieceColor.BLACK);

    assertTrue(pawn.isValidMoveShape(
        new Location(CENTER_FILE, BLACK_START_RANK),
        new Location(CENTER_FILE, BLACK_START_RANK + STEP_2)
    ));
  }

  // =========================
  // Invalid double moves off start
  // =========================

  @Test
  public void pawnIsValidMoveShapeWhiteOffStartCannotMoveTwoSquares() {
    Pawn pawn = new Pawn(PieceColor.WHITE);

    assertFalse(pawn.isValidMoveShape(
        new Location(CENTER_FILE, WHITE_START_RANK - STEP_1),
        new Location(CENTER_FILE, WHITE_START_RANK - STEP_3)
    ));
  }

  @Test
  public void pawnIsValidMoveShapeBlackOffStartCannotMoveTwoSquares() {
    Pawn pawn = new Pawn(PieceColor.BLACK);

    assertFalse(pawn.isValidMoveShape(
        new Location(CENTER_FILE, BLACK_START_RANK + STEP_1),
        new Location(CENTER_FILE, BLACK_START_RANK + STEP_3)
    ));
  }

  // =========================
  // Diagonal captures
  // =========================

  @Test
  public void pawnDiagonalMovesAreValid() {
    Pawn pawnW = new Pawn(PieceColor.WHITE);

    assertTrue(pawnW.isValidMoveShape(
        new Location(CENTER_FILE, WHITE_START_RANK),
        new Location(CENTER_FILE - STEP_1, WHITE_START_RANK - STEP_1)
    ));

    assertTrue(pawnW.isValidMoveShape(
        new Location(CENTER_FILE, WHITE_START_RANK),
        new Location(CENTER_FILE + STEP_1, WHITE_START_RANK - STEP_1)
    ));
  }

  @Test
  public void pawnBlackDiagonalMovesAreValid() {
    Pawn pawnB = new Pawn(PieceColor.BLACK);

    assertTrue(pawnB.isValidMoveShape(
        new Location(CENTER_FILE, BLACK_START_RANK),
        new Location(CENTER_FILE - STEP_1, BLACK_START_RANK + STEP_1)
    ));

    assertTrue(pawnB.isValidMoveShape(
        new Location(CENTER_FILE, BLACK_START_RANK),
        new Location(CENTER_FILE + STEP_1, BLACK_START_RANK + STEP_1)
    ));
  }

  // =========================
  // Invalid movement types
  // =========================

  @Test
  public void pawnHorizontalMoveReturnsFalse() {
    Pawn pawn = new Pawn(PieceColor.WHITE);

    assertFalse(pawn.isValidMoveShape(
        new Location(CENTER_FILE, WHITE_START_RANK),
        new Location(CENTER_FILE + STEP_1, WHITE_START_RANK)
    ));
  }

  @Test
  public void pawnBackwardMoveReturnsFalse() {
    Pawn pawn = new Pawn(PieceColor.WHITE);

    assertFalse(pawn.isValidMoveShape(
        new Location(CENTER_FILE, WHITE_START_RANK),
        new Location(CENTER_FILE, WHITE_START_RANK + STEP_1)
    ));
  }

  @Test
  public void pawnSameSquareReturnsFalse() {
    Pawn pawn = new Pawn(PieceColor.WHITE);

    assertFalse(pawn.isValidMoveShape(
        new Location(CENTER_FILE, WHITE_START_RANK),
        new Location(CENTER_FILE, WHITE_START_RANK)
    ));
  }

  // =========================
  // Edge / boundary tests
  // =========================

  @Test
  public void pawnEdgeFileMoveIsValid() {
    Pawn pawn = new Pawn(PieceColor.WHITE);

    assertTrue(pawn.isValidMoveShape(
        new Location(EDGE_FILE_LEFT, WHITE_START_RANK),
        new Location(EDGE_FILE_LEFT, WHITE_START_RANK - STEP_1)
    ));
  }

  @Test
  public void pawnOffBoardMovesAreInvalid() {
    Pawn pawnW = new Pawn(PieceColor.WHITE);

    assertFalse(pawnW.isValidMoveShape(
        new Location(EDGE_FILE_LEFT, WHITE_START_RANK),
        new Location(OUT_OF_BOUNDS_NEG, WHITE_START_RANK - STEP_1)
    ));

    Pawn pawnB = new Pawn(PieceColor.BLACK);

    assertFalse(pawnB.isValidMoveShape(
        new Location(EDGE_FILE_RIGHT, BLACK_START_RANK),
        new Location(OUT_OF_BOUNDS_POS, BLACK_START_RANK + STEP_1)
    ));
  }

  // =========================
  // Null handling
  // =========================

  @Test
  public void pawnNullFromThrowsException() {
    Pawn pawn = new Pawn(PieceColor.WHITE);

    assertThrows(IllegalArgumentException.class,
        () -> pawn.isValidMoveShape(null, new Location(CENTER_FILE, WHITE_START_RANK - STEP_1)));
  }

  @Test
  public void pawnNullToThrowsException() {
    Pawn pawn = new Pawn(PieceColor.WHITE);

    assertThrows(IllegalArgumentException.class,
        () -> pawn.isValidMoveShape(new Location(CENTER_FILE, WHITE_START_RANK), null));
  }
}