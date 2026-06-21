package domain;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Board {

  private static final PieceType[] BACK_RANK = {
      PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
      PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK,
  };

  private final Piece[][] squares = new Piece[8][8];

  private Optional<Piece> pieceAt(Position position) {
    return Optional.ofNullable(squares[position.getRow() - 1][position.getCol() - 1]);
  }

  public void initializeBoard() {
    for (int col = 0; col < 8; ++col) {
      // white pieces
      squares[0][col] = new Piece(BACK_RANK[col], Color.WHITE);
      squares[1][col] = new Piece(PieceType.PAWN, Color.WHITE);

      // black pieces
      squares[7][col] = new Piece(BACK_RANK[col], Color.BLACK);
      squares[6][col] = new Piece(PieceType.PAWN, Color.BLACK);
    }
  }

  public boolean isEmpty(Position position) {
    return pieceAt(position).isEmpty();
  }

  public Piece getPieceAt(Position position) {
    return pieceAt(position).orElseThrow(
        () -> new NoSuchElementException("Cannot get piece at empty position")
    );
  }
}