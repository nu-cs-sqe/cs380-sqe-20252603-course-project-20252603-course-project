package ui;

import domain.GameState;
import java.awt.Color;
import java.awt.Font;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.text.MessageFormat;

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

  private final ResourceBundle messages = ResourceBundle.getBundle("messages");

  private JLabel currentPlayerLabel;

  /**
   * Creates the game stats panel showing player information.
   */
  public GameStatsView(String player1Name, String player2Name, ResourceBundle messages) {
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

    // Initialize current player properly
    currentPlayerLabel = new JLabel();
    currentPlayerLabel.setFont(HEADER_FONT);
    currentPlayerLabel.setForeground(LABEL_FOREGROUND);

    updateGameState(GameState.WHITE_TURN, player1Name, player2Name);

    add(playerInfoLabel);
    add(player1Label);
    add(player2Label);
    add(currentPlayerLabel);
  }

  public GameStatsView(String player1Name, String player2Name, GameState initialState) {
    this(player1Name, player2Name, ResourceBundle.getBundle("MessagesBundle"));
  }

  private void styleBodyLabel(JLabel label) {
    label.setFont(BODY_FONT);
    label.setForeground(LABEL_FOREGROUND);
    label.setBorder(LEFT_INDENT);
  }

  /**
   * Updates the label for the current player's turn, a win, or a draw
   */
  public void updateGameState(GameState gameState, String player1Name, String player2Name) {
    switch (gameState) {
      case WHITE_TURN:
        currentPlayerLabel.setText(
            MessageFormat.format(
                messages.getString("game.currentPlayer"),
                player1Name));
        break;

      case BLACK_TURN:
        currentPlayerLabel.setText(
            MessageFormat.format(
                messages.getString("game.currentPlayer"),
                player2Name));
        break;

      case WHITE_WIN:
        currentPlayerLabel.setText(
            MessageFormat.format(
                messages.getString("game.winner"),
                player1Name));
        break;

      case BLACK_WIN:
        currentPlayerLabel.setText(
            MessageFormat.format(
                messages.getString("game.winner"),
                player2Name));
        break;

      case DRAW:
        currentPlayerLabel.setText(
            messages.getString("game.draw"));
        break;

      default:
        throw new IllegalStateException(
            "Unexpected game state: " + gameState);
    }
  }
}
