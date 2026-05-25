package ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Displays player and game status information.
 */
public class GameStatsView extends JPanel {

  private static final Color PANEL_BACKGROUND = new Color(104, 76, 150);
  private static final Color LABEL_FOREGROUND = Color.WHITE;

  private static final Font HEADER_FONT =
      new Font("Arial", Font.BOLD, 30);

  private static final Font BODY_FONT =
      new Font("Arial", Font.BOLD, 20);

  private static final Border LEFT_INDENT =
      BorderFactory.createEmptyBorder(0, 20, 0, 0);

  private JLabel currentPlayerLabel;

  /**
   * Creates the game stats panel showing player information.
   */
  public GameStatsView(String player1Name, String player2Name) {

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setOpaque(true);
    setBackground(PANEL_BACKGROUND);

    JLabel playerInfoLabel = new JLabel("Player Information");
    playerInfoLabel.setFont(HEADER_FONT);
    playerInfoLabel.setForeground(LABEL_FOREGROUND);

    JLabel player1Label =
        new JLabel("Player 1: " + player1Name + " (Team: White)");
    styleBodyLabel(player1Label);

    JLabel player2Label =
        new JLabel("Player 2: " + player2Name + " (Team: Black)");
    styleBodyLabel(player2Label);

    currentPlayerLabel =
        new JLabel("Current Player: " + player1Name);
    currentPlayerLabel.setFont(HEADER_FONT);
    currentPlayerLabel.setForeground(LABEL_FOREGROUND);

    add(playerInfoLabel);
    add(player1Label);
    add(player2Label);
    add(currentPlayerLabel);
  }

  /**
   * Applies standard styling to body labels.
   */
  private void styleBodyLabel(JLabel label) {
    label.setFont(BODY_FONT);
    label.setForeground(LABEL_FOREGROUND);
    label.setBorder(LEFT_INDENT);
  }

  /**
   * Updates the displayed current player.
   */
  public void setCurrentPlayer(String name) {
    currentPlayerLabel.setText("Current Player: " + name);
  }
}