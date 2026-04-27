package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test void validPiece_createdSuccessfully() {                         // BVA-PC-01
        Piece piece = new Piece(PieceType.KING, Color.WHITE);
        assertEquals(PieceType.KING, piece.getType());
        assertEquals(Color.WHITE, piece.getColor());
    }

    @Test void blackPawn_createdSuccessfully() {                          // BVA-PC-02
        Piece piece = new Piece(PieceType.PAWN, Color.BLACK);
        assertEquals(PieceType.PAWN, piece.getType());
        assertEquals(Color.BLACK, piece.getColor());
    }

    @Test void nullType_throwsException() {                               // BVA-PC-03
        assertThrows(IllegalArgumentException.class,
                () -> new Piece(null, Color.WHITE));
    }

    @Test void nullColor_throwsException() {                              // BVA-PC-04
        assertThrows(IllegalArgumentException.class,
                () -> new Piece(PieceType.QUEEN, null));
    }

    @Test void allPieceTypes_canBeCreated() {
        for (PieceType type : PieceType.values()) {
            for (Color color : Color.values()) {
                Piece piece = new Piece(type, color);
                assertEquals(type, piece.getType());
                assertEquals(color, piece.getColor());
            }
        }
    }
}