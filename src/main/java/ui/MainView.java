package ui;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

  private BoardView boardView;
  private BoardController boardController;
  private GameStatsView gameStatsView;
  private String player1Name;
  private String player2Name;

  public MainView(String player1Name, String player2Name) {
    this.player1Name = player1Name;
    this.player2Name = player2Name;
    configureMainView();
    addGameStatsView();
    addBoardView();
  }

  private void configureMainView() {
    setTitle("Chess");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);
  }

  private void addGameStatsView() {
    gameStatsView = new GameStatsView(player1Name, player2Name);
    add(gameStatsView, BorderLayout.SOUTH);
  }

  private void addBoardView() {
    boardController = new BoardController();
    boardView = new BoardView(boardController);
    boardController.setBoardView(boardView);
    add(boardView, BorderLayout.CENTER);
  }
}
