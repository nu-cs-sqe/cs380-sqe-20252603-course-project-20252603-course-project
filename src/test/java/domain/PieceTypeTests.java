package domain;

import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PieceTypeTests {

  @Test
  void PieceType_PawnReference_EvaluatesToPawnSingleton() {
    PieceType actual = PieceType.PAWN;

    assertEquals(PieceType.PAWN, actual);
  }

  @Test
  void PieceType_RookReference_EvaluatesToRookSingleton() {
    PieceType actual = PieceType.ROOK;

    assertEquals(PieceType.ROOK, actual);
  }

  @Test
  void PieceType_KnightReference_EvaluatesToKnightSingleton() {
    PieceType actual = PieceType.KNIGHT;

    assertEquals(PieceType.KNIGHT, actual);
  }

  @Test
  void PieceType_BishopReference_EvaluatesToBishopSingleton() {
    PieceType actual = PieceType.BISHOP;

    assertEquals(PieceType.BISHOP, actual);
  }

  @Test
  void PieceType_QueenReference_EvaluatesToQueenSingleton() {
    PieceType actual = PieceType.QUEEN;

    assertEquals(PieceType.QUEEN, actual);
  }

  @Test
  void PieceType_KingReference_EvaluatesToKingSingleton() {
    PieceType actual = PieceType.KING;

    assertEquals(PieceType.KING, actual);
  }

  @Test
  void PieceTypeValues_NoArguments_ReturnsConstantsInDeclarationOrder() {
    PieceType[] actual = PieceType.values();

    assertArrayEquals(new PieceType[]{
        PieceType.PAWN,
        PieceType.ROOK,
        PieceType.KNIGHT,
        PieceType.BISHOP,
        PieceType.QUEEN,
        PieceType.KING
    }, actual);
  }

  @Test
  void PieceTypeValueOf_PawnIdentifier_ReturnsPawnSingleton() {
    PieceType actual = PieceType.valueOf("PAWN");

    assertEquals(PieceType.PAWN, actual);
  }

  @Test
  void PieceTypeValueOf_RookIdentifier_ReturnsRookSingleton() {
    PieceType actual = PieceType.valueOf("ROOK");

    assertEquals(PieceType.ROOK, actual);
  }

  @Test
  void PieceTypeValueOf_KnightIdentifier_ReturnsKnightSingleton() {
    PieceType actual = PieceType.valueOf("KNIGHT");

    assertEquals(PieceType.KNIGHT, actual);
  }

  @Test
  void PieceTypeValueOf_BishopIdentifier_ReturnsBishopSingleton() {
    PieceType actual = PieceType.valueOf("BISHOP");

    assertEquals(PieceType.BISHOP, actual);
  }

  @Test
  void PieceTypeValueOf_QueenIdentifier_ReturnsQueenSingleton() {
    PieceType actual = PieceType.valueOf("QUEEN");

    assertEquals(PieceType.QUEEN, actual);
  }

  @Test
  void PieceTypeValueOf_KingIdentifier_ReturnsKingSingleton() {
    PieceType actual = PieceType.valueOf("KING");

    assertEquals(PieceType.KING, actual);
  }

  @Test
  void PieceTypeValueOf_PawnDisplayName_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> PieceType.valueOf("Pawn")
    );

    assertEquals("No enum constant domain.piece.PieceType.Pawn", exception.getMessage());
  }

  @Test
  void PieceTypeValueOf_LowercasePawnIdentifier_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> PieceType.valueOf("pawn")
    );

    assertEquals("No enum constant domain.piece.PieceType.pawn", exception.getMessage());
  }

  @Test
  void PieceTypeValueOf_NullName_ThrowsNullPointerException() {
    NullPointerException exception = assertThrows(
        NullPointerException.class,
        () -> PieceType.valueOf(null)
    );

    assertEquals("Name is null", exception.getMessage());
  }
}
