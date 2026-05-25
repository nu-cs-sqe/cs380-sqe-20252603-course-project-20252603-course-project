package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

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
    // Resolved String Literal: Sourced dynamically via locale keys
    setTitle(messages.getString("ui.window.title"));

    // Explicit Window Behavior Definition
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(IS_WINDOW_RESIZABLE);
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