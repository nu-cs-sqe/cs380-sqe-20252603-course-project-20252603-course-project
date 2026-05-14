package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

public class PieceTests {
  @Test
  public void pieceConstructor_PawnTypeAndBlackColor_StoresTypeAndColor() {
    final TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals(PieceType.PAWN, piece.getType());
    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void pieceConstructor_RookTypeAndWhiteColor_StoresTypeAndColor() {
    final TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals(PieceType.ROOK, piece.getType());
    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void pieceConstructor_KnightTypeAndBlackColor_StoresTypeAndColor() {
    final TestPiece piece = new TestPiece(PieceType.KNIGHT, PieceColor.BLACK);

    assertEquals(PieceType.KNIGHT, piece.getType());
    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void pieceConstructor_BishopTypeAndWhiteColor_StoresTypeAndColor() {
    final TestPiece piece = new TestPiece(PieceType.BISHOP, PieceColor.WHITE);

    assertEquals(PieceType.BISHOP, piece.getType());
    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void pieceConstructor_QueenTypeAndBlackColor_StoresTypeAndColor() {
    final TestPiece piece = new TestPiece(PieceType.QUEEN, PieceColor.BLACK);

    assertEquals(PieceType.QUEEN, piece.getType());
    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void pieceConstructor_KingTypeAndWhiteColor_StoresTypeAndColor() {
    final TestPiece piece = new TestPiece(PieceType.KING, PieceColor.WHITE);

    assertEquals(PieceType.KING, piece.getType());
    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void pieceConstructor_NullType_ThrowsIllegalArgumentException() {
    final IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new TestPiece(null, PieceColor.BLACK));

    assertEquals("type must not be null", exception.getMessage());
  }

  @Test
  public void pieceConstructor_NullColor_ThrowsIllegalArgumentException() {
    final IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new TestPiece(PieceType.PAWN, null));

    assertEquals("color must not be null", exception.getMessage());
  }

  @Test
  public void pieceGetType_PawnTypeAndBlackColor_ReturnsPawn() {
    final TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals(PieceType.PAWN, piece.getType());
  }

  @Test
  public void pieceGetType_RookTypeAndWhiteColor_ReturnsRook() {
    final TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals(PieceType.ROOK, piece.getType());
  }

  @Test
  public void pieceGetType_KnightTypeAndBlackColor_ReturnsKnight() {
    final TestPiece piece = new TestPiece(PieceType.KNIGHT, PieceColor.BLACK);

    assertEquals(PieceType.KNIGHT, piece.getType());
  }

  @Test
  public void pieceGetType_BishopTypeAndWhiteColor_ReturnsBishop() {
    final TestPiece piece = new TestPiece(PieceType.BISHOP, PieceColor.WHITE);

    assertEquals(PieceType.BISHOP, piece.getType());
  }

  @Test
  public void pieceGetType_QueenTypeAndBlackColor_ReturnsQueen() {
    final TestPiece piece = new TestPiece(PieceType.QUEEN, PieceColor.BLACK);

    assertEquals(PieceType.QUEEN, piece.getType());
  }

  @Test
  public void pieceGetType_KingTypeAndWhiteColor_ReturnsKing() {
    final TestPiece piece = new TestPiece(PieceType.KING, PieceColor.WHITE);

    assertEquals(PieceType.KING, piece.getType());
  }

  @Test
  public void pieceGetType_NullType_ThrowsIllegalArgumentException() {
    final IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new TestPiece(null, PieceColor.BLACK));

    assertEquals("type must not be null", exception.getMessage());
  }

  @Test
  public void pieceGetColor_PawnTypeAndBlackColor_ReturnsBlack() {
    final TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void pieceGetColor_RookTypeAndWhiteColor_ReturnsWhite() {
    final TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void pieceMakeCopy_PawnTypeAndBlackColor_ReturnsDistinctCopy() {
    final TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    final Piece copy = piece.makeCopy();

    assertNotSame(piece, copy);
    assertEquals(PieceType.PAWN, copy.getType());
    assertEquals(PieceColor.BLACK, copy.getColor());
  }

  @Test
  public void pieceMakeCopy_KingTypeAndWhiteColor_ReturnsDistinctCopy() {
    final TestPiece piece = new TestPiece(PieceType.KING, PieceColor.WHITE);

    final Piece copy = piece.makeCopy();

    assertNotSame(piece, copy);
    assertEquals(PieceType.KING, copy.getType());
    assertEquals(PieceColor.WHITE, copy.getColor());
  }

  @Test
  public void pieceToString_PawnTypeAndBlackColor_ReturnsBlackPawn() {
    final TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals("BLACK PAWN", piece.toString());
  }

  @Test
  public void pieceToString_RookTypeAndWhiteColor_ReturnsWhiteRook() {
    final TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals("WHITE ROOK", piece.toString());
  }

  @Test
  public void pieceToString_KnightTypeAndBlackColor_ReturnsBlackKnight() {
    final TestPiece piece = new TestPiece(PieceType.KNIGHT, PieceColor.BLACK);

    assertEquals("BLACK KNIGHT", piece.toString());
  }

  @Test
  public void pieceToString_BishopTypeAndWhiteColor_ReturnsWhiteBishop() {
    final TestPiece piece = new TestPiece(PieceType.BISHOP, PieceColor.WHITE);

    assertEquals("WHITE BISHOP", piece.toString());
  }

  @Test
  public void pieceToString_QueenTypeAndBlackColor_ReturnsBlackQueen() {
    final TestPiece piece = new TestPiece(PieceType.QUEEN, PieceColor.BLACK);

    assertEquals("BLACK QUEEN", piece.toString());
  }

  @Test
  public void pieceToString_KingTypeAndWhiteColor_ReturnsWhiteKing() {
    final TestPiece piece = new TestPiece(PieceType.KING, PieceColor.WHITE);

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
    public boolean hasMoved() {
      return false;
    }
  }
}
