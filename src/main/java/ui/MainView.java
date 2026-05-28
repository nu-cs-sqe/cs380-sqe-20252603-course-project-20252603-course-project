package ui;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainView extends JFrame {

  private static final boolean IS_WINDOW_RESIZABLE = false;

  private BoardView boardView;
  private BoardController boardController;
  private GameStatsView gameStatsView;
  private final String player1Name;
  private final String player2Name;
  private final ResourceBundle messages;

  public MainView(String player1Name, String player2Name, ResourceBundle messages) {
    this.player1Name = player1Name;
    this.player2Name = player2Name;
    this.messages = messages;

    configureMainView();
    addGameStatsView();
    addBoardView();
  }

  private void configureMainView() {
    setTitle(messages.getString("window.title"));

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(IS_WINDOW_RESIZABLE);
  }

  private void addGameStatsView() {
    gameStatsView = new GameStatsView(player1Name, player2Name, messages);
    add(gameStatsView, BorderLayout.SOUTH);
  }

  private void addBoardView() {
    boardController = new BoardController();
    boardView = new BoardView(boardController);
    boardController.setBoardView(boardView);
    add(boardView, BorderLayout.CENTER);
  }
}
