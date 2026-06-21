package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;


public class BoardTest {

  /*
   * isEmpty() unit tests
   * */

  @ParameterizedTest
  @CsvSource({
      "1, 1",
      "2, 1",
      "7, 1",
      "8, 1",
      "1, 2",
      "1, 7",
      "1, 8",
  })
  public void IsEmpty_NewBoardRowColBounds_IsTrue(int row, int col) {
    Board board = new Board();
    Position position = EasyMock.createMock(Position.class);

    EasyMock.expect(position.getRow()).andStubReturn(row);
    EasyMock.expect(position.getCol()).andStubReturn(col);

    EasyMock.replay(position);

    boolean actual = board.isEmpty(position);

    assertTrue(actual);
  }

  @Test
  public void IsEmpty_AfterInitRow1Col1_IsFalse() {
    Board board = new Board();
    Position position = EasyMock.createMock(Position.class);

    EasyMock.expect(position.getRow()).andStubReturn(1);
    EasyMock.expect(position.getCol()).andStubReturn(1);

    EasyMock.replay(position);

    board.initializeBoard();

    boolean actual = board.isEmpty(position);

    assertFalse(actual);
  }

  /*
   * getPieceAt() SOCIABLE unit tests
   * */

  @ParameterizedTest
  @CsvSource({
      "1, 1, WHITE, ROOK",
      "1, 8, WHITE, ROOK",
      "8, 1, BLACK, ROOK",
      "2, 1, WHITE, PAWN",
      "7, 1, BLACK, PAWN",
      "1, 2, WHITE, KNIGHT",
      "1, 7, WHITE, KNIGHT",
      "1, 3, WHITE, BISHOP",
      "1, 4, WHITE, QUEEN",
      "1, 5, WHITE, KING",
  })
  public void GetPieceAt_AfterInitRowXColY_CorrectPiece(int row,
                                                        int col,
                                                        Color color,
                                                        PieceType pieceType) {
    Board board = new Board();
    Position position = EasyMock.createMock(Position.class);

    EasyMock.expect(position.getRow()).andStubReturn(row);
    EasyMock.expect(position.getCol()).andStubReturn(col);

    EasyMock.replay(position);

    board.initializeBoard();

    Piece actual = board.getPieceAt(position);

    assertEquals(color, actual.getColor());
    assertEquals(pieceType, actual.getPieceType());
    assertFalse(board.isEmpty(position));
  }

  @Test
  public void GetPieceAt_NewBoardRow1Col1_ThrowsException() {
    Board board = new Board();
    Position position = EasyMock.createMock(Position.class);

    EasyMock.expect(position.getRow()).andStubReturn(1);
    EasyMock.expect(position.getCol()).andStubReturn(1);

    EasyMock.replay(position);

    Exception exception = assertThrows(
        NoSuchElementException.class,
        () -> board.getPieceAt(position)
    );

    assertTrue(board.isEmpty(position));

    String expected = "Cannot get piece at empty position";
    String actual = exception.getMessage();
    assertEquals(expected, actual);

  }

  /*
   * initializeBoard() unit tests
   * */

  @ParameterizedTest
  @CsvSource({
      "3, 1",
      "3, 8",
      "6, 1",
      "6, 8",
      "4, 4",
  })
  public void InitializeBoard_EmptySquare_EmptyIsTrue(int row, int col) {
    Board board = new Board();
    Position position = EasyMock.createMock(Position.class);

    EasyMock.expect(position.getRow()).andStubReturn(row);
    EasyMock.expect(position.getCol()).andStubReturn(col);

    EasyMock.replay(position);

    board.initializeBoard();

    boolean actual = board.isEmpty(position);

    assertTrue(actual);
  }

  @ParameterizedTest
  @CsvSource({
      "1, ROOK",
      "2, KNIGHT",
      "3, BISHOP",
      "4, QUEEN",
      "5, KING",
      "6, BISHOP",
      "7, KNIGHT",
      "8, ROOK",
  })
  public void InitializeBoard_Row1ColY_WhiteBackRankCorrect(int col,
                                                            PieceType pieceType) {
    Board board = new Board();
    Position position = EasyMock.createMock(Position.class);

    EasyMock.expect(position.getRow()).andStubReturn(1);
    EasyMock.expect(position.getCol()).andStubReturn(col);

    EasyMock.replay(position);

    board.initializeBoard();

    Piece actual = board.getPieceAt(position);

    assertEquals(Color.WHITE, actual.getColor());
    assertEquals(pieceType, actual.getPieceType());
    assertFalse(board.isEmpty(position));
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
  public void InitializeBoard_Row2_WhitePawns(int col) {
    Board board = new Board();
    Position position = EasyMock.createMock(Position.class);

    EasyMock.expect(position.getRow()).andStubReturn(2);
    EasyMock.expect(position.getCol()).andStubReturn(col);

    EasyMock.replay(position);

    board.initializeBoard();

    Piece actual = board.getPieceAt(position);

    assertEquals(Color.WHITE, actual.getColor());
    assertEquals(PieceType.PAWN, actual.getPieceType());
    assertFalse(board.isEmpty(position));
  }

  @ParameterizedTest
  @CsvSource({
      "1, ROOK",
      "2, KNIGHT",
      "3, BISHOP",
      "4, QUEEN",
      "5, KING",
      "6, BISHOP",
      "7, KNIGHT",
      "8, ROOK",
  })
  public void InitializeBoard_Row8ColY_BlackBackRankCorrect(int col,
                                                            PieceType pieceType) {
    Board board = new Board();
    Position position = EasyMock.createMock(Position.class);

    EasyMock.expect(position.getRow()).andStubReturn(8);
    EasyMock.expect(position.getCol()).andStubReturn(col);

    EasyMock.replay(position);

    board.initializeBoard();

    Piece actual = board.getPieceAt(position);

    assertEquals(Color.BLACK, actual.getColor());
    assertEquals(pieceType, actual.getPieceType());
    assertFalse(board.isEmpty(position));
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
  public void InitializeBoard_Row7_BlackPawns(int col) {
    Board board = new Board();
    Position position = EasyMock.createMock(Position.class);

    EasyMock.expect(position.getRow()).andStubReturn(7);
    EasyMock.expect(position.getCol()).andStubReturn(col);

    EasyMock.replay(position);

    board.initializeBoard();

    Piece actual = board.getPieceAt(position);

    assertEquals(Color.BLACK, actual.getColor());
    assertEquals(PieceType.PAWN, actual.getPieceType());
    assertFalse(board.isEmpty(position));
  }
}
