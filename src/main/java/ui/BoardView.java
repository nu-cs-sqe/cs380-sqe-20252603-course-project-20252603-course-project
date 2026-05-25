package ui;

import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoardView extends JPanel {
  private static final int BOARD_SIZE = 8;
  private static final int TILE_SIZE = 100;
  private final Color lightSquareColor = new Color(240, 217, 181);
  private final Color darkSquareColor = new Color(181, 136, 99);
  private final Color selectedSquareColor = new Color(164, 149, 195);

  private int selectedRow = -1;
  private int selectedCol = -1;

  private Map<PieceType, Image> whitePieceImages;
  private Map<PieceType, Image> blackPieceImages;

  private BoardController boardController;

  public BoardView(BoardController boardController) {
    setPreferredSize(new Dimension(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE));
    loadPieceImages();
    this.boardController = boardController;
    boardController.setBoardView(this);
    addMouseListener(new BoardMouseListener());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawBoard(g);
    drawSelectedSquare(g);
    drawPieces(g);
  }

  private void drawBoard(Graphics g) {
    for (int row = 0; row < BOARD_SIZE; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        Color squareColor = (row + col) % 2 == 0 ? lightSquareColor : darkSquareColor;
        g.setColor(squareColor);
        g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
      }
    }
  }

  private void loadPieceImages() {
    whitePieceImages = new HashMap<>();
    blackPieceImages = new HashMap<>();

    loadOnePieceImage(PieceType.PAWN, PieceColor.WHITE, "images/white_pawn.png");
    loadOnePieceImage(PieceType.ROOK, PieceColor.WHITE, "images/white_rook.png");
    loadOnePieceImage(PieceType.KNIGHT, PieceColor.WHITE, "images/white_knight.png");
    loadOnePieceImage(PieceType.BISHOP, PieceColor.WHITE, "images/white_bishop.png");
    loadOnePieceImage(PieceType.QUEEN, PieceColor.WHITE, "images/white_queen.png");
    loadOnePieceImage(PieceType.KING, PieceColor.WHITE, "images/white_king.png");

    loadOnePieceImage(PieceType.PAWN, PieceColor.BLACK, "images/black_pawn.png");
    loadOnePieceImage(PieceType.ROOK, PieceColor.BLACK, "images/black_rook.png");
    loadOnePieceImage(PieceType.KNIGHT, PieceColor.BLACK, "images/black_knight.png");
    loadOnePieceImage(PieceType.BISHOP, PieceColor.BLACK, "images/black_bishop.png");
    loadOnePieceImage(PieceType.QUEEN, PieceColor.BLACK, "images/black_queen.png");
    loadOnePieceImage(PieceType.KING, PieceColor.BLACK, "images/black_king.png");
  }

  private void loadOnePieceImage(PieceType type, PieceColor color, String imagePath) {
    InputStream imageInputStream = getClass().getClassLoader().getResourceAsStream(imagePath);
    BufferedImage image = null;
    try {
      image = ImageIO.read(imageInputStream);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    if (color.equals(PieceColor.BLACK)) {
      blackPieceImages.put(type, image);
    } else {
      whitePieceImages.put(type, image);
    }
  }

  private void drawPieces(Graphics g) {
    Piece[][] boardSnapshot = boardController.getBoardSnapshot();
    if (boardSnapshot == null) {
      return;
    }
    for (int row = 0; row < BOARD_SIZE; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        Piece piece = boardSnapshot[row][col];
        if (piece != null) {
          Image pieceImage = piece.getColor() == PieceColor.WHITE
              ? whitePieceImages.get(piece.getType())
              : blackPieceImages.get(piece.getType());
          g.drawImage(pieceImage, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
        }
      }
    }
  }

  private void drawSelectedSquare(Graphics g) {
    if (selectedRow < 0 || selectedCol < 0) {
      return;
    }
    g.setColor(selectedSquareColor);
    g.fillRect(selectedCol * TILE_SIZE, selectedRow * TILE_SIZE, TILE_SIZE, TILE_SIZE);
  }

  private class BoardMouseListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent ev) {
      int col = ev.getX() / TILE_SIZE;
      int row = ev.getY() / TILE_SIZE;
      if (col < 0 || col >= BOARD_SIZE || row < 0 || row >= BOARD_SIZE) {
        return;
      }
      selectedCol = col;
      selectedRow = row;
      boardController.handleSquareClick(new Location(selectedCol, selectedRow));
      repaint();
    }
  }
}
