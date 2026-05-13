package ui;

import javax.swing.*;

public class GameStatsView extends JPanel {
    public GameStatsView(String player1Name, String player2Name) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel playerInfoLabel =
                new JLabel("Player Information");

        JLabel player1Label =
                new JLabel("Player 1: " + player1Name + " (Team: White)");

        JLabel player2Label =
                new JLabel("Player 2: " + player2Name + " (Team: Black)");

        JLabel currentPlayerLabel =
                new JLabel("Current Player: " + player1Name);

        add(playerInfoLabel);
        add(player1Label);
        add(player2Label);
        add(currentPlayerLabel);
    }
}
