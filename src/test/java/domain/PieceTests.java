package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

public class PieceTests {

  @Test
  public void PieceConstructor_PawnTypeAndBlackColor_StoresTypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals(PieceType.PAWN, piece.getType());
    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void PieceConstructor_RookTypeAndWhiteColor_StoresTypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals(PieceType.ROOK, piece.getType());
    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void PieceConstructor_KnightTypeAndBlackColor_StoresTypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.KNIGHT, PieceColor.BLACK);

    assertEquals(PieceType.KNIGHT, piece.getType());
    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void PieceConstructor_BishopTypeAndWhiteColor_StoresTypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.BISHOP, PieceColor.WHITE);

    assertEquals(PieceType.BISHOP, piece.getType());
    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void PieceConstructor_QueenTypeAndBlackColorStores_TypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.QUEEN, PieceColor.BLACK);

    assertEquals(PieceType.QUEEN, piece.getType());
    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void PieceConstructor_KingTypeAndWhiteColor_StoresTypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.KING, PieceColor.WHITE);

    assertEquals(PieceType.KING, piece.getType());
    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void PieceConstructor_NullType_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new TestPiece(null, PieceColor.BLACK));

    assertEquals("type must not be null", exception.getMessage());
  }

  @Test
  public void PieceConstructor_NullColor_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new TestPiece(PieceType.PAWN, null));

    assertEquals("color must not be null", exception.getMessage());
  }

  @Test
  public void PieceGetType_PawnTypeAndBlackColor_ReturnsPawn() {
    TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals(PieceType.PAWN, piece.getType());
  }

  @Test
  public void PieceGetType_RookTypeAndWhiteColor_ReturnsRook() {
    TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals(PieceType.ROOK, piece.getType());
  }

  @Test
  public void PieceGetType_KnightTypeAndBlackColor_ReturnsKnight() {
    TestPiece piece = new TestPiece(PieceType.KNIGHT, PieceColor.BLACK);

    assertEquals(PieceType.KNIGHT, piece.getType());
  }

  @Test
  public void PieceGetType_BishopTypeAndWhiteColor_ReturnsBishop() {
    TestPiece piece = new TestPiece(PieceType.BISHOP, PieceColor.WHITE);

    assertEquals(PieceType.BISHOP, piece.getType());
  }

  @Test
  public void PieceGetType_QueenTypeAndBlackColor_ReturnsQueen() {
    TestPiece piece = new TestPiece(PieceType.QUEEN, PieceColor.BLACK);

    assertEquals(PieceType.QUEEN, piece.getType());
  }

  @Test
  public void PieceGetType_KingTypeAndWhiteColor_ReturnsKing() {
    TestPiece piece = new TestPiece(PieceType.KING, PieceColor.WHITE);

    assertEquals(PieceType.KING, piece.getType());
  }

  @Test
  public void PieceGetType_NullType_ThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new TestPiece(null, PieceColor.BLACK));

    assertEquals("type must not be null", exception.getMessage());
  }

  @Test
  public void PieceGetColor_PawnTypeAndBlackColor_ReturnsBlack() {
    TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void PieceGetColor_RookTypeAndWhiteColor_ReturnsWhite() {
    TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void PieceMakeCopy_PawnTypeAndBlackColor_ReturnsDistinctCopy() {
    TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    Piece copy = piece.makeCopy();

    assertNotSame(piece, copy);
    assertEquals(PieceType.PAWN, copy.getType());
    assertEquals(PieceColor.BLACK, copy.getColor());
  }

  @Test
  public void PieceMakeCopy_KingTypeAndWhiteColor_ReturnsDistinctCopy() {
    TestPiece piece = new TestPiece(PieceType.KING, PieceColor.WHITE);

    Piece copy = piece.makeCopy();

    assertNotSame(piece, copy);
    assertEquals(PieceType.KING, copy.getType());
    assertEquals(PieceColor.WHITE, copy.getColor());
  }

  @Test
  public void PieceToString_PawnTypeAndBlackColor_ReturnsBlackPawn() {
    TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals("BLACK PAWN", piece.toString());
  }

  @Test
  public void PieceToString_RookTypeAndWhiteColor_ReturnsWhiteRook() {
    TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals("WHITE ROOK", piece.toString());
  }

  @Test
  public void PieceToString_KnightTypeAndBlackColor_ReturnsBlackKnight() {
    TestPiece piece = new TestPiece(PieceType.KNIGHT, PieceColor.BLACK);

    assertEquals("BLACK KNIGHT", piece.toString());
  }

  @Test
  public void PieceToString_BishopTypeAndWhiteColor_ReturnsWhiteBishop() {
    TestPiece piece = new TestPiece(PieceType.BISHOP, PieceColor.WHITE);

    assertEquals("WHITE BISHOP", piece.toString());
  }

  @Test
  public void PieceToString_QueenTypeAndBlackColor_ReturnsBlackQueen() {
    TestPiece piece = new TestPiece(PieceType.QUEEN, PieceColor.BLACK);

    assertEquals("BLACK QUEEN", piece.toString());
  }

  @Test
  public void PieceToString_KingTypeAndWhiteColor_ReturnsWhiteKing() {
    TestPiece piece = new TestPiece(PieceType.KING, PieceColor.WHITE);

    assertEquals("WHITE KING", piece.toString());
  }

  private static class TestPiece extends Piece {

    TestPiece(PieceType type, PieceColor color) {
      super(type, color);
    }

        @Override
        public Piece makeCopy() {
            return new TestPiece(getType(), getColor());
        }

        @Override
        public boolean isValidMoveShape(Location from, Location to) {
            return false;
        }
    }
  }
}
