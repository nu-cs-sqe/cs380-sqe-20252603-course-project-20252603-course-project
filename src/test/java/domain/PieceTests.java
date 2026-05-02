package domain;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void PieceConstructor_QueenTypeAndBlackColor_StoresTypeAndColor() {
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
