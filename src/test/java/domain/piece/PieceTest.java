package domain.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {
    @Test
    public void constructor_kingAndWhite_createsSuccessfully() {
        Piece piece = new Piece(PieceType.KING, Color.WHITE);
        assertNotNull(piece);
    }

    @Test
    public void constructor_queenAndBlack_createsSuccessfully() {
        Piece piece = new Piece(PieceType.QUEEN, Color.BLACK);
        assertNotNull(piece);
    }

    @Test
    public void constructor_rookAndWhite_createsSuccessfully() {
        Piece piece = new Piece(PieceType.ROOK, Color.WHITE);
        assertNotNull(piece);
    }

    @Test
    public void constructor_bishopAndBlack_createsSuccessfully() {
        Piece piece = new Piece(PieceType.BISHOP, Color.BLACK);
        assertNotNull(piece);
    }

    @Test
    public void constructor_knightAndWhite_createsSuccessfully() {
        Piece piece = new Piece(PieceType.KNIGHT, Color.WHITE);
        assertNotNull(piece);
    }

    @Test
    public void constructor_pawnAndBlack_createsSuccessfully() {
        Piece piece = new Piece(PieceType.PAWN, Color.BLACK);
        assertNotNull(piece);
    }

    @Test
    public void constructor_nullPieceType_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Piece(null, Color.BLACK));
    }

    @Test
    public void constructor_nullColor_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Piece(PieceType.PAWN, null));
    }

    @Test
    public void getColor_pieceCreatedWithWhite_returnsWhite() {
        Piece piece = new Piece(PieceType.KING, Color.WHITE);
        assertEquals(Color.WHITE, piece.getColor());
    }

    @Test
    public void getColor_pieceCreatedWithBlack_returnsBlack() {
        Piece piece = new Piece(PieceType.KING, Color.BLACK);
        assertEquals(Color.BLACK, piece.getColor());
    }

    @Test
    public void getPieceType_pieceCreatedWithKing_returnsKing() {
        Piece piece = new Piece(PieceType.KING, Color.WHITE);
        assertEquals(PieceType.KING, piece.getPieceType());
    }

    @Test
    public void getPieceType_pieceCreatedWithQueen_returnsQueen() {
        Piece piece = new Piece(PieceType.QUEEN, Color.WHITE);
        assertEquals(PieceType.QUEEN, piece.getPieceType());
    }

    @Test
    public void getPieceType_pieceCreatedWithRook_returnsRook() {
        Piece piece = new Piece(PieceType.ROOK, Color.WHITE);
        assertEquals(PieceType.ROOK, piece.getPieceType());
    }

    @Test
    public void getPieceType_pieceCreatedWithBishop_returnsBishop() {
        Piece piece = new Piece(PieceType.BISHOP, Color.WHITE);
        assertEquals(PieceType.BISHOP, piece.getPieceType());
    }

    @Test
    public void getPieceType_pieceCreatedWithKnight_returnsKnight() {
        Piece piece = new Piece(PieceType.KNIGHT, Color.WHITE);
        assertEquals(PieceType.KNIGHT, piece.getPieceType());
    }

    @Test
    public void getPieceType_pieceCreatedWithPawn_returnsPawn() {
        Piece piece = new Piece(PieceType.PAWN, Color.WHITE);
        assertEquals(PieceType.PAWN, piece.getPieceType());
    }
}
