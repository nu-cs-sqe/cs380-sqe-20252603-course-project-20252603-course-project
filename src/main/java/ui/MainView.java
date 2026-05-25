package ui;

import java.awt.BorderLayout;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;

public class MainView extends JFrame {

  private static final String BUNDLE_NAME = "MessagesBundle";

  private BoardView boardView;
  private BoardController boardController;
  private GameStatsView gameStatsView;
  private String player1Name;
  private String player2Name;
  private Locale locale;

  public MainView(String player1Name, String player2Name, Locale locale) {
    this.player1Name = player1Name;
    this.player2Name = player2Name;
    this.locale = locale;
    configureMainView();
    addGameStatsView();
    addBoardView();
  }

  private void configureMainView() {
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    setTitle(bundle.getString("mainWindow.title"));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);
  }

  private void addGameStatsView() {
    gameStatsView = new GameStatsView(player1Name, player2Name, locale);
    add(gameStatsView, BorderLayout.SOUTH);
  }

  private void addBoardView() {
    boardController = new BoardController();
    boardView = new BoardView(boardController);
    boardController.setBoardView(boardView);
    add(boardView, BorderLayout.CENTER);
  }
}
