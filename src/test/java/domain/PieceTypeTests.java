package domain;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

class PieceTypeTests {

  @Test
  void pieceTypePawnReferenceEvaluatesToPawnSingleton() {
    PieceType actual = PieceType.PAWN;

    assertEquals(PieceType.PAWN, actual);
  }

  @Test
  void pieceTypeRookReferenceEvaluatesToRookSingleton() {
    PieceType actual = PieceType.ROOK;

    assertEquals(PieceType.ROOK, actual);
  }

  @Test
  void pieceTypeKnightReferenceEvaluatesToKnightSingleton() {
    PieceType actual = PieceType.KNIGHT;

    assertEquals(PieceType.KNIGHT, actual);
  }

  @Test
  void pieceTypeBishopReferenceEvaluatesToBishopSingleton() {
    PieceType actual = PieceType.BISHOP;

    assertEquals(PieceType.BISHOP, actual);
  }

  @Test
  void pieceTypeQueenReferenceEvaluatesToQueenSingleton() {
    PieceType actual = PieceType.QUEEN;

    assertEquals(PieceType.QUEEN, actual);
  }

  @Test
  void pieceTypeKingReferenceEvaluatesToKingSingleton() {
    PieceType actual = PieceType.KING;

    assertEquals(PieceType.KING, actual);
  }

  @Test
  void pieceTypeValuesNoArgumentsReturnsConstantsInDeclarationOrder() {
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
  void pieceTypeValueOfPawnIdentifierReturnsPawnSingleton() {
    PieceType actual = PieceType.valueOf("PAWN");

    assertEquals(PieceType.PAWN, actual);
  }

  @Test
  void pieceTypeValueOfRookIdentifierReturnsRookSingleton() {
    PieceType actual = PieceType.valueOf("ROOK");

    assertEquals(PieceType.ROOK, actual);
  }

  @Test
  void pieceTypeValueOfKnightIdentifierReturnsKnightSingleton() {
    PieceType actual = PieceType.valueOf("KNIGHT");

    assertEquals(PieceType.KNIGHT, actual);
  }

  @Test
  void pieceTypeValueOfBishopIdentifierReturnsBishopSingleton() {
    PieceType actual = PieceType.valueOf("BISHOP");

    assertEquals(PieceType.BISHOP, actual);
  }

  @Test
  void pieceTypeValueOfQueenIdentifierReturnsQueenSingleton() {
    PieceType actual = PieceType.valueOf("QUEEN");

    assertEquals(PieceType.QUEEN, actual);
  }

  @Test
  void pieceTypeValueOfKingIdentifierReturnsKingSingleton() {
    PieceType actual = PieceType.valueOf("KING");

    assertEquals(PieceType.KING, actual);
  }

  @Test
  void pieceTypeValueOfPawnDisplayNameThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> PieceType.valueOf("Pawn")
    );

    assertEquals("No enum constant domain.piece.PieceType.Pawn", exception.getMessage());
  }

  @Test
  void pieceTypeValueOfLowercasePawnIdentifierThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> PieceType.valueOf("pawn")
    );

    assertEquals("No enum constant domain.piece.PieceType.pawn", exception.getMessage());
  }

  @Test
  void pieceTypeValueOfNullNameThrowsNullPointerException() {
    NullPointerException exception = assertThrows(
        NullPointerException.class,
        () -> PieceType.valueOf(null)
    );

    assertEquals("Name is null", exception.getMessage());
  }
}
