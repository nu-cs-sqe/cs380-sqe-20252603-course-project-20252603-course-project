package ui;

import java.util.Locale;
import javax.swing.JPanel;

public class GameStatsView extends JPanel {

  private Locale locale;

  public GameStatsView(String player1Name, String player2Name, Locale locale) {
    this.locale = locale;
  }
}
