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
  public void pieceConstructorPawnTypeAndBlackColorStoresTypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals(PieceType.PAWN, piece.getType());
    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void pieceConstructorRookTypeAndWhiteColorStoresTypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals(PieceType.ROOK, piece.getType());
    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void pieceConstructorKnightTypeAndBlackColorStoresTypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.KNIGHT, PieceColor.BLACK);

    assertEquals(PieceType.KNIGHT, piece.getType());
    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void pieceConstructorBishopTypeAndWhiteColorStoresTypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.BISHOP, PieceColor.WHITE);

    assertEquals(PieceType.BISHOP, piece.getType());
    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void pieceConstructorQueenTypeAndBlackColorStoresTypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.QUEEN, PieceColor.BLACK);

    assertEquals(PieceType.QUEEN, piece.getType());
    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void pieceConstructorKingTypeAndWhiteColorStoresTypeAndColor() {
    TestPiece piece = new TestPiece(PieceType.KING, PieceColor.WHITE);

    assertEquals(PieceType.KING, piece.getType());
    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void pieceConstructorNullTypeThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new TestPiece(null, PieceColor.BLACK));

    assertEquals("type must not be null", exception.getMessage());
  }

  @Test
  public void pieceConstructorNullColorThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new TestPiece(PieceType.PAWN, null));

    assertEquals("color must not be null", exception.getMessage());
  }

  @Test
  public void pieceGetTypePawnTypeAndBlackColorReturnsPawn() {
    TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals(PieceType.PAWN, piece.getType());
  }

  @Test
  public void pieceGetTypeRookTypeAndWhiteColorReturnsRook() {
    TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals(PieceType.ROOK, piece.getType());
  }

  @Test
  public void pieceGetTypeKnightTypeAndBlackColorReturnsKnight() {
    TestPiece piece = new TestPiece(PieceType.KNIGHT, PieceColor.BLACK);

    assertEquals(PieceType.KNIGHT, piece.getType());
  }

  @Test
  public void pieceGetTypeBishopTypeAndWhiteColorReturnsBishop() {
    TestPiece piece = new TestPiece(PieceType.BISHOP, PieceColor.WHITE);

    assertEquals(PieceType.BISHOP, piece.getType());
  }

  @Test
  public void pieceGetTypeQueenTypeAndBlackColorReturnsQueen() {
    TestPiece piece = new TestPiece(PieceType.QUEEN, PieceColor.BLACK);

    assertEquals(PieceType.QUEEN, piece.getType());
  }

  @Test
  public void pieceGetTypeKingTypeAndWhiteColorReturnsKing() {
    TestPiece piece = new TestPiece(PieceType.KING, PieceColor.WHITE);

    assertEquals(PieceType.KING, piece.getType());
  }

  @Test
  public void pieceGetTypeNullTypeThrowsIllegalArgumentException() {
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> new TestPiece(null, PieceColor.BLACK));

    assertEquals("type must not be null", exception.getMessage());
  }

  @Test
  public void pieceGetColorPawnTypeAndBlackColorReturnsBlack() {
    TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals(PieceColor.BLACK, piece.getColor());
  }

  @Test
  public void pieceGetColorRookTypeAndWhiteColorReturnsWhite() {
    TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals(PieceColor.WHITE, piece.getColor());
  }

  @Test
  public void pieceMakeCopyPawnTypeAndBlackColorReturnsDistinctCopy() {
    TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    Piece copy = piece.makeCopy();

    assertNotSame(piece, copy);
    assertEquals(PieceType.PAWN, copy.getType());
    assertEquals(PieceColor.BLACK, copy.getColor());
  }

  @Test
  public void pieceMakeCopyKingTypeAndWhiteColorReturnsDistinctCopy() {
    TestPiece piece = new TestPiece(PieceType.KING, PieceColor.WHITE);

    Piece copy = piece.makeCopy();

    assertNotSame(piece, copy);
    assertEquals(PieceType.KING, copy.getType());
    assertEquals(PieceColor.WHITE, copy.getColor());
  }

  @Test
  public void pieceToStringPawnTypeAndBlackColorReturnsBlackPawn() {
    TestPiece piece = new TestPiece(PieceType.PAWN, PieceColor.BLACK);

    assertEquals("BLACK PAWN", piece.toString());
  }

  @Test
  public void pieceToStringRookTypeAndWhiteColorReturnsWhiteRook() {
    TestPiece piece = new TestPiece(PieceType.ROOK, PieceColor.WHITE);

    assertEquals("WHITE ROOK", piece.toString());
  }

  @Test
  public void pieceToStringKnightTypeAndBlackColorReturnsBlackKnight() {
    TestPiece piece = new TestPiece(PieceType.KNIGHT, PieceColor.BLACK);

    assertEquals("BLACK KNIGHT", piece.toString());
  }

  @Test
  public void pieceToStringBishopTypeAndWhiteColorReturnsWhiteBishop() {
    TestPiece piece = new TestPiece(PieceType.BISHOP, PieceColor.WHITE);

    assertEquals("WHITE BISHOP", piece.toString());
  }

  @Test
  public void pieceToStringQueenTypeAndBlackColorReturnsBlackQueen() {
    TestPiece piece = new TestPiece(PieceType.QUEEN, PieceColor.BLACK);

    assertEquals("BLACK QUEEN", piece.toString());
  }

  @Test
  public void pieceToStringKingTypeAndWhiteColorReturnsWhiteKing() {
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
  }
}
