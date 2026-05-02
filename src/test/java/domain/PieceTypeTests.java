package domain;

import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
