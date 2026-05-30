package ui;

import java.awt.Color;
import java.awt.Font;
import java.util.ResourceBundle;
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
  private final ResourceBundle messages;

  /**
   * Creates the game stats panel showing player information.
   */
  public GameStatsView(String player1Name, String player2Name, ResourceBundle messages) {
    this.messages = messages;

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setOpaque(true);
    setBackground(PANEL_BACKGROUND);

    JLabel playerInfoLabel = new JLabel(messages.getString("game.playerInfo"));
    playerInfoLabel.setFont(HEADER_FONT);
    playerInfoLabel.setForeground(LABEL_FOREGROUND);

    JLabel player1Label =
        new JLabel(messages.getString("game.player1") + ": " + player1Name + " ("
            + messages.getString("game.teamWhite") + ")");
    styleBodyLabel(player1Label);

    JLabel player2Label =
        new JLabel(messages.getString("game.player2") + ": " + player2Name + " ("
            + messages.getString("game.teamBlack") + ")");
    styleBodyLabel(player2Label);

    currentPlayerLabel = new JLabel(messages.getString("game.currentPlayer") + " " + player1Name);
    currentPlayerLabel.setFont(HEADER_FONT);
    currentPlayerLabel.setForeground(LABEL_FOREGROUND);

    add(playerInfoLabel);
    add(player1Label);
    add(player2Label);
    add(currentPlayerLabel);
  }

  private void styleBodyLabel(JLabel label) {
    label.setFont(BODY_FONT);
    label.setForeground(LABEL_FOREGROUND);
    label.setBorder(LEFT_INDENT);
  }

  public void updateCurrentPlayerLabel(String name) {
    currentPlayerLabel.setText(messages.getString("game.currentPlayer") + " " + name);
  }
}
