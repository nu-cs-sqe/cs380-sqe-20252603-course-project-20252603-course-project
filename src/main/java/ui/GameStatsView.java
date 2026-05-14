package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStatsView extends JPanel {

  private JLabel currentPlayerLabel;

  public GameStatsView(String player1Name, String player2Name) {
    currentPlayerLabel = new JLabel("Current Player: " + player1Name);
  }

  public void updateCurrentPlayerLabel(String playerName) {
    currentPlayerLabel.setText("Current Player: " + playerName);
  }
}
